/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Daniel Coelho PAD 1 e 2 - New - "Adicionado o CRUD de um PRODUTO" - Sprint 1
 */
public class Product {
    
    private int id;
    private String name;
    private double price;
    private String type;
    private int stockQuantity;
    private int pointCost;
    private int soldQuantity;
    
    public Product(){
    
    }
    
    public Product(String name, double price, String type, int stockQuantity, int pointCost) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.stockQuantity = stockQuantity; 
        this.pointCost = pointCost;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }
      
    public int getId() {
        return id;
    }
    
    public int getSoldQuantity() {
        return soldQuantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getPointCost() {
        return pointCost;
    }
    
    
}
