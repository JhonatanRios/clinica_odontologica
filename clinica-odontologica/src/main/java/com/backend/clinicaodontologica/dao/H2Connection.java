package com.backend.clinicaodontologica.dao;

import java.sql.*;

public class H2Connection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/clinicaC1", "sa", "sa");
    }
}