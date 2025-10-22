/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import util.ConnectionFactory;

/**
 *
 * @author Usuario
 */
public class LoggerDAO {

    //Método responsavel por salvar logs no banco
    public void salvarLog(String usuario, String classe, String metodo, String messageLog, Exception e) {
        String sql = "INSERT INTO log_errors (log_user , class_name , method_name , message , stacktrace , log_timestamp ) VALUES (?, ?, ?, ?, ?, ?)";

        LocalDateTime agora = LocalDateTime.now();

        try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            // Define os parâmetros do log
            stmt.setString(1, usuario);
            stmt.setString(2, classe);
            stmt.setString(3, metodo);

            // Caso exista uma exceção, salva a mensagem e o stacktrace
            if (e != null) {
                stmt.setString(4, e.getMessage() != null ? e.getMessage() : messageLog);
                stmt.setString(5, Arrays.toString(e.getStackTrace()));
            } else {
                stmt.setString(4, messageLog);
                stmt.setString(5, null);
            }

            // Define o horário exato do log
            stmt.setTimestamp(6, Timestamp.valueOf(agora));
            
            // Executa a inserção no banco
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // Caso ocorra falha ao registrar o log, exibe no console
            System.err.println("Erro ao salvar log: " + ex.getMessage());
        }
    }
}
