package com.moonproof.clientexperements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.moonproof")
public class ClientExperimentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientExperimentsApplication.class, args);
    }

}
