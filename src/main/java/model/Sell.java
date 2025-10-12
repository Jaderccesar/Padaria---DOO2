/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Usuario
 */
public class Sell {
    
    private int id;
    private Client client;
    private ArrayList<Product> products;
    private HashMap<Product, Integer> quantities;
    private Date sellDate;
    private Double total;
    private Double price;
    
    public Sell(){
        products = new ArrayList<>();
        quantities = new HashMap<>();
    }
    
    public Sell(Client client, Double total, Double price){
        this.client   = client;
        this.total    = total;
        this.products = new ArrayList<>();
        this.price    = price;
        this.quantities = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public void addProduct(Product product, int quantity) {
        products.add(product);
        quantities.put(product, quantity);
    }

    public int getQuantity(Product product) {
        return quantities.getOrDefault(product, 0);
    }

    public HashMap<Product, Integer> getQuantities() {
        return quantities;
    }
    
    public void redeemPoints(int pointsToUse) {
    if (client == null || total == null) return;

    int availablePoints = client.getTotalPoints() != null ? client.getTotalPoints() : 0;

    if (pointsToUse > availablePoints) {
        return;
    }

    double discount = pointsToUse;
    if (discount > total) discount = total;

    total -= discount;
    client.setTotalPoints(availablePoints - pointsToUse);
}

    
}
