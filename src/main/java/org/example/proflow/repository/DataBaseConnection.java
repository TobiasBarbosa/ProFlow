package org.example.proflow.repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    private static String dev_db_url = System.getenv("DEV_DB_URL");
    private static String dev_db_user = System.getenv("DEV_DB_USER");
    private static String dev_db_password = System.getenv("DEV_DB_PASSWORD");

//    @Value("${spring.datasource.url}")
//    private static String dev_db_url;// henter fra environment variables som er lagret i TouristGuideApplication
//    @Value("${spring.datasource.name}")
//    private static String dev_db_user;
//    @Value("${spring.datasource.password}")
//    private static String dev_db_password;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    protected DataBaseConnection() { //hvorfor protected?
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    public static Connection getConnection() throws SQLException { //Hvorfor static?
        return DriverManager.getConnection(dev_db_url, dev_db_user, dev_db_password);
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
