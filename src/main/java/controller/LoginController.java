package controller;

import dao.UsersDAO;
import model.Users;

public class LoginController {
    private final UsersDAO userDAO = new UsersDAO();

    public Users autenticar(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new RuntimeException("Preencha usuário e senha.");
        }

        Users user = userDAO.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new RuntimeException("Usuário ou senha inválidos!");
        }

        return user;
    }
}
