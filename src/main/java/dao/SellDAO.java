/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Client;
import model.Sell;
import model.Product;
import util.ConnectionFactory;

/**
 *
 * @author Usuario
 */
public class SellDAO extends AbstractDAO<Sell, Integer> {

    @Override
    public Integer save(Sell sell) {
        String sql = "INSERT INTO sell (client_id, sell_date, total, price) VALUES (?, ?, ?, ?)";

        Integer generatedId = executeInsert(sql, stmt -> {
            stmt.setInt(1, sell.getClient().getId());
            stmt.setTimestamp(2, new java.sql.Timestamp(sell.getSellDate().getTime()));
            stmt.setDouble(3, sell.getTotal());
            stmt.setDouble(4, sell.getPrice());
        }, "SellDAO", "save");

        for (Product product : sell.getProducts()) {
            int quantity = sell.getQuantity(product);

            String sqlProduct = "INSERT INTO sell_product (sell_id, product_id, quantity, price) VALUES(?, ?, ?, ?)";
            executeInsert(sqlProduct, stmt -> {
                stmt.setInt(1, generatedId);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, quantity);
                stmt.setDouble(4, product.getPrice());
            }, "SellDAO", "saveProduct");
        }
        
        applyLoyaltyPoints(sell);

        return generatedId;
    }

    @Override
    public Sell findById(Integer id) {
        String sql = "SELECT * FROM sell WHERE id = ?";
        return executeQuery(sql, stmt -> stmt.setInt(1, id), result -> {
            if (result.next()) {
                return mapResultSetToSell(result);
            }
            return null;
        }, "SellDAO", "findById");
    }

    @Override
    public List<Sell> findAll() {
        String sql = "SELECT * FROM sell ORDER BY id";
        return executeQuery(sql, stmt -> {
        }, result -> {
            List<Sell> sells = new ArrayList<>();
            while (result.next()) {
                sells.add(mapResultSetToSell(result));
            }
            return sells;
        }, "SellDAO", "findAll");
    }

    @Override
    public void update(Sell sell, Integer id) {
        String sql = "UPDATE sell SET client_id = ?, sell_date = ?, total = ?, price = ? WHERE id = ?";
        executeUpdate(sql, stmt -> {
            stmt.setInt(1, sell.getClient().getId());
            stmt.setTimestamp(2, new java.sql.Timestamp(sell.getSellDate().getTime()));
            stmt.setDouble(3, sell.getTotal());
            stmt.setDouble(4, sell.getPrice());
            stmt.setInt(5, id);
        }, "SellDAO", "update");
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM sell WHERE id = ?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id), "SellDAO", "delete");
    }

    private Sell mapResultSetToSell(ResultSet rs) throws SQLException {
        Sell sell = new Sell();
        sell.setId(rs.getInt("id"));
        sell.setSellDate(rs.getTimestamp("sell_date"));
        sell.setTotal(rs.getDouble("total"));
        sell.setPrice(rs.getDouble("price"));

        String sqlProducts = "SELECT p.*, sp.quantity FROM product p "
                + "JOIN sell_product sp ON p.id = sp.product_id "
                + "WHERE sp.sell_id = ?";
        List<Product> products = executeQuery(sqlProducts, stmt -> stmt.setInt(1, sell.getId()), result -> {
            List<Product> list = new ArrayList<>();
            while (result.next()) {
                Product p = new Product();
                p.setId(result.getInt("id"));
                p.setName(result.getString("name"));
                p.setPrice(result.getDouble("price"));
                list.add(p);
            }
            return list;
        }, "SellDAO", "mapProducts");

        sell.setProducts((ArrayList<Product>) products);
        return sell;
    }

    public List<Sell> filter(Integer id, String clientName, String dateStart, String dateEnd) {
        List<Sell> sells = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT s.*, c.name AS client_name "
                + "FROM sell s "
                + "JOIN client c ON s.client_id = c.id "
                + "WHERE 1=1"
        );

        if (id != null) {
            sql.append(" AND s.id = ?");
            params.add(id);
        }
        if (clientName != null) {
            sql.append(" AND c.name ILIKE ?");
            params.add("%" + clientName + "%");
        }
        if (dateStart != null) {
            sql.append(" AND s.sell_date >= ?");
            params.add(Timestamp.valueOf(dateStart));
        }
        if (dateEnd != null) {
            sql.append(" AND s.sell_date <= ?");
            params.add(Timestamp.valueOf(dateEnd));
        }

        sql.append(" ORDER BY s.id");

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                } else if (param instanceof Timestamp) {
                    ps.setTimestamp(i + 1, (Timestamp) param);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sells.add(mapResultSetToSell(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao filtrar vendas: " + e.getMessage(), e);
        }

        return sells;
    }
    
    private void applyLoyaltyPoints(Sell sell) {
    if (sell == null || sell.getClient() == null) return;

    int earnedPoints = (int) (sell.getTotal() / 10);

    if (earnedPoints > 0) {
        Client client = sell.getClient();
        int currentPoints = client.getTotalPoints() != null ? client.getTotalPoints() : 0;
        client.setTotalPoints(currentPoints + earnedPoints);

        ClientDAO clientDAO = new ClientDAO();
        clientDAO.updateTotalPoints(client);
    }
   }
    
}