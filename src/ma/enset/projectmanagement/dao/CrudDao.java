package ma.enset.projectmanagement.dao;

import java.util.List;

public interface CrudDao<T>{
    List<T> findAll() ;
    T findById(int id);
    T ajouter(T o);
    boolean supprimer(T o);
    T modifier(T o);
}
