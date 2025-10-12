
import dao.ProductDAO;
import model.Product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pichau
 */
public class TesteProdutoDAO {
      
     public static void main(String[] args) {
        ProductDAO productDao = new ProductDAO();
        Product product = new Product();
        product.setName("Pão Francês");
        product.setPrice(0.80);
        product.setType("Pão");
        product.setStockQuantity(200);
        product.setPointCost(5);
        productDao.save(product);
        System.out.println("Produto salvo com sucesso!");
    }
}
