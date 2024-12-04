package org.example.proflow;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class ProFlowApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(ProFlowApplication.class, args);
    }

}



