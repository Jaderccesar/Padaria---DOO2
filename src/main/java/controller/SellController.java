/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.SellDAO;
import java.util.List;
import model.Sell;

/**
 *
 * @author Usuario
 */
public class SellController {

    private final SellDAO sellDAO = new SellDAO();

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

    public List<Sell> filterSells(Integer id, String clientName) {
        Integer idFilter = (id != null && id > 0) ? id : null;
        String nameFilter = (clientName != null && !clientName.trim().isEmpty()) ? clientName.trim() : null;
        String startDate = null;
        String endDate = null;

        if (idFilter == null && nameFilter == null && startDate == null) {
            return findAll();
        }

        return sellDAO.filter(idFilter, nameFilter, startDate, endDate);
    }
}
