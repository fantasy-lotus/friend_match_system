package com.lotus;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lotus.mapper")
public class BootStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStartApplication.class, args);
    }

}
