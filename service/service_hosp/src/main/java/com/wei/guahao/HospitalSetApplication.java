package com.wei.guahao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
//开启nacos
@EnableDiscoveryClient

//开启feign远程调用
@EnableFeignClients(basePackages = "com.wei")
public class HospitalSetApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalSetApplication.class,args);
    }

}
