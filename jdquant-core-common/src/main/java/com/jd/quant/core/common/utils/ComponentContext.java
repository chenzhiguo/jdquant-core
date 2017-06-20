package com.jd.quant.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ComponentContext implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(ComponentContext.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        LOG.info("设置Spring应用上下文");
        ComponentContext.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getComponent(String name) {
        assert (applicationContext != null);
        return (T) applicationContext.getBean(name);
    }

    /**
     * 得到bean
     *
     * @param name
     * @return
     */
    public static Object getBean(final String name) {
        if (null == applicationContext) {
            LOG.error("applicationContext is null");
        }
        return applicationContext.getBean(name);
    }

    public <T> T getComponent(Class<T> beanType) {
        assert (applicationContext != null);
        Map<String, T> matchedTypes = getComponentsOfType(beanType);
        if (matchedTypes.size() > 0) {
            for (Map.Entry<String, T> entry : matchedTypes.entrySet()) {
                Primary primary = getTargetClass(entry.getValue()).getAnnotation(Primary.class);
                if (primary != null)
                    return entry.getValue();
            }

            if (matchedTypes.size() > 1) {
                LOG.warn("无法定位唯一类型bean：" + beanType.getName());
                for (Map.Entry<String, T> entry : matchedTypes.entrySet()) {
                    LOG.warn("候选：" + getTargetClass(entry.getValue()).getName());
                }
            }

            return (T) matchedTypes.values().toArray()[0];
        }

        throw new NoSuchBeanDefinitionException(beanType.getName());
    }

    public <T> Map<String, T> getComponentsOfType(Class<T> beanType) {
        return applicationContext.getBeansOfType(beanType);
    }

    public Class<?> getTargetClass(Object instance) {
        while (instance instanceof Advised) {
            try {
                instance = ((Advised) instance).getTargetSource().getTarget();
            } catch (Exception e) {
                return instance.getClass();
            }
        }
        return instance.getClass();
    }

    public <T> T getTargetObject(Object instance) {
        while (instance instanceof Advised) {
            try {
                instance = ((Advised) instance).getTargetSource().getTarget();
            } catch (Exception e) {
                return (T) instance;
            }
        }

        return (T) instance;
    }

    public <T> T inject(Class<T> clz) {
        T instance;
        try {
            instance = clz.newInstance();
            return inject(instance);
        } catch (InstantiationException e) {
            LOG.error("无法捕获的InstantiationException", e);
            throw new RuntimeException("无法根据类[" + clz.getName() + "]实例化对象，请确保有公用构造函数");
        } catch (IllegalAccessException e) {
            LOG.error("无法捕获的IllegalAccessException", e);
            throw new RuntimeException("无法根据类[" + clz.getName() + "]实例化对象，请确保有公用构造函数");
        }
    }

    public <T> T inject(Object instance) {
        // 自动装载动态载入对象
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(instance);
        return (T) instance;
    }
}