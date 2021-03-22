package com.example.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/genesis",
                    "root",
                    "root");
        }catch (Exception e){
            System.out.println(e);
        }
        return connection;
    }

}
