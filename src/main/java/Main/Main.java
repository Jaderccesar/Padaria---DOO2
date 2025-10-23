package Main;

import dao.ClientDAO;
import dao.ProductDAO;
import dao.SellDAO;
import model.Client;
import model.Product;
import model.Sell;
import view.LoginView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 *
 */
public class Main {
    public static void main(String[] args) {

       java.awt.EventQueue.invokeLater(() -> new LoginView().setVisible(true));
    }
}
