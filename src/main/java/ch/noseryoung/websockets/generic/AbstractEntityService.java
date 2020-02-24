package ch.noseryoung.websockets.generic;

import java.util.List;
import java.util.NoSuchElementException;

public interface AbstractEntityService<T extends AbstractEntity> {

    List<T> findAll();

    T findById(String id) throws NoSuchElementException;

    T create(T entity);

    T save(T entity);

    T updateById(String id, T entity) throws NoSuchElementException;

    void deleteById(String id) throws NoSuchElementException;

    boolean existsById(String id);
}
