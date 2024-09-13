package com.beautysalon.beautysalonsystem.model.tools;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcProvider {
    @Getter
    private static JdbcProvider jdbcProvider = new JdbcProvider();

    private JdbcProvider() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "javaee",
                "java123"
        );
    }
}
