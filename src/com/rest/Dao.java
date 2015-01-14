package com.rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by VIRAL on 1/13/2015.
 */
public class Dao {

    private static DataSource dataSource = null;
    private static Context context = null;

    public static class ConnectionUtil {

        private static final String driver = "com.mysql.jdbc.Driver";
        private static final String url = "jdbc:mysql://localhost:3306/restdatabase";
        public static Connection con = null;

        public static Connection getCon() {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, "root", "viral");
            } catch (ClassNotFoundException e) {
                System.out.println("Error in Class.forName " + e);
            } catch (SQLException e) {
                System.out.println("Error in con " + e);
            }
            return con;
        }
    }
}
