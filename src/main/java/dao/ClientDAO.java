/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Client;

public class ClientDAO extends AbstractDAO<Client, Integer> {

    /**
     * Insere um novo cliente no banco de dados e retorna o ID gerado.
     *
     */
    @Override
    public Integer save(Client client) {
        String sql = "INSERT INTO client (name, cpf, phone, total_points) VALUES (?, ?, ?, ?)";

        Integer generatedId = executeInsert(sql, stmt -> {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCpf());
            stmt.setString(3, client.getPhone());
            stmt.setInt(4, client.getTotalPoints());
        }, "ClientDAO", "save");

        return generatedId;
    }

    /**
     * Busca um cliente no banco de dados com base no seu identificador.
     *
     */
    @Override
    public Client findById(Integer id) {
        String sql = "SELECT * FROM client WHERE id = ?";

        return executeQuery(sql, stmt -> stmt.setInt(1, id), result -> {
            if (result.next()) {
                return mapResultSetToClient(result);
            }
            return null;
        }, "ClientDAO", "findById");
    }

    /**
     * Retorna todos os clientes cadastrados no banco de dados, ordenados por ID.
     *
     */
    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM client ORDER BY id";

        return executeQuery(sql, stmt -> {}, result -> {
            List<Client> clients = new ArrayList<>();
            while (result.next()) {
                clients.add(mapResultSetToClient(result));
            }
            return clients;
        }, "ClientDAO", "findAll");
    }

    /**
     * Atualiza os dados de um cliente existente com base em seu ID.
     *
     */
    @Override
    public void update(Client client, Integer id) {
        String sql = "UPDATE client SET name = ?, cpf = ?, phone = ?, total_points = ? WHERE id = ?";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCpf());
            stmt.setString(3, client.getPhone());
            stmt.setInt(4, client.getTotalPoints());
            stmt.setInt(5, id);
        }, "ClientDAO", "update");
    }

    /**
     * Exclui um cliente do banco de dados com base no ID informado.
     *
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM client WHERE id = ?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id), "ClientDAO", "delete");
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setName(rs.getString("name"));
        client.setCpf(rs.getString("cpf"));
        client.setPhone(rs.getString("phone"));
        client.setTotalPoints(rs.getInt("total_points"));
        return client;
    }

    //Filtra clientes de forma dinâmica com base em parâmetros opcionais (ID, nome e CPF).
    public List<Client> filter(int id, String name, String cpf) {
        List<Object> parameters = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM client WHERE 1=1");

        if (id > 0) {
            sql.append(" AND id = ?");
            parameters.add(id);
        }

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND name ILIKE ?");
            parameters.add("%" + name + "%");
        }

        if (cpf != null && !cpf.trim().isEmpty()) {
            sql.append(" AND cpf ILIKE ?");
            parameters.add("%" + cpf + "%");
        }

        sql.append(" ORDER BY id");

        return executeQuery(sql.toString(), stmt -> {
            int i = 1;
            for (Object param : parameters) {
                setStatementParameter(stmt, i++, param);
            }
        }, result -> {
            List<Client> clients = new ArrayList<>();
            while (result.next()) {
                clients.add(mapResultSetToClient(result));
            }
            return clients;
        }, "ClientDAO", "filter");
    }
    
    /**
     * Atualiza apenas o total de pontos de um cliente específico.
     *
     */
    public void updateTotalPoints(Client client) {
    String sql = "UPDATE client SET total_points = ? WHERE id = ?";
    executeUpdate(sql, stmt -> {
        stmt.setInt(1, client.getTotalPoints());
        stmt.setInt(2, client.getId());
    }, "ClientDAO", "updateTotalPoints");
   }
}
