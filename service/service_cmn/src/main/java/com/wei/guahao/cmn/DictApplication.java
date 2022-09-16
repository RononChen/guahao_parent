package com.wei.guahao.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wei.guahao"})
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class,args);
    }
}
