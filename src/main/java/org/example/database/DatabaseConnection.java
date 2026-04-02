package org.example.database;

import java.sql.Connection;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/proyecto_biblioteca";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "zeusgorka1";

    public static Connection conectar (){
        try {
            Connection connection = java.sql.DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
            return connection;
        } catch (java.sql.SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
