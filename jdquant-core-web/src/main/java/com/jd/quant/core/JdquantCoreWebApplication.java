package com.jd.quant.core;

import com.jd.quant.core.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * JDQuant Core Application Bootloader
 *
 * @author Zhiguo.Chen
 */
@Import({MyBatisConfig.class})
@SpringBootApplication(scanBasePackages = {"com.jd.quant.core"})
public class JdquantCoreWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdquantCoreWebApplication.class, args);
    }
}
