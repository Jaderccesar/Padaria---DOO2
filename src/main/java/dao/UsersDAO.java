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

/**
 *
 * @author Daniel Coelho
 */
public class UsersDAO extends AbstractDAO<Users, Integer> {

    @Override
    public Integer save(Users user) {
        String sql = "INSERT INTO users (username, password, is_admin) VALUES (?, ?, ?)";

        Integer generatedId = executeInsert(sql, stmt -> {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isAdmin());
        }, "UserDAO", "save");

        return generatedId;
    }

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

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id), "UserDAO", "delete");
    }

    private Users mapResultSetToUser(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setAdmin(rs.getBoolean("is_admin"));
        return user;
    }

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
