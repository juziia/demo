package com.juzi.proxy;
import java.lang.reflect.*;
import com.git.dao.TestDao;


public class $Proxy0 extends com.git.simulate.Proxy implements com.git.dao.TestDao{


		static java.lang.reflect.Method m2;

				static java.lang.reflect.Method m0;

				static java.lang.reflect.Method m1;

				static java.lang.reflect.Method m3;

				static java.lang.reflect.Method m4;

		

		public $Proxy0(com.git.simulate.InvocationHandler invocationHandler){
			super(invocationHandler);
		}

		public java.lang.Integer testInteger(java.lang.Integer args0){
			try{
				return (java.lang.Integer)super.h.invoke(this,m2,new Object[]{args0});
			}	catch(Throwable throwable){
				throwable.printStackTrace();
			}
				 throw new IllegalStateException(" 返回类型: java.lang.Integer 发生错误");
		}



		public void test(com.git.dao.TestDao args0) throws java.lang.Exception{
			try{
				super.h.invoke(this,m0,new Object[]{args0});
			}	catch(Throwable throwable){
				throwable.printStackTrace();
			}
				
		}



		public java.lang.Integer test(){
			try{
				return (java.lang.Integer)super.h.invoke(this,m1,null);
			}	catch(Throwable throwable){
				throwable.printStackTrace();
			}
				 throw new IllegalStateException(" 返回类型: java.lang.Integer 发生错误");
		}



		public int testInt(int args0){
			try{
				return (int)super.h.invoke(this,m3,new Object[]{args0});
			}	catch(Throwable throwable){
				throwable.printStackTrace();
			}
				 throw new IllegalStateException(" 返回类型: int 发生错误");
		}



		public com.juzi.User findByUser(){
			try{
				return (com.juzi.User)super.h.invoke(this,m4,null);
			}	catch(Throwable throwable){
				throwable.printStackTrace();
			}
				 throw new IllegalStateException(" 返回类型: com.juzi.User 发生错误");
		}



		static {
				try{
					m2 = Class.forName("com.git.dao.TestDao").getMethod("testInteger",new Class[]{Class.forName("java.lang.Integer")});
				
									m0 = Class.forName("com.git.dao.TestDao").getMethod("test",new Class[]{Class.forName("com.git.dao.TestDao")});
				
									m1 = Class.forName("com.git.dao.TestDao").getMethod("test",new Class[0]);
				
									m3 = Class.forName("com.git.dao.TestDao").getMethod("testInt",new Class[]{int.class});
				
									m4 = Class.forName("com.git.dao.TestDao").getMethod("findByUser",new Class[0]);
				
				}
		catch (NoSuchMethodException nosuchmethodexception)
		{
			throw new NoSuchMethodError(nosuchmethodexception.getMessage());
		}
		catch (ClassNotFoundException classnotfoundexception)
		{
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
	}
}
