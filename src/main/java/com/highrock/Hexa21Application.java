package com.highrock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.highrock.mapper")
public class Hexa21Application {
    public static void main(String[] args) {
        SpringApplication.run(Hexa21Application.class, args);
    }
}