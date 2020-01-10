package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, N> {

    T create(T entity);

    T get(N id);

    T update(T entity);

    boolean deleteById(N id);

    boolean delete(T entity);

    List<T> getAll();
}
