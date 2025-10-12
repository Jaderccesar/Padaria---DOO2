package Main;

import dao.ClientDAO;
import dao.ProductDAO;
import dao.SellDAO;
import model.Client;
import model.Product;
import model.Sell;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class Main {
    public static void main(String[] args) {

        SellDAO daoSell = new SellDAO();
        ClientDAO daoClient = new ClientDAO();
        ProductDAO daoProduct = new ProductDAO();
        
        Client c1 = new Client("Jader", "11559192976", "479916765267", 26);
        daoClient.save(c1);
        
        
        Product p1 = new Product("Banana", 12, "frutas", 5, 2);
        Product p2 = new Product("Computador", 5000, "Eletronicos", 25, 10);
        
        daoProduct.save(p1);
        daoProduct.save(p2);
        
        
        Sell s1 = new Sell();
        
        
        daoSell.save(s1);
    }
}
