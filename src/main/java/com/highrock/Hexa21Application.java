package com.highrock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class Hexa21Application {
    public static void main(String[] args) {
        SpringApplication.run(Hexa21Application.class, args);
    }
}