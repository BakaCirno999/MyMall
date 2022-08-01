package com.mymall.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mymall.mall.mapper")
@SpringBootApplication
public class MyMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMallApplication.class, args);
    }

}
