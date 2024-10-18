/**
 * Auf dieser Seite stellen wir eine Verbindung zur Datenbank über Mysql her. 
 * Hier müssen Datenbank-Url, Benutzername und Passwort korrekt angegeben werden.
 * @author erkan
 */
package com.mycompany.mavenproject8.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/findinworld";  // Datenbank URL
    private static final String USERNAME = "root";  // Datenbank Username
    private static final String PASSWORD = "12345";  // Datenbank kennword

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL JDBC
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
