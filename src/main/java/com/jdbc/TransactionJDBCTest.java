package com.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionJDBCTest {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionJDBCTest.class);

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);  //开启事务
        PreparedStatement preparedStatement = connection.prepareStatement("");
        preparedStatement.executeUpdate();
        connection.commit();    //提交事务
        connection.close();
    }

    private static Connection getConnection() throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql:localhost//";
        String username = "root";
        String password = "root";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }
        return DriverManager.getConnection(url,username,password);
    }
}
