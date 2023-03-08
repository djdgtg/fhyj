package com.fhyj.safe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fhyj.safe.mapper")
public class SafeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SafeApplication.class, args);
    }
}
