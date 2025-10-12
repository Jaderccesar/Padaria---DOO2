/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exception.AppException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.ConnectionFactory;

/**
 *
 * @author Usuario
 */
public abstract class AbstractDAO<T, ID> implements GenericDAO<T, ID> {
    
    private final LoggerDAO logger = new LoggerDAO();
    private final String usuario = "admin";
    
    protected Integer executeInsert(String sql, SQLConsumer<PreparedStatement> consumer, String classe, String metodo) {
        
        Integer generatedId = null;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

             consumer.accept(stmt); 
             stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            logger.salvarLog(usuario, classe, metodo, null, e); 
            throw new AppException("Erro ao executar insert e retornar ID", e);
        }

        return generatedId; 
    }
    protected void executeUpdate(String sql, SQLConsumer<PreparedStatement> consumer, String classe, String metodo) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            consumer.accept(stmt);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.salvarLog(usuario, classe, metodo, null,e);
            throw new AppException("Erro ao executar update", e);
        }
    }

    protected <R> R executeQuerySingle(String sql, SQLConsumer<PreparedStatement> consumer,
                                       SQLFunction<ResultSet, R> mapper, String classe, String metodo) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            consumer.accept(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapper.apply(rs);
            }

        } catch (SQLException e) {
            logger.salvarLog(usuario, classe, metodo,null,e);
            throw new AppException("Erro ao executar query", e);
        }
    }

    protected <R> R executeQuery(String sql, SQLConsumer<PreparedStatement> consumer,
                                 SQLFunction<ResultSet, R> mapper, String classe, String metodo) {
        return executeQuerySingle(sql, consumer, mapper, classe, metodo);
    }

    @FunctionalInterface
    protected interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    @FunctionalInterface
    protected interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }
    
     protected void setStatementParameter(PreparedStatement stmt, int index, Object param) throws SQLException {
   
        switch (param) {
            case String s -> stmt.setString(index, s);
            case Integer i -> stmt.setInt(index, i);
            case Double d -> stmt.setDouble(index, d);
            case Boolean b -> stmt.setBoolean(index, b);
            case Long l -> stmt.setLong(index, l);
            case null, default -> {
                stmt.setObject(index, param);
            }
    }
}   

}
