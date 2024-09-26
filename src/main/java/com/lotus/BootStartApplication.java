package com.lotus;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lotus.mapper")
@EnableScheduling
public class BootStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStartApplication.class, args);
    }

}
