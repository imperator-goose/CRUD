package com.ruslan.utils;



import java.sql.*;

public class JdbcUtils {

    private static Connection connection;


    public static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/jdbc",
                        "root",
                        "root");
            } catch (SQLException e) {
                System.exit(1);
            }
        }

        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }


    public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}