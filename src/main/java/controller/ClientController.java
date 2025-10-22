/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ClientDAO;
import dao.LoggerDAO;
import java.util.List;
import model.Client;

/**
 * Controlador responsável por gerenciar a lógica de negócios da entidade Client.
 * Atua como intermediário entre a camada de apresentação (View) e a camada de persistência (DAO),
 * validando dados, aplicando regras e registrando logs.
 */
public class ClientController {

    // Instância de acesso à base de dados para os clientes 
    private final ClientDAO clientDAO = new ClientDAO();
    // Instância de acesso à base de dados para logs 
    private final LoggerDAO loggerDAO = new LoggerDAO();
    
    String usuario = "admin";
    String class_name = "ClientController";

    //Valida e salva um novo cliente no banco de dados.
    public Integer saveClient(Client client) throws IllegalArgumentException {

        if (client.getName() == null || client.getName().trim().isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "saveClient", "Tentativa de inserção com nome vazio", null);
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (client.getCpf() == null || client.getCpf().trim().isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "saveClient", "Tentativa de inserção com cpf vazio", null);
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }

        if (!client.getCpf().matches("\\d{11}")) {
            loggerDAO.salvarLog(usuario, class_name, "saveClient", "Tentativa de inserção com cpf inválido", null);
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (client.getPhone() == null || client.getPhone().trim().isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "saveClient", "Tentativa de inserção com telefone vazio", null);
            throw new IllegalArgumentException("O telefone é obrigatório.");
        }

        if (client.getTotalPoints() < 0) {
            loggerDAO.salvarLog(usuario, class_name, "saveClient", "Tentativa de inserção com pontos negativos", null);
            throw new IllegalArgumentException("O total de pontos não pode ser negativo.");
        }

        List<Client> existing = clientDAO.filter(0, null, client.getCpf());
        if (!existing.isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "saveClient", "Tentativa de inserção de cliente com cpf já existente", null);
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }

        loggerDAO.salvarLog(usuario, class_name, "saveClient", "Cliente com id: " + client.getId() + " inserido com sucesso!", null);
        return clientDAO.save(client);
    }

    //Atualiza os dados de um cliente existente.
    public void updateClient(Client client, Integer id) {

        if (client.getName() == null || client.getName().trim().isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "updateClient", "Tentativa de atualizacao com nome vazio", null);
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (client.getCpf() == null || client.getCpf().trim().isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "updateClient", "Tentativa de atualizacao com cpf vazio", null);
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }

        if (!client.getCpf().matches("\\d{11}")) {
            loggerDAO.salvarLog(usuario, class_name, "updateClient", "Tentativa de atualizacao de cliente com cpf inválido", null);
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (client.getPhone() == null || client.getPhone().trim().isEmpty()) {
            loggerDAO.salvarLog(usuario, class_name, "updateClient", "Tentativa de atualizacao com telefone vazio", null);
            throw new IllegalArgumentException("O telefone é obrigatório.");
        }

        if (client.getTotalPoints() < 0) {
            loggerDAO.salvarLog(usuario, class_name, "updateClient", "Tentativa de atualizacao com pontos negativos", null);
            throw new IllegalArgumentException("O total de pontos não pode ser negativo.");
        }

        List<Client> existing = clientDAO.filter(0, null, client.getCpf());
        for (Client existingClient : existing) {

            if (!existingClient.getId().equals(id)) {
                loggerDAO.salvarLog(usuario, class_name, "updateClient", "Tentativa de atualização de cliente com cpf já existente", null);
                throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
            }
        }
        
        loggerDAO.salvarLog(usuario, class_name, "updateClient", "Cliente com id: " + id + " atualizado com sucesso!", null);
        clientDAO.update(client, id);
    }

    //Retorna todos os clientes cadastrados.
    public List<Client> findAll() {
        try {
            return clientDAO.findAll();
        } catch (Exception e) {
            loggerDAO.salvarLog(usuario, class_name, "FindAll Cliente", "Falha ao carregar clientes", null);
            throw new RuntimeException("Falha ao carregar clientes.", e);
        }
    }

    //Aplica filtros de busca para clientes.
    public List<Client> filterClients(int id, String name, String cpf) {

        if (id > 0 || (name != null && !name.isEmpty()) || (cpf != null && !cpf.isEmpty())) {
            return clientDAO.filter(id, name, cpf);
        } else {
            return findAll();
        }
    }

    // Busca um cliente específico pelo ID.
    public Client findById(Integer id) {
        try {
            return clientDAO.findById(id);
        } catch (Exception e) {
            loggerDAO.salvarLog(usuario, class_name, "FindById Cliente", "Falha ao buscar cliente com id" + id, null);
            throw new RuntimeException("Falha ao localizar cliente no banco.", e);
        }
    }

    //Exclui um cliente com base no ID informado.
    public void deleteClient(Integer id) {
        try {
            loggerDAO.salvarLog(usuario, class_name, "Delete Cliente", "Usuário com id: " + id + " deletado!", null);
            clientDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
             loggerDAO.salvarLog(usuario, class_name, "Delete Cliente", "Falha ao deletar cliente com id" + id, null);
            throw new RuntimeException("Erro ao excluir cliente. Detalhes no console.", e);
        }
    }
    
    //Busca um cliente pelo CPF.
    public Client findByCpf(String cpf) {
        try {
            List<Client> clients = clientDAO.filter(0, null, cpf);
            if (!clients.isEmpty()) {
                return clients.get(0);
            } else {
                return null; 
            }
        } catch (Exception e) {
            loggerDAO.salvarLog(usuario, class_name, "findByCpf", "Falha ao buscar cliente com CPF: " + cpf, null);
            throw new RuntimeException("Falha ao localizar cliente pelo CPF no banco.", e);
        }
    }

}
