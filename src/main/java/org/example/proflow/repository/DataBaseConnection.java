package org.example.proflow.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

        private static String db_url = System.getenv("DB_URL");
        private static String db_username = System.getenv("DB_USER");
        private static String db_password = System.getenv("DB_PASSWORD");

        protected DataBaseConnection() {
        }

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(db_url, db_username, db_password);
        }

}
