package com.apergot.springbootrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootRestapiApplication extends SpringBootServletInitializer { //implements CommandLineRunner

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(SpringBootRestapiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestapiApplication.class, args);
    }
    /*
    //Simplest way to create passwords for users using bycrypt, needs to implement CommandLineRunner
    @Override
    public void run(String... args) {
        String password = "";
        for (int i = 0; i< 4; i++) {
            String passwordBcrypt = passwordEncoder.encode(password);
            System.out.println(passwordBcrypt);
        }
    }*/
}
