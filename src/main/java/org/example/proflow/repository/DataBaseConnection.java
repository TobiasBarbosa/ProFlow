package org.example.proflow.repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
//    private static String dev_db_url = System.getenv("DEV_DB_URL");
//    private static String dev_db_user = System.getenv("DEV_DB_USER");
//    private static String dev_db_password = System.getenv("DEV_DB_PASSWORD");

   private static String dev_db_url = "jdbc:h2:mem:testdb;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;INIT=runscript from 'src/main/resources/h2.sql'";
   private static String dev_db_user = "sa";
   private static String dev_db_password = "";

//    @Value("${spring.datasource.url}")
//    private static String dev_db_url;// henter fra environment variables som er lagret i TouristGuideApplication
//    @Value("${spring.datasource.name}")
//    private static String dev_db_user;
//    @Value("${spring.datasource.password}")
//    private static String dev_db_password;

    //***CONSTRUCTOR***-------------------------------------------------------------------------------------------------
    public DataBaseConnection() { //hvorfor protected?
    }

    //***METHODS***-----------------------------------------------------------------------------------------------------
    public static Connection getConnection() throws SQLException { //Hvorfor static?
        return DriverManager.getConnection(dev_db_url, dev_db_user, dev_db_password);
    }

    //***END***---------------------------------------------------------------------------------------------------------
}
