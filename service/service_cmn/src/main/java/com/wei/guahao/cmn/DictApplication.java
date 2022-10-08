package com.wei.guahao.cmn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//定义扫描的路径从中找出 标识了需要装配的类 自动装配到spring的bean容器中 比如Swagger2
@ComponentScan(basePackages = {"com.wei"})

//一定要设置mapper的扫描路径
@MapperScan(value = {"com.wei.guahao.cmn.mapper"})
//开启nacos
@EnableDiscoveryClient
public class DictApplication {


    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class,args);
    }
}
