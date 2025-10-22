/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Users;

public class UsersDAO extends AbstractDAO<Users, Integer> {

    /**
     * Salva um novo usuário no banco de dados.
     */
    @Override
    public Integer save(Users user) {
        String sql = "INSERT INTO users (username, password, is_admin) VALUES (?, ?, ?)";

        // Executa o insert usando o método genérico do AbstractDAO
        Integer generatedId = executeInsert(sql, stmt -> {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isAdmin());
        }, "UserDAO", "save");

        return generatedId;
    }

    /**
     * Busca um usuário pelo seu ID.
     */
    @Override
    public Users findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        return executeQuery(sql, stmt -> stmt.setInt(1, id), result -> {
            if (result.next()) {
                return mapResultSetToUser(result);
            }
            return null;
        }, "UserDAO", "findById");
    }

    /**
     * Retorna todos os usuários cadastrados no banco.
     */
    @Override
    public List<Users> findAll() {
        String sql = "SELECT * FROM users ORDER BY id";

        return executeQuery(sql, stmt -> {}, result -> {
            List<Users> users = new ArrayList<>();
            while (result.next()) {
                users.add(mapResultSetToUser(result));
            }
            return users;
        }, "UserDAO", "findAll");
    }

    /**
     * Atualiza os dados de um usuário existente.
     */
    @Override
    public void update(Users user, Integer id) {
        String sql = "UPDATE users SET username = ?, password = ?, is_admin = ? WHERE id = ?";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isAdmin());
            stmt.setInt(4, id);
        }, "UserDAO", "update");
    }

    /**
     * Remove um usuário pelo ID.
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id), "UserDAO", "delete");
    }

    /**
     * Mapeia os dados de um ResultSet para um objeto Users.
     */
    private Users mapResultSetToUser(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setAdmin(rs.getBoolean("is_admin"));
        return user;
    }

    /**
     * Busca um usuário pelo username e password para autenticação.
     */
    public Users findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        return executeQuery(sql, stmt -> {
            stmt.setString(1, username);
            stmt.setString(2, password);
        }, result -> {
            if (result.next()) {
                return mapResultSetToUser(result);
            }
            return null;
        }, "UserDAO", "findByUsernameAndPassword");
    }
}
