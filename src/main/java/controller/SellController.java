/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.LoggerDAO;
import dao.SellDAO;
import java.util.List;
import model.Product;
import model.Sell;

/**
 *
 * @author Usuario
 */
public class SellController {

    private final SellDAO sellDAO = new SellDAO();
    private final LoggerDAO loggerDAO = new LoggerDAO();
    String usuario = "admin";
    String class_name = "SellController";

    public Integer save(Sell sell) {
        if (sell.getClient() == null) {
            throw new IllegalArgumentException("O cliente da venda é obrigatório.");
        }
        if (sell.getTotal() == null || sell.getTotal() <= 0) {
            throw new IllegalArgumentException("O valor total deve ser maior que zero.");
        }
        if (sell.getPrice() == null || sell.getPrice() < 0) {
            throw new IllegalArgumentException("O preço deve ser um valor válido.");
        }

        return sellDAO.save(sell);
    }

    public void update(Sell sell, Integer id) {
        if (sell.getClient() == null) {
            throw new IllegalArgumentException("O cliente da venda é obrigatório.");
        }
        if (sell.getTotal() == null || sell.getTotal() <= 0) {
            throw new IllegalArgumentException("O valor total deve ser maior que zero.");
        }
        sellDAO.update(sell, id);
    }
    
   public List<Product> findProductsBySellId(int sellId) {
    try {
        return sellDAO.findProductsBySellId(sellId);
    } catch (Exception e) {
        throw new RuntimeException("Falha ao buscar produtos da venda.", e);
    }
}

    public List<Sell> findAll() {
        try {
            return sellDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao carregar vendas.", e);
        }
    }

    public Sell findById(Integer id) {
        try {
            return sellDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao localizar venda no banco.", e);
        }
    }

    public List<Sell> filterSells(int id, String name, String cpf) {
        
        if(id > 0 || !name.isEmpty() || !cpf.isEmpty()){
            return sellDAO.filter(id, name, cpf);
        } else {
            return findAll();
        }
    }
    
    public void addProductToSell(int sellId, Product product, int quantity) {
        if (sellId <= 0 || product == null || quantity <= 0) {
            throw new IllegalArgumentException("Venda, produto e quantidade devem ser válidos.");
        }

        sellDAO.addProductToSell(sellId, product.getId(), quantity, product.getPrice());

        ProductController productController = new ProductController();
        productController.updateStock(product.getId(), -quantity);
    }
    
    public void applyLoyaltyPoints(Sell sell) {
        sellDAO.applyLoyaltyPoints(sell);
    }
    
    public Sell findByIdWithProducts(int sellId) {
        return sellDAO.findByIdWithProducts(sellId);
    }
    
    public void deleteSell(Integer sellId) {
        try {
            loggerDAO.salvarLog(usuario, class_name, "Delete Venda", "Venda com id: " + sellId + " deletada!", null);
            sellDAO.delete(sellId);
        } catch (Exception e) {
            loggerDAO.salvarLog(usuario, class_name, "Delete Venda", "Falha ao deletar venda com id: " + sellId, null);
            throw new RuntimeException("Erro ao excluir venda. Detalhes no console.", e);
        }
    }

    
}
