package org.example.proflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataBaseConnection {

    //***TO DO***-------------------------------------------------------------------------------------------------------
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    @Value("${spring.datasource.url}")
    private String db_url;// henter fra environment variables som er lagret i TouristGuideApplication
    @Value("${spring.datasource.username}")
    private String db_user;
    @Value("${spring.datasource.password}")
    private String db_password;

    //***METHODS***-----------------------------------------------------------------------------------------------------
    public Connection getConnection() throws SQLException { //Hvorfor static
        try {
        return DriverManager.getConnection(db_url, db_user, db_password);
        }catch (SQLException e){
            throw new SQLException("Error connecting to the database", e);
        }
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
