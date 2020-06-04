package com.git.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.git")
@EnableAspectJAutoProxy(proxyTargetClass = false)     // 启用动态代理
// Spring AOP有两种动态代理的方式,一种是JDK Proxy 一种是Cglib 默认使用的是JDK PROXY ,
// 可以通过设置属性值proxyTargetClass为true,使用Cglib动态代理
public class AppConfig {
}
