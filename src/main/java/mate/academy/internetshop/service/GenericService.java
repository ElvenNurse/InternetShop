package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, N> {

    T create(T entity);

    Optional<T> get(N id);

    T update(T entity);

    boolean deleteById(N id);

    boolean delete(T entity);

    List<T> getAll();
}
