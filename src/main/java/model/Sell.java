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
    
    /**
     * Construtor padrão da classe Sell.
     * Inicializa as listas de produtos e quantidades vazias.
     */
    public Sell(){
        products = new ArrayList<>();
        quantities = new HashMap<>();
    }
    
    /**
     * Construtor que cria uma venda associando cliente, total e preço.
     * Inicializa também as listas de produtos e quantidades.
     */
    public Sell(Client client, Double total, Double price){
        this.client   = client;
        this.total    = total;
        this.products = new ArrayList<>();
        this.price    = price;
        this.quantities = new HashMap<>();
    }
    
    // ------------------ Getters e Setters ------------------

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
    
    // ------------------ Métodos Funcionais ------------------

    /**
     * Adiciona um produto à lista de produtos vendidos
     * e registra a quantidade correspondente no mapa.
     *
     */
    public void addProduct(Product product, int quantity) {
        products.add(product);
        quantities.put(product, quantity);
    }

    /**
     * Retorna a quantidade de um produto específico na venda.
     * Caso o produto não exista na venda, retorna 0.
     *
     */
    public int getQuantity(Product product) {
        return quantities.getOrDefault(product, 0);
    }

    /**
     * Retorna o mapa completo de produtos e suas quantidades.
     *
     */
    public HashMap<Product, Integer> getQuantities() {
        return quantities;
    }
    
    /**
     * Permite que o cliente utilize seus pontos acumulados para obter desconto na compra.
     *
     * Regras:
     *  - O cliente precisa estar definido e possuir pontos suficientes.
     *  - Cada ponto equivale a 1 unidade monetária de desconto.
     *
     */
    public void redeemPoints(int pointsToUse) {
    // Verifica se o cliente e o total da venda estão definidos
    if (client == null || total == null) return;

    // Obtém a quantidade de pontos disponíveis do cliente
    int availablePoints = client.getTotalPoints() != null ? client.getTotalPoints() : 0;

    // Se o cliente tentar usar mais pontos do que tem, o método é encerrado
    if (pointsToUse > availablePoints) {
        return;
    }

    // Calcula o desconto baseado na quantidade de pontos usados
    double discount = pointsToUse;
    if (discount > total) discount = total;

    // Aplica o desconto ao valor total
    total -= discount;
    
    // Atualiza o saldo de pontos do cliente
    client.setTotalPoints(availablePoints - pointsToUse);
    }
}
