package org.arvik.zafkielbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final Connection cnx;

    public Database() {
        try {
            cnx = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/zafkiel-bot?serverTimezone=UTC",
                    "root",
                    "");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }
}