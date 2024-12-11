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

    //***TO DO***--------------------------------------------------------------------------------------------------
    //***ATTRIBUTES***--------------------------------------------------------------------------------------------------
//    private static String dev_db_url = System.getenv("DEV_DB_URL");
//    private static String dev_db_user = System.getenv("DEV_DB_USER");
//    private static String dev_db_password = System.getenv("DEV_DB_PASSWORD");

//   private static String db_url = "jdbc:h2:mem:testdb;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;INIT=runscript from 'src/main/resources/h2.sql'";
//   private static String db_user = "sa";
//   private static String db_password = "";

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
