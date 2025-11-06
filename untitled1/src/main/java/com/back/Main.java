package com.back;

import com.back.global.app.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Spring Boot ApplicationContext 초기화
            ApplicationContext context = SpringApplication.run(AppConfig.class, args);
            App app = context.getBean(App.class);
            
            // Scanner 설정
            Scanner scanner = new Scanner(System.in);
            app.setScanner(scanner);
            
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}