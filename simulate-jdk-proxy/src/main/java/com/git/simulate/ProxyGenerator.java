package com.git.simulate;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

public class ProxyGenerator {

    private final String h = "com.git.simulate.InvocationHandler";

    private String superClassName = "com.git.simulate.Proxy";

    private static final String importPkg = "import java.lang.reflect.*;";       // 导入反射包下所有类

    private Map<String, List<ProxyGenerator.ProxyMethod>> proxyMethods = new HashMap<>();

    private List<ProxyGenerator.MethodInfo> methods = new ArrayList<>();

    private List<ProxyGenerator.FieldInfo> fields = new ArrayList<>();

    // 用于存放基本数据类型所对应的包装类型
    private static Map<Class<?>,Class<?>> table = new HashMap<>(8);

    static {
        table.put(byte.class,Byte.class);
        table.put(int.class,Integer.class);
        table.put(short.class,Short.class);
        table.put(long.class,Long.class);
        table.put(char.class,Character.class);
        table.put(float.class,Float.class);
        table.put(double.class,Double.class);
        table.put(boolean.class, Boolean.class);


    }


    private static final String line = "\n";
    private static final String tab = "\t";


    private int proxyMethodCount = 0;

    private String className;   // 代理类名称
    private Class<?>[] interfaces;      // 接口数组
    private String interfacesStr;

    private StringBuffer proxyClassContent = new StringBuffer();


    public ProxyGenerator(String className, Class<?>[] interfaces) {
        this.className = className;
        this.interfaces = interfaces;
        this.interfacesStr = generateInterfacesStr(interfaces);
    }

    private String generateInterfacesStr(Class<?>[] interfaces) {
        if(interfaces.length == 1){
            return interfaces[0].getName();
        }

        StringBuffer sb = new StringBuffer();

        for (Class<?> inf : interfaces) {
            sb.append(inf.getName()).append(", ");
        }


        return sb.substring(0,sb.lastIndexOf(","));
    }


    /**
     * 生成代理类的Class字节码对象
     *
     * @param proxyName
     * @param interfaces
     * @return
     */
    public static String generateProxyClass(String proxyName, Class<?>[] interfaces) {

        Objects.requireNonNull(interfaces);

        ProxyGenerator proxyGenerator = new ProxyGenerator(proxyName, interfaces);

        // 生成代理类中所有内容
        String proxyClassStr = proxyGenerator.generateClassFile();


        return proxyClassStr;

    }

    private String generateClassFile() {

        // 整个代理类的内容
        StringBuffer proxyClassContent = new StringBuffer("package com.juzi.proxy;").append(line);
        // 用于添加导包部分内容
        StringBuffer importContent = new StringBuffer();


        int index = this.className.lastIndexOf(".");
        String className = this.className.substring(index + 1);          // 代理类类名称
        String packageName = this.className.substring(0, index);        // 代理类所在包

        // 导包
        importContent.append(importPkg).append(line);

        // 导入所有接口,并且将所有接口中的方法的信息用proxyMethods存储
        String importInterface = appendInterface(interfaces);
        importContent.append(importInterface);


        Set<String> keys = proxyMethods.keySet();
        for (String key : keys) {
            List<ProxyMethod> proxyMethodList = proxyMethods.get(key);
            if (proxyMethodList != null && !proxyMethodList.isEmpty()) {

                for (ProxyMethod proxyMethod : proxyMethodList) {
                    // String name,Class<?>[] parameterTypes,Class<?>[] exceptionTypes
                    //
                    methods.add(
                            new MethodInfo(
                                    proxyMethod.getMethodName(),
                                    proxyMethod.getMethodFieldName(),
                                    proxyMethod.getFormClass(),
                                    proxyMethod.getParameterTypes()));

                    fields.add(
                            new FieldInfo(proxyMethod.methodFieldName)
                    );
                }

            }
        }

        // 生成代理类
        String proxyClass = generateProxyClassStr(packageName,importContent);


        return proxyClass;

    }

    private String generateProxyClassStr(String packageName, StringBuffer importContent) {

        // 包名,导包, 类 部分
        String proxyClassName = className.replace(packageName + ".", "");
        proxyClassContent.append("package ").append(packageName).append(";").append(line)
                          .append(importContent.toString()).append(line).append(line)
                          .append("public class ").append(proxyClassName).append(" extends ").append(superClassName).append(" implements ").append(interfacesStr).append("{").append(line)
                          .append(line).append(line);

        // 代理类方法属性部分
        for (FieldInfo field : fields) {
            // java.lang.reflect.Method m1;
            String methodField = field.GeneratorMethodField();
            proxyClassContent.append(methodField).append(line).append(tab).append(tab);
        }

        proxyClassContent.append(line).append(line).append(tab).append(tab);

        // 构造方法部分
        proxyClassContent.append("public ").append(proxyClassName).append("(").append(h).append(" invocationHandler").append("){").append(line)
                         .append(tab).append(tab).append(tab).append("super(invocationHandler);").append(line)
                         .append(tab).append(tab).append("}");


        proxyClassContent.append(line).append(line).append(tab).append(tab);

        // 类中所有方法部分
        Set<String> keys = proxyMethods.keySet();
        for (String key : keys) {
            List<ProxyMethod> proxyMethods = this.proxyMethods.get(key);
            for (ProxyMethod proxyMethod : proxyMethods) {
                String methodContent = proxyMethod.generatorMethod();
                proxyClassContent.append(methodContent).append(line).append(line).append(tab).append(tab);
            }
        }

        // 静态块给方法属性赋值部分
        proxyClassContent.append("static {").append(line)
                .append(tab).append(tab).append(tab).append(tab).append("try{").append(line);
        for (MethodInfo method : methods) {
            proxyClassContent.append(method.generatorMethodInfo()).append(line).append(tab).append(tab).append(tab).append(tab);
        }

        proxyClassContent.append("}\n" +
                "\t\tcatch (NoSuchMethodException nosuchmethodexception)\n" +
                "\t\t{\n" +
                "\t\t\tthrow new NoSuchMethodError(nosuchmethodexception.getMessage());\n" +
                "\t\t}\n" +
                "\t\tcatch (ClassNotFoundException classnotfoundexception)\n" +
                "\t\t{\n" +
                "\t\t\tthrow new NoClassDefFoundError(classnotfoundexception.getMessage());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n");


        return proxyClassContent.toString();
    }


    /**
     * 将接口全部导入,并且添加需要代理的方法
     *
     * @return
     */
    private String appendInterface(Class<?>[] interfaces) {

        StringBuffer sb = new StringBuffer();

        for (Class<?> inf : interfaces) {
            // 导入包
            sb.append("import ").append(inf.getName()).append(";").append(line);

            Method[] methods = inf.getMethods();


            for (Method method : methods) {

                // 方法名称
                String methodName = method.getName();
                // 返回值类型
                Class<?> returnType = method.getReturnType();
                // 参数列表
                Class<?>[] parameterTypes = method.getParameterTypes();
                // 声明的异常类型列表
                Class<?>[] exceptionTypes = method.getExceptionTypes();

                // 考虑hash冲突使用List
                List<ProxyGenerator.ProxyMethod> proxyMethodList = proxyMethods.get(methodName);
                if (proxyMethodList == null) {
                    proxyMethodList = new ArrayList<>();
                    proxyMethods.put(methodName, proxyMethodList);
                }
                //String methodName, Class<?>[] parameterTypes, Class<?> returnType, Class<?>[] exceptionTypes, Class<?> formClass
                proxyMethodList.add(new ProxyMethod(methodName, parameterTypes, returnType, exceptionTypes, inf));
            }

        }

        return sb.toString();
    }

    /**
     * Proxy类中主要有三个部分
     * <p>
     * Method 对象       FieldInfo  表示
     * 属性名称
     * <p>
     * static 块初始化Method对象  MethodInfo表示
     * 方法名称
     * 方法参数列表
     * 方法属于的接口类
     * <p>
     * <p>
     * 方法内容  ProxyMethod表示
     * <p>
     * 方法名称
     * 方法返回值类型
     * 方法的参数列表
     * 方法属于的接口类
     * 方法的声明的异常
     * 方法的属性名称     // 此处就需要定义好,用于传给其他对象
     */


    private class ProxyMethod {

        private String methodName;      // 方法名称
        private Class<?>[] parameterTypes;  // 参数Class数组
        private Class<?> returnType;        // 返回值Class类型
        private Class<?>[] exceptionTypes;        // 声明的异常Class数组
        private Class<?> formClass;             //  方法所在的接口的Class对象
        private String methodFieldName;     // 在代理类中的属性名称

        private String exceptionStr;        // 方法上声明的异常字符串
        private String parameterTypesStr;      //  形参列表转换之后的字符串
        private String parametersStr;       // 方法参数字符串
        private String returnTypeStr;       // 方法返回值的字符串
        private String finallyReturnStr = "";        // 用于存在异常返回的字符串
        private String argsStr;




        private StringBuffer methodContent = new StringBuffer();        // 方法内容


        public ProxyMethod(String methodName, Class<?>[] parameterTypes, Class<?> returnType, Class<?>[] exceptionTypes, Class<?> formClass) {

            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
            this.exceptionTypes = exceptionTypes;
            this.formClass = formClass;


            this.methodFieldName = "m" + (ProxyGenerator.this.proxyMethodCount++);
            // 将声明的异常类型处理成String  throws Exception
            this.exceptionStr = this.exceptionStr(exceptionTypes);
            this.parameterTypesStr = generatorDescriptor(parameterTypes);   // 形参列表字符串
            this.parametersStr = generatorParametersStr(parameterTypes);          // 方法参数字符串
            this.returnTypeStr = generatorReturnTypeStr(returnType);        // 返回类型字符串
            this.argsStr = generatorArgsStr(parameterTypes);        // invoke方法对象数组
        }

        private String generatorParametersStr(Class<?>[] parameterTypes) {
            if (parameterTypes == null || parameterTypes.length == 0) {
                return "";
            }

            StringBuffer sb = new StringBuffer();
            int length = parameterTypes.length;
            for (int i = 0; i < length; i++) {
                //  com.User args0, com.User args1, ...
                sb.append(parameterTypes[i].getName()).append(" ").append("args").append(i).append(", ");
            }


            return sb.substring(0, sb.lastIndexOf(",")).toString();
        }

        /**
         * 生成invocationHandler所需要的对象数组
         *
         * @param parameterTypes
         * @return
         */
        private String generatorArgsStr(Class<?>[] parameterTypes) {
            if (parameterTypes == null || parameterTypes.length == 0) {
                return "null";
            }

            StringBuffer sb = new StringBuffer("new Object[]{");
            int length = parameterTypes.length;

            for (int i = 0; i < length; i++) {
                sb.append("args").append(i).append(", ");
            }

            return sb.substring(0, sb.lastIndexOf(","))+"}";
        }


        private String generatorMethod() {
            String returnTypeName = returnType.getName();
            /**
             * public void test(com.git.dao.TestDao args0) throws java.lang.Exception{
             * 		try{
             * 		super.h.invoke(this,m0,new Object[]{args0});
             *                }catch(Throwable throwable){
             * 		throwable.printStackTrace();
             *        }
             * }
             */
            // public com.test.User test(java.lang.String) throws java.lang.Exception
            methodContent.append("public ").append(returnTypeName)
                    .append(" ").append(methodName).append("(").append(parametersStr).append(")")
                    .append(exceptionStr).append("{").append(line)
                    // 捕获异常
                    .append(tab).append(tab).append(tab).append("try{").append(line)
                    // return (java.lang.Integer)super.h.invoke(this,m1,new Class[]{})
                    .append(tab).append(tab).append(tab).append(tab).append(returnTypeStr).append("super.h.invoke(this,")
                    .append(methodFieldName).append(",").append(argsStr).append(");").append(line)
                    .append(tab).append(tab).append(tab).append("}").append(tab).append("catch(Throwable throwable){").append(line)
                    .append(tab).append(tab).append(tab).append(tab).append("throwable.printStackTrace();").append(line)
                    .append(tab).append(tab).append(tab).append("}").append(line).append(tab).append(tab)
                    .append(tab).append(tab).append(finallyReturnStr)
                    .append(line).append(tab).append(tab).append("}")
                    .append(line).append(line);

            return methodContent.toString();
        }


        private String generatorReturnTypeStr(Class<?> returnType) {
            if (returnType == void.class || returnType == Void.class) {
                return "";
            }




            finallyReturnStr = " throw new IllegalStateException(\" 返回类型: "+returnType.getName()+" 发生错误"+"\");";  // 用于存在异常时使用

            return "return (" + returnType.getName() + ")";

        }


        private String generatorDescriptor(Class<?>[] parameterTypes) {

            if (parameterTypes == null || parameterTypes.length == 0) {

                return "";
            }

            StringBuffer sb = new StringBuffer();
            for (Class<?> parameterType : parameterTypes) {
                String parameterTypeName = parameterType.getName();
                sb.append(parameterTypeName).append(",");
            }


            return sb.substring(0, sb.lastIndexOf(",")).toString();
        }


       /* // 将方法上声明的异常类型全部导入
        private String importPackage(Class<?>[] exceptionTypes) {

            if(exceptionTypes == null || exceptionTypes.length == 0){

                return "";
            }

            StringBuffer importPackage = new StringBuffer();

            for (Class<?> exceptionType : exceptionTypes) {
                String className = exceptionType.getName();
                importPackage.append("import ").append(className).append(";").append(line);
            }

            return importPackage.toString();
        }*/


        // 处理方法上声明的异常  throws  Exception
        private String exceptionStr(Class<?>[] exceptionTypes) {
            if (exceptionTypes == null || exceptionTypes.length == 0) {
                return "";
            }

            StringBuilder sb = new StringBuilder(" throws ");

            for (Class<?> exceptionType : exceptionTypes) {
                // 添加异常
                sb.append(exceptionType.getName()).append(", ");
            }


            return sb.substring(0, sb.lastIndexOf(",")).toString();
        }

        public String getMethodFieldName() {
            return methodFieldName;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public Class<?>[] getExceptionTypes() {
            return exceptionTypes;
        }

        public Class<?> getFormClass() {
            return formClass;
        }

        public String getMethodName() {
            return methodName;
        }
    }


    // 代理类中的方法的信息

    /**
     * 方法属性名称  参数列表  目标对象的Class对象
     */
    private class MethodInfo {
        private String name;        // 方法名称
        private Class<?>[] parameterTypes;    // 方法形参列表
        private Class<?> formClass;   // 接口Class对象
        private String methodFieldName; // 方法对象属性名称
        private String parameterTypesStr;


        private StringBuffer methodContent = new StringBuffer();        // 将信息组成内容




        /**
         *
         * @param name  方法名称
         * @param methodFieldName       方法属性对象名称
         * @param fromClass        接口Class对象
         * @param parameterTypes     参数列表
         */
        public MethodInfo(String name,String methodFieldName,Class<?> fromClass, Class<?>[] parameterTypes) {
            this.name = name;
            this.methodFieldName = methodFieldName;
            this.formClass = fromClass;
            this.parameterTypes = parameterTypes;
            this.parameterTypesStr = generatorParameterTypeStr(parameterTypes);

        }

        private String generatorParameterTypeStr(Class<?>[] parameterTypes) {
            if(parameterTypes == null || parameterTypes.length == 0){

                return "new Class[0]";
            }
            StringBuffer sb = new StringBuffer("new Class[]{");

            for (Class<?> parameterType : parameterTypes) {
                if(table.get(parameterType) != null){
                    // int.class
                    sb.append(parameterType.getName()).append(".class").append(",");
                    continue;
                }
                sb.append("Class.forName(\"").append(parameterType.getName()).append("\"),");
            }


            return sb.substring(0,sb.lastIndexOf(","))+"}";
        }

        /**
         *  生成static块内容
         * @return
         */
        private String generatorMethodInfo(){

            StringBuffer sb = new StringBuffer();

            sb.append(tab).append(tab).append(tab).append(tab).append(tab)
              .append(methodFieldName).append(" = ").append("Class.forName(\"").append(formClass.getName()).append("\")")
              .append(".getMethod(\"").append(name).append("\",").append(parameterTypesStr).append(");")
              .append(line).append(tab).append(tab).append(tab).append(tab);



            return sb.toString();
        }




        private String generatorDescriptor(Class<?>[] parameterTypes) {

            if (parameterTypes == null || parameterTypes.length == 0) {

                return "";
            }

            StringBuffer sb = new StringBuffer();
            for (Class<?> parameterType : parameterTypes) {
                String parameterTypeName = parameterType.getName();
                sb.append(parameterTypeName).append(",");
            }


            return sb.substring(0, sb.lastIndexOf(",")).toString();
        }


    }


    /**
     *  定义代理类中的方法对象属性
     */
    private class FieldInfo{

        private String fieldType = "java.lang.reflect.Method";
        private String fieldName;

        public FieldInfo(String fieldName) {
            this.fieldName = fieldName;
        }

        private String GeneratorMethodField(){
            StringBuffer sb = new StringBuffer();
            sb.append(tab).append(tab).append("static ").append(fieldType).append(" ").append(fieldName).append(";").append(line);
            return sb.toString();
        }


    }


}
