/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author Daniel Coelho - PAD 1 e 2 - New - "Adicionado o CRUD de um PRODUTO" - Sprint 1
 */
public class ProductDAO extends AbstractDAO<Product, Integer> {
    
    @Override
    public Integer save(Product product) {
        String sql = "INSERT INTO product (name, price, type, stock_quantity, point_cost) VALUES (?, ?, ?, ?, ?)";

        Integer generatedId = executeInsert(sql, stmt -> {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getType());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getPointCost());
        }, "ProductDAO", "save");

        return generatedId;
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return executeQuery(sql, stmt -> stmt.setInt(1, id), result -> {
            if (result.next()) {
                return mapResultSetToProduct(result);
            }
            return null;
        }, "ProductDAO", "findById");
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product ORDER BY id";
        return executeQuery(sql, stmt -> {}, result -> {
            List<Product> products = new ArrayList<>();
            while (result.next()) {
                products.add(mapResultSetToProduct(result));
            }
            return products;
        }, "ProductDAO", "findAll");
    }

    @Override
    public void update(Product product, Integer id) {
        String sql = "UPDATE product SET name = ?, price = ?, type = ?, stock_quantity = ?, point_cost = ? WHERE id = ?";
        executeUpdate(sql, stmt -> {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getType());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getPointCost());
            stmt.setInt(6, id);
        }, "ProductDAO", "update");
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM product WHERE id = ?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id), "ProductDAO", "delete");
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
    
        Product product = new Product();  

        product.setId(rs.getInt("id"));                      
        product.setName(rs.getString("name"));               
        product.setPrice(rs.getDouble("price"));             
        product.setType(rs.getString("type"));               
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setPointCost(rs.getInt("point_cost"));        

        return product;  
    }
    
    public List<Product> filter(int id, String name, String type) {
    
        List<Object> parameters = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        
        if (id > 0) {
            sql.append(" AND id = ?");
            parameters.add(id);
        }
         
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND name ILIKE ?");
            parameters.add("%" + name + "%");
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND type = ?");
            parameters.add(type);
        }


        sql.append(" ORDER BY id");

        return executeQuery(sql.toString(), stmt -> {
            int i = 1;
            for (Object param : parameters) { 

                setStatementParameter(stmt, i++, param);
            }
        }, result -> { 
            List<Product> products = new ArrayList<>();
            while (result.next()) {
                 products.add(mapResultSetToProduct(result)); 
            }
            return products;
        }, "ProductDAO", "filter");
    }
}
