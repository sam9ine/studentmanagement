package com.project.db;

import java.sql.*;

public class DBconnection {
    public static Connection getConnection()throws Exception{
        String url="jdbc:mysql://localhost:3306/studentdb";
        String user ="root";
        String password ="root";

        return DriverManager.getConnection(url,user,password);
    }

}
