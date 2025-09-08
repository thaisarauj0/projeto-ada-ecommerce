package repository;

import java.util.List;

public interface IRepository <T, ID>{
    void add(T entity);
    T findById(ID id);
    List<T> listAll();
    void update(T entity);
}
