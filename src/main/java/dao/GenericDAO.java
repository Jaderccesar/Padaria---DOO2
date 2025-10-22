/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    
    //Insere uma nova entidade no banco de dados.
    ID save(T entity);
    
    //Busca uma entidade no banco de dados com base no seu identificador.
    T findById(ID id);
    
    //Retorna uma lista contendo todas as entidades cadastradas.
    List<T> findAll();
    
    //Atualiza as informações de uma entidade existente no banco de dados.
    void update(T entity, ID id);
    
    //Remove uma entidade do banco de dados com base em seu identificador.
    void delete(ID id);
}
