package org.example.proflow.repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
//    private static String db_url = System.getenv("DB_URL");
//    private static String db_username = System.getenv("DB_USER");
//    private static String db_password = System.getenv("DB_PASSWORD");

    @Value("${spring.datasource.url}")
    private static String dev_db_url;// henter fra environment variables som er lagret i TouristGuideApplication
    @Value("${spring.datasource.name}")
    private static String dev_db_user;
    @Value("${spring.datasource.password}")
    private static String dev_db_password;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    protected DataBaseConnection() { //hvorfor protected?
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    public static Connection getConnection() throws SQLException { //Hvorfor static?
        return DriverManager.getConnection(dev_db_url, dev_db_user, dev_db_password);
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
