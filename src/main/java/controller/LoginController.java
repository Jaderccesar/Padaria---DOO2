package controller;

import dao.UsersDAO;
import model.Users;


/**
 * Controlador responsável pela autenticação de usuários no sistema.
 * Atua como intermediário entre a interface de login e o banco de dados,
 * garantindo que apenas usuários válidos consigam acesso.
 *
 */
public class LoginController {
    
    // DAO responsável pelas operações de banco de dados relacionadas a usuários 
    private final UsersDAO userDAO = new UsersDAO();

    /**
     * Realiza a autenticação de um usuário a partir das credenciais informadas.
     * 
     * O método verifica se os campos obrigatórios (usuário e senha) estão preenchidos
     * e, em seguida, consulta o banco de dados para confirmar se as credenciais são válidas.
     * 
     */
    public Users autenticar(String username, String password) {
        
        // Validação dos campos obrigatórios
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new RuntimeException("Preencha usuário e senha.");
        }

        // Consulta ao banco de dados para validar o usuário
        Users user = userDAO.findByUsernameAndPassword(username, password);
        
        // Caso o usuário não exista ou senha esteja incorreta
        if (user == null) {
            throw new RuntimeException("Usuário ou senha inválidos!");
        }

        // Retorna o usuário autenticado
        return user;
    }
}
