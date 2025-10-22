/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class ConnectionFactory {
    
    //private static final String URL = "jdbc:postgresql://localhost:5432/padaria";
    //private static final String USER = "postgres";
    //private static final String PASSWORD = "padariadanieljaderleandro12";

    //private static final String URL = "jdbc:postgresql://localhost:5432/postgres";;
    //private static final String USER = "postgres";
    //private static final String PASSWORD = "jader_1208";
    
    private static final String URL = "jdbc:postgresql://localhost:5432/teste";;
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    
     public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
}
