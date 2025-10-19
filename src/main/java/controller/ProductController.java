/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.util.List;
import model.Product;
import dao.LoggerDAO;

/**
 *
 * @author Daniel Coelho - PAD 1 e 2 - New - "Adicionado o CRUD de um PRODUTO" - Sprint 1
 */
public class ProductController {
    
    private final ProductDAO productDAO = new ProductDAO();
    private final LoggerDAO loggerDAO = new LoggerDAO();
    String usuario = "admin";
    String class_name = "ProductController";
    
    public Integer saveProduct(Product product) throws IllegalArgumentException {
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("A quantidade no estoque deve ser maior ou igual a zero.");
        }
        if (product.getPointCost() < 0) {
            throw new IllegalArgumentException("A pontuação deve ser maior ou igual a zero.");
        }
        
        if (product.getType().equals("Selecionar")) {
            throw new IllegalArgumentException("Informe um tipo válido para o produto.");
        }
        
         return productDAO.save(product);
    }
    
    public void updateProduct(Product product, Integer id) {
    
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("A quantidade no estoque deve ser maior ou igual a zero.");
        }
        if (product.getPointCost() < 0) {
            throw new IllegalArgumentException("A pontuação deve ser maior ou igual a zero.");
        }
        
        if (product.getType().equals("Selecionar")) {
            throw new IllegalArgumentException("Informe um tipo válido para o produto.");
        }
        
        productDAO.update(product, id);
    }
    
    public List<Product> findAll() {
        try {
            return productDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao carregar produtos.", e);
        }
    }
    
    public List<Product> filterProducts(int id, String name, String type) {
        
        if(id > 0 || !name.isEmpty() || !type.isEmpty()){
            return productDAO.filter(id,name, type);
        } else {
            return findAll();
        }
    }

    public Product findById(Integer intValue) {
        try {
            return productDAO.findById(intValue);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao localizar produto no banco. Detalhes no console.", e);
        }
    }
  
    
    public void updateStock(int productId, int quantitySold) {
        if (productId <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        System.out.println("no controller" + productId + quantitySold);
        ProductDAO productDAO = new ProductDAO();
        productDAO.updateStock(productId, quantitySold);
    }
    
    public void deleteProduct(Integer id) {
        try {
            loggerDAO.salvarLog(usuario, class_name, "Delete Produto", "Produto com id: " + id + " deletado!", null);
            productDAO.delete(id);
        } catch (Exception e) {
             loggerDAO.salvarLog(usuario, class_name, "Delete Produto", "Falha ao deletar produto com id" + id, null);
            throw new RuntimeException("Erro ao excluir produto. Detalhes no console.", e);
        }
    }
}
