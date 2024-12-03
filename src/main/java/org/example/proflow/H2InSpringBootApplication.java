package org.example.proflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class H2InSpringBootApplication {

    public static void main(String[] args){
        ConfigurableApplicationContext run = SpringApplication.run(H2InSpringBootApplication.class, args);
        System.out.println(run.getBean("dataSource"));
    }
}
