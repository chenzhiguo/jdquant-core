package com.jd.quant.core.common.utils;

import com.google.common.collect.Lists;
import com.jd.quant.core.common.exception.UtilException;
import com.jd.quant.core.common.support.BasicType;
import com.jd.quant.core.common.support.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class工具类
 */
public final class ClassUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ClassUtil.class);

    private ClassUtil() {}

    /**
     * 取得某个接口下所有实现这个接口的类
     */
    public static List<Class> getAllClassByInterface(Class c) {
        List<Class> returnClassList = null;

        if (c.isInterface()) {
            // 获取当前的包名
            String packageName = c.getPackage().getName();
            // 获取当前包下以及子包下所以的类
            List<Class<?>> allClass = getClasses(packageName);
            if (allClass != null) {
                returnClassList = Lists.newArrayList();
                for (Class classes : allClass) {
                    // 判断是否是同一个接口
                    if (c.isAssignableFrom(classes)) {
                        // 本身不加入进去
                        if (!c.equals(classes)) {
                            returnClassList.add(classes);
                        }
                    }
                }
            }
        }

        return returnClassList;
    }


    /**
     * 取得某一类所在包的所有类名 不含迭代
     *
     * @param classLocation
     * @param packageName
     * @return
     */
    public static String[] getPackageAllClassName(String classLocation, String packageName) {
        //将packageName分解
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        for (String aPackagePathSplit : packagePathSplit) {
            realClassLocation = realClassLocation + File.separator + aPackagePathSplit;
        }
        File packeageDir = new File(realClassLocation);
        if (packeageDir.isDirectory()) {
            return packeageDir.list();
        }
        return null;
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(String packageName) {

        //第一个class类的集合
        List<Class<?>> classes = Lists.newArrayList();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()) {
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            } else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 扫面该包路径下所有class文件
     *
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage() {
        return scanPackage(StringConstants.BLANK, null);
    }

    /**
     * 扫面该包路径下所有class文件
     *
     * @param packageName 包路径 com | com. | com.abs | com.abs.
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(String packageName) {
        return scanPackage(packageName, null);
    }

    /**
     * 扫描指定包路径下所有包含指定注解的类
     * @param packageName 包路径
     * @param annotationClass 注解类
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageByAnnotation(String packageName, final Class<? extends Annotation> annotationClass) {
        return scanPackage(packageName, new ClassFilter() {
            @Override
            public boolean accept(Class<?> clazz) {
                return clazz.isAnnotationPresent(annotationClass);
            }
        });
    }

    /**
     * 扫描指定包路径下所有指定类的子类
     * @param packageName 包路径
     * @param superClass 父类 
     * @return 类集合
     */
    public static Set<Class<?>> scanPackageBySuper(String packageName, final Class<?> superClass) {
        return scanPackage(packageName, new ClassFilter() {
            @Override
            public boolean accept(Class<?> clazz) {
                return superClass.isAssignableFrom(clazz) && !superClass.equals(clazz);
            }
        });
    }

    /**
     * 扫面包路径下满足class过滤器条件的所有class文件，</br> 
     * 如果包路径为 com.abs + A.class 但是输入 abs会产生classNotFoundException</br>
     * 因为className 应该为 com.abs.A 现在却成为abs.A,此工具类对该异常进行忽略处理,有可能是一个不完善的地方，以后需要进行修改</br>
     *
     * @param packageName 包路径 com | com. | com.abs | com.abs.
     * @param classFilter class过滤器，过滤掉不需要的class
     * @return 类集合
     */
    public static Set<Class<?>> scanPackage(String packageName, ClassFilter classFilter) {
        if(StringUtil.isBlank(packageName)) {
            packageName = StringConstants.BLANK;
        }
        LOG.debug("Scan classes from package [{}]...", packageName);
        packageName = getWellFormedPackageName(packageName);

        final Set<Class<?>> classes = new HashSet<Class<?>>();
        for (String classPath : getClassPaths(packageName)) {
            LOG.debug("Scan classpath: [{}]", classPath);
            // 填充 classes
            fillClasses(classPath, packageName, classFilter, classes);
        }

        //如果在项目的ClassPath中未找到，去系统定义的ClassPath里找
        if(classes.isEmpty()) {
            for (String classPath : getJavaClassPaths()) {
                LOG.debug("Scan java classpath: [{}]", classPath);
                // 填充 classes
                fillClasses(new File(classPath), packageName, classFilter, classes);
            }
        }
        return classes;
    }

    /**
     * 获得指定类中的Public方法名<br>
     * 去重重载的方法
     * @param clazz 类
     */
    public final static Set<String> getMethods(Class<?> clazz) {
        HashSet<String> methodSet = new HashSet<String>();
        Method[] methodArray = clazz.getMethods();
        for (Method method : methodArray) {
            String methodName = method.getName();
            methodSet.add(methodName);
        }
        return methodSet;
    }

    /**
     * 获得ClassPath
     * @return ClassPath集合
     */
    public static Set<String> getClassPathResources(){
        return getClassPaths(StringConstants.BLANK);
    }

    /**
     * 获得ClassPath
     * @param packageName 包名称
     * @return ClassPath路径字符串集合
     */
    public static Set<String> getClassPaths(String packageName){
        String packagePath = packageName.replace(StringConstants.PERIOD, StringConstants.SLASH);
        Enumeration<URL> resources;
        try {
            resources = getClassLoader().getResources(packagePath);
        } catch (IOException e) {
            throw new UtilException(StringUtil.format("Loading classPath [{}] error!", packagePath), e);
        }
        Set<String> paths = new HashSet<String>();
        while(resources.hasMoreElements()) {
            paths.add(resources.nextElement().getPath());
        }
        return paths;
    }

    /**
     * @return 获得Java ClassPath路径，不包括 jre
     */
    public static String[] getJavaClassPaths() {
        String[] classPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
        return classPaths;
    }

    /**
     * 转换基本类型
     * @param clazz 被转换为基本类型的类，必须为包装类型
     * @return 基本类型类
     */
    public static Class<?> castToPrimitive(Class<?> clazz) {
        if(null == clazz || clazz.isPrimitive()) {
            return clazz;
        }

        BasicType basicType;
        try {
            basicType = BasicType.valueOf(clazz.getSimpleName().toUpperCase());
        }catch(Exception e) {
            return clazz;
        }

        //基本类型
        switch (basicType) {
            case BYTE:
                return byte.class;
            case SHORT:
                return short.class;
            case INTEGER:
                return int.class;
            case LONG:
                return long.class;
            case DOUBLE:
                return double.class;
            case FLOAT:
                return float.class;
            case BOOLEAN:
                return boolean.class;
            case CHAR:
                return char.class;
            default:
                return clazz;
        }
    }

    /**
     * @return 当前线程的class loader
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获得class loader<br>
     * 若当前线程class loader不存在，取当前类的class loader
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if(classLoader == null) {
            classLoader = ClassUtil.class.getClassLoader();
        }
        return classLoader;
    }

    /**
     * 实例化对象
     * @param clazz 类名
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String clazz) {
        try {
            return (T) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new UtilException(StringUtil.format("Instance class [{}] error!", clazz), e);
        }
    }

    /**
     * 实例化对象
     * @param clazz 类
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new UtilException(StringUtil.format("Instance class [{}] error!", clazz), e);
        }
    }

    /**
     * 克隆对象
     * @param obj 被克隆对象
     * @return 克隆后的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T cloneObj(T obj) {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        try {
            final ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);
            final ObjectInputStream in =new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
            return (T) in.readObject();
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 加载类
     * @param className 类名
     * @param isInitialized 是否初始化
     * @return 类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        }catch (ClassNotFoundException e) {
            throw new UtilException(e);
        }
        return clazz;
    }

    /**
     * 加载类并初始化
     * @param className 类名 
     * @return 类
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    //--------------------------------------------------------------------------------------------------- Private method start
    /**
     * 文件过滤器，过滤掉不需要的文件<br>
     * 只保留Class文件、目录和Jar
     */
    private static FileFilter fileFilter = new FileFilter(){
        @Override
        public boolean accept(File pathname) {
            return isClass(pathname.getName()) || pathname.isDirectory() || isJarFile(pathname);
        }
    };

    /**
     * 改变 com -> com. 避免在比较的时候把比如 completeTestSuite.class类扫描进去，如果没有"."</br>
     * 那class里面com开头的class类也会被扫描进去,其实名称后面或前面需要一个 ".",来添加包的特征
     *
     * @param packageName
     * @return 格式化后的包名
     */
    private static String getWellFormedPackageName(String packageName) {
        return packageName.lastIndexOf(StringConstants.PERIOD) != packageName.length() - 1 ? packageName + StringConstants.PERIOD : packageName;
    }

    /**
     * 填充满足条件的class 填充到 classes<br>
     * 同时会判断给定的路径是否为Jar包内的路径，如果是，则扫描此Jar包
     *
     * @param path Class文件路径或者所在目录Jar包路径
     * @param packageName 需要扫面的包名
     * @param classFilter class过滤器
     * @param classes List 集合
     */
    private static void fillClasses(String path, String packageName, ClassFilter classFilter, Set<Class<?>> classes) {
        //判定给定的路径是否为Jar
        int index = path.lastIndexOf(FileUtil.JAR_PATH_EXT);
        if(index != -1) {
            //Jar文件
            path = path.substring(0, index + FileUtil.JAR_FILE_EXT.length());	//截取jar路径
            path = StringUtil.removePrefix(path, FileUtil.PATH_FILE_PRE);	//去掉文件前缀
            processJarFile(new File(path), packageName, classFilter, classes);
        }else {
            fillClasses(new File(path), packageName, classFilter, classes);
        }
    }

    /**
     * 填充满足条件的class 填充到 classes
     *
     * @param file Class文件或者所在目录Jar包文件
     * @param packageName 需要扫面的包名
     * @param classFilter class过滤器
     * @param classes List 集合
     */
    private static void fillClasses(File file, String packageName, ClassFilter classFilter, Set<Class<?>> classes) {
        if (file.isDirectory()) {
            processDirectory(file, packageName, classFilter, classes);
        } else if (isClassFile(file)) {
            processClassFile(file, packageName, classFilter, classes);
        } else if (isJarFile(file)) {
            processJarFile(file, packageName, classFilter, classes);
        }
    }

    /**
     * 处理如果为目录的情况,需要递归调用 fillClasses方法
     *
     * @param directory 目录
     * @param packageName 包名
     * @param classFilter 类过滤器
     * @param classes 类集合
     */
    private static void processDirectory(File directory, String packageName, ClassFilter classFilter, Set<Class<?>> classes) {
        for (File file : directory.listFiles(fileFilter)) {
            fillClasses(file, packageName, classFilter, classes);
        }
    }

    /**
     * 处理为class文件的情况,填充满足条件的class 到 classes
     *
     * @param file class文件
     * @param packageName 包名
     * @param classFilter 类过滤器
     * @param classes 类集合
     */
    private static void processClassFile(File file, String packageName, ClassFilter classFilter, Set<Class<?>> classes) {
        final String filePathWithDot = file.getAbsolutePath().replace(File.separator, StringConstants.PERIOD);
        int subIndex = -1;
        if ((subIndex = filePathWithDot.indexOf(packageName)) != -1) {
            final String className = filePathWithDot.substring(subIndex).replace(FileUtil.CLASS_EXT, StringConstants.BLANK);
            fillClass(className, packageName, classes, classFilter);
        }
    }

    /**
     * 处理为jar文件的情况，填充满足条件的class 到 classes
     *
     * @param file jar文件
     * @param packageName 包名
     * @param classFilter 类过滤器
     * @param classes 类集合
     */
    private static void processJarFile(File file, String packageName, ClassFilter classFilter, Set<Class<?>> classes) {
        try {
            for (JarEntry entry : Collections.list(new JarFile(file).entries())) {
                if (isClass(entry.getName())) {
                    final String className = entry.getName().replace(StringConstants.SLASH, StringConstants.PERIOD).replace(FileUtil.CLASS_EXT, StringConstants.BLANK);
                    fillClass(className, packageName, classes, classFilter);
                }
            }
        } catch (Throwable ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    /**
     * 填充class 到 classes
     *
     * @param className 类名
     * @param packageName 包名
     * @param classes 类集合
     * @param classFilter 类过滤器
     */
    private static void fillClass(String className, String packageName, Set<Class<?>> classes, ClassFilter classFilter) {
        if (className.startsWith(packageName)) {
            try {
                final Class<?> clazz = Class.forName(className, false, getClassLoader());
                if (classFilter == null || classFilter.accept(clazz)) {
                    classes.add(clazz);
                }
            } catch (Throwable ex) {
                //Log.error(LOG, ex, "Load class [{}] error!", className);
                //Pass Load Error.
            }
        }
    }

    /**
     * @param file 文件
     * @return 是否为类文件
     */
    private static boolean isClassFile(File file) {
        return isClass(file.getName());
    }

    /**
     * @param fileName 文件名
     * @return 是否为类文件
     */
    private static boolean isClass(String fileName) {
        return fileName.endsWith(FileUtil.CLASS_EXT);
    }

    /**
     * @param file 文件
     * @return是否为Jar文件
     */
    private static boolean isJarFile(File file) {
        return file.getName().endsWith(FileUtil.JAR_FILE_EXT);
    }
    //--------------------------------------------------------------------------------------------------- Private method end

    /**
     * 类过滤器，用于过滤不需要加载的类<br>
     */
    public interface ClassFilter {
        boolean accept(Class<?> clazz);
    }
}
