package com.library.management.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class LibraryManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApp.class, args);
    }

}
