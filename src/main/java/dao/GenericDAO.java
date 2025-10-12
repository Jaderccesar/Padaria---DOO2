/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author Usuario
 */
public interface GenericDAO<T, ID> {
    
    ID save(T entity);
    T findById(ID id);
    List<T> findAll();
    void update(T entity, ID id);
    void delete(ID id);
}
