package com.wei.guahao.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//这是一个配置类
@Configuration

//扫描指定的mapper
@MapperScan("com.wei.guahao.mapper")

//定义扫描的路径从中找出 标识了需要装配的类 自动装配到spring的bean容器中
@ComponentScan(basePackages = "com.wei")
public class HospitalSetConfig {
}
