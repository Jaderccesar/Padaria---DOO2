/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import model.Client;
import model.Sell;
import model.Product;

/**
 *
 * @author Usuario
 */
public class SellDAO extends AbstractDAO<Sell, Integer> {

    /**
     * Salva uma nova venda no banco de dados, incluindo os produtos vendidos.
     * Também aplica pontos de fidelidade ao cliente.
     *
     */
    @Override
    public Integer save(Sell sell) {
        String sql = "INSERT INTO sell (client_id, sell_date, total, price) VALUES (?, ?, ?, ?)";

        Integer generatedId = executeInsert(sql, stmt -> {
            stmt.setInt(1, sell.getClient().getId());
            stmt.setTimestamp(2, new java.sql.Timestamp(sell.getSellDate().getTime()));
            stmt.setDouble(3, sell.getTotal());
            stmt.setDouble(4, sell.getPrice());
        }, "SellDAO", "save");

        // Salvar produtos da venda
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

        // Aplicar pontos de fidelidade
        applyLoyaltyPoints(sell);

        return generatedId;
    }
    
    /**
     * Retorna a lista de produtos associados a uma venda, com quantidade e preço.
     *
     */
    public List<Product> findProductsBySellId(int sellId) {
    String sql = """
        SELECT 
            p.id AS product_id,
            p.name AS product_name,
            p.price AS product_price,
            p.type AS product_type,
            p.stock_quantity AS product_stock_quantity,
            p.point_cost AS product_point_cost,
            sp.quantity AS quantity,
            sp.price AS sell_price
        FROM sell_product sp
        JOIN product p ON p.id = sp.product_id
        WHERE sp.sell_id = ?
        ORDER BY p.name
    """;

    return executeQuery(sql,
        stmt -> {
            stmt.setInt(1, sellId);
        },
        rs -> {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getDouble("product_price"));
                product.setType(rs.getString("product_type"));
                product.setStockQuantity(rs.getInt("product_stock_quantity"));
                product.setPointCost(rs.getInt("product_point_cost"));
                product.setStockQuantity((product.getStockQuantity() - rs.getInt("quantity")));

                products.add(product);
            }
            return products;
        },
        "SellDAO",
        "findProductsBySellId"
    );
}


    /**
     * Busca uma venda pelo ID.
     *
     */
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

    /**
     * Retorna todas as vendas cadastradas, incluindo informações do cliente.
     */
    @Override
    public List<Sell> findAll() {
        String sql = "SELECT s.id AS sell_id, s.sell_date, s.total, s.price, c.id AS client_id, c.name AS client_name, c.cpf AS client_cpf, c.phone AS client_phone, c.total_points AS client_points  FROM sell s JOIN client c ON c.id = s.client_id order by s.id";
        return executeQuery(sql, stmt -> {
        }, result -> {
            List<Sell> sells = new ArrayList<>();
            while (result.next()) {
                sells.add(mapResultSetToSell(result));
            }
            return sells;
        }, "SellDAO", "findAll");
    }

    /**
     * Atualiza uma venda existente no banco de dados.
     *
     */
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

    /**
     * Exclui uma venda pelo ID.
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM sell WHERE id = ?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id), "SellDAO", "delete");
    }
    
    /**
     * Mapeia o ResultSet para um objeto Sell.
     *
     */
    private Sell mapResultSetToSell(ResultSet rs) throws SQLException {
        Sell sell = new Sell();

        sell.setId(rs.getInt("sell_id"));
        sell.setSellDate(rs.getDate("sell_date"));
        sell.setTotal(rs.getDouble("total"));
        sell.setPrice(rs.getDouble("price"));

        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setName(rs.getString("client_name"));
        client.setCpf(rs.getString("client_cpf"));
        client.setPhone(rs.getString("client_phone"));
        client.setTotalPoints(rs.getInt("client_points"));

        sell.setClient(client);

        return sell;
    }

    /**
     * Filtra vendas por ID, nome do cliente ou CPF.
     *
     */
    public List<Sell> filter(int id, String name, String cpf) {

        List<Object> parameters = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT s.id AS sell_id, s.sell_date, s.total, s.price, c.id AS client_id, c.name AS client_name, c.cpf AS client_cpf, c.phone AS client_phone, c.total_points AS client_points FROM sell s JOIN client c ON c.id = s.client_id WHERE 1=1");

        if (id > 0) {
            sql.append(" AND s.id = ?");
            parameters.add(id);
        }

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND c.name ILIKE ?");
            parameters.add("%" + name + "%");
        }

        if (cpf != null && !cpf.trim().isEmpty()) {
            sql.append(" AND c.cpf ILIKE ?");
            parameters.add("%" + cpf + "%");
        }

        sql.append(" ORDER BY s.id");

        return executeQuery(sql.toString(), stmt -> {
            int i = 1;
            for (Object param : parameters) {
                setStatementParameter(stmt, i++, param);
            }
        }, result -> {
            List<Sell> sells = new ArrayList<>();
            while (result.next()) {
                sells.add(mapResultSetToSell(result));
            }
            return sells;
        }, "SellDAO", "filter");
    }

    /**
     * Aplica pontos de fidelidade ao cliente com base no total da venda.
     */
    public void applyLoyaltyPoints(Sell sell) {
        if (sell == null || sell.getClient() == null) {
            return;
        }

        int earnedPoints = (int) (sell.getTotal() / 10) / 2;

        if (earnedPoints > 0) {
            Client client = sell.getClient();
            int currentPoints = client.getTotalPoints() != null ? client.getTotalPoints() : 0;
            client.setTotalPoints(currentPoints + earnedPoints);

            ClientDAO clientDAO = new ClientDAO();
            clientDAO.updateTotalPoints(client);
        }
    }
    
    /**
     * Adiciona um produto a uma venda existente.
     *
     */
    public void addProductToSell(int sellId, int productId, int quantity, double price) {
        if (sellId <= 0 || productId <= 0 || quantity <= 0 || price < 0) {
            throw new IllegalArgumentException("Venda, produto, quantidade e preço devem ser válidos.");
        }

        String sql = "INSERT INTO sell_product (sell_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setInt(1, sellId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
        }, "SellDAO", "addProductToSell");
    }
    
    /**
     * Busca uma venda completa pelo ID, incluindo produtos associados.
     *
     */
    public Sell findByIdWithProducts(int sellId) {
            // 1. Buscar a venda e o cliente
            String sqlSell = """
                SELECT s.id, s.client_id, s.sell_date, s.total, s.price,
                       c.name, c.cpf, c.total_points
                FROM sell s
                JOIN client c ON s.client_id = c.id
                WHERE s.id = ?
            """;

            Sell sell = executeQuery(
                sqlSell,
                stmt -> stmt.setInt(1, sellId),
                rs -> {
                    if (rs.next()) {
                        Client client = new Client();
                        client.setId(rs.getInt("client_id"));
                        client.setName(rs.getString("name"));
                        client.setCpf(rs.getString("cpf"));
                        client.setTotalPoints(rs.getInt("total_points"));

                        Sell s = new Sell();
                        s.setId(rs.getInt("id"));
                        s.setClient(client);
                        s.setSellDate(rs.getTimestamp("sell_date"));
                        s.setTotal(rs.getDouble("total"));
                        s.setPrice(rs.getDouble("price"));
                        return s;
                    }
                    return null;
                },
                "SellDAO",
                "findByIdWithProducts"
            );

            if (sell == null) {
                return null; // Venda não encontrada
            }

            // 2. Buscar produtos da venda
            String sqlProducts = """
                SELECT sp.product_id, p.name, p.type, sp.quantity, sp.price
                FROM sell_product sp
                JOIN product p ON sp.product_id = p.id
                WHERE sp.sell_id = ?
            """;

            List<Product> products = executeQuery(
                sqlProducts,
                stmt -> stmt.setInt(1, sellId),
                rs -> {
                    List<Product> list = new ArrayList<>();
                    while (rs.next()) {
                        Product p = new Product();
                        p.setId(rs.getInt("product_id"));
                        p.setName(rs.getString("name"));
                        p.setType(rs.getString("type"));
                        p.setPrice(rs.getDouble("price"));
                        p.setSoldQuantity(rs.getInt("quantity"));
                        list.add(p);
                    }
                    return list;
                },
                "SellDAO",
                "findByIdWithProducts_Products"
            );

            sell.setProducts(new ArrayList<>(products));

            return sell;
    }
}
