package com.git.simulate;


import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Proxy {



    // 方法调用处理器  代理对象中的每个方法都通过此对象执行
    protected InvocationHandler h;

    private Proxy() { }

    protected Proxy(InvocationHandler h) {
        Objects.requireNonNull(h);

        this.h = h;
    }


    /**
     * 生成代理对象
     *
     * @param classLoader 类加载器
     * @param interfaces  目标类的接口
     * @param h
     * @return
     */
    public static Object newProxyInstance(ClassLoader classLoader, Class<?>[] interfaces, InvocationHandler h) {

        Objects.requireNonNull(interfaces);

        // 获取代理类字节码对象
        Class<?> proxyClazz = getProxyClass0(classLoader, interfaces);

        try {
            // 获取构造方法
            Constructor<?> constructor = proxyClazz.getConstructor(InvocationHandler.class);

            return constructor.newInstance(h);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("创建代理对象失败");

    }


    private static Class<?> getProxyClass0(ClassLoader classLoader, Class<?>[] interfaces) {

        // 使用ProxyClassFactory.apply(classLoader,interfaces)方法获取字节码对象
        Class<?> proxyClass = ProxyClassFactory.apply(classLoader, interfaces);

        return proxyClass;
    }


    private static final class ProxyClassFactory {

        private static final String PROXY_PKG = "com.juzi.proxy";
        private static final String PROXY_PATH_DIR = "/com/juzi/proxy/";

        private static final String PROXY_PREFIX = "$Proxy";

        private static final AtomicLong atomicLong = new AtomicLong();

        // 获取当前工作目录路径
        private static String projectPath = System.getProperty("user.dir");

        //
        static {
            File file = new File(projectPath +PROXY_PATH_DIR);
            // 初始化创建代理类生成目录
            if( ! file.exists()){
                file.mkdirs();
            }
        }



        private static Class<?> apply(ClassLoader classLoader, Class<?>[] interfaces) {

            // 接口数量检查
            if (interfaces.length > 65535) {
                throw new IllegalArgumentException("interface limit exceeded");
            }

            long num = atomicLong.get();
            String proxyName = PROXY_PKG + "." + PROXY_PREFIX + num;

            //  生成代理类,并且获取代理类的字节码对象
            String proxyClassStr = ProxyGenerator.generateProxyClass(proxyName, interfaces);

            // 在JDk Proxy底层调用的是 native (C++)的方法defineClass加载的类
            Class<?> proxyClass = defineClass0(classLoader, proxyName, proxyClassStr);

            return proxyClass;
        }

        private static Class<?> defineClass0(ClassLoader classLoader, String proxyName, String proxyClassStr) {
            // 项目路径

            String proxyClassPath = proxyName.replaceAll("\\.", "/");
            File file = new File(projectPath+"\\" + proxyClassPath + ".java");
            if (! file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                fw.write(proxyClassStr);
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException("初始化文件出错: + " + file.getPath());
                }
            }

            try {
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
                Iterable<? extends JavaFileObject> iterable = fileManager.getJavaFileObjects(file);

                JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, iterable);
                task.call();

                fileManager.close();


                // 使用UrlClassLoader类加载器
                URL[] urls = {new URL("file:"+projectPath+"\\")};
                URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);
                // 加载类
                Class<?> proxyClazz = urlClassLoader.loadClass(proxyName);
                // file.delete();  // 将java文件删除
                return proxyClazz;
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
    }
}



}
