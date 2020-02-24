package ch.noseryoung.websockets.generic;

import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class AbstractEntityServiceImpl<T extends AbstractEntity> implements AbstractEntityService<T> {

    protected AbstractEntityRepository<T> repository;
    protected Logger logger;

    private String className;

    public AbstractEntityServiceImpl(AbstractEntityRepository<T> repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
        initClassName();
    }

    private void initClassName() {
        try {
            this.className = Class.forName(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName()).getSimpleName();
        } catch (ClassNotFoundException e) {
            this.className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
        }
    }

    @Override
    public List<T> findAll() {
        logger.debug("Attempting to find all {}", multipleEntities());

        List<T> entities = repository.findAllByDeletedFalse();

        logger.debug("Loaded all {}", multipleEntities());

        return entities;
    }

    @Override
    public T findById(String id) throws NoSuchElementException {
        logger.debug("Attempting to find {} with ID '{}'", singleEntity(), id);

        Optional<T> optional = repository.findByIdAndDeletedFalse(id);

        if(optional.isPresent()) {
            T entity = optional.get();

            logger.debug("Found {} with ID '{}'", singleEntity(), id);

            return entity;
        } else {
            logger.debug("{} with ID '{}' not found", singleEntity(), id);

            throw new NoSuchElementException(String.format("%s with ID '%s' not found", singleEntity(), id));
        }
    }

    @Override
    public final T create(T entity) {
        return finishCreate(midCreate(startCreate(entity)));
    }

    private T startCreate(T entity) {
        logger.debug("Attempting to create new {}", singleEntity());

        return entity;
    }

    protected T midCreate(T entity) {
        return entity;
    }

    private T finishCreate(T entity) {
        entity.setId(null);

        entity = repository.save(entity);

        logger.debug("Created {}. New ID is '{}'", singleEntity(), entity.getId());

        return entity;
    }

    @Override
    public final T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public final T updateById(String id, T entity) throws NoSuchElementException {
        return finishUpdate(id, midUpdate(id, startUpdate(id, entity)));
    }

    private T startUpdate(String id, T entity) {
        logger.debug("Attempting to update {} with ID '{}'", singleEntity(), id);

        if(repository.existsById(id)) {
            return entity;
        } else {
            logger.debug("{} with ID '{}' not found", singleEntity(), id);

            throw new NoSuchElementException(id);
        }
    }

    protected T midUpdate(String id, T entity) {
        return entity;
    }

    private T finishUpdate(String id, T entity) {
        entity.setId(id);

        entity = repository.save(entity);

        logger.debug("Updated {} with ID '{}'", singleEntity(), id);

        return entity;
    }

    @Override
    public final void deleteById(String id) throws NoSuchElementException {
        finishDelete(id, midDelete(id, startDelete(id)));
    }

    private T startDelete(String id) {
        logger.debug("Attempting to delete {} with ID '{}'", singleEntity(), id);

        return findById(id);
    }

    protected T midDelete(String id, T entity) {
        return entity;
    }

    private void finishDelete(String id, T entity) {
        entity.setDeleted(true);

        repository.save(entity);

        logger.debug("Marked {} with ID '{}' as deleted", singleEntity(), id);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsByIdAndDeletedFalse(id);
    }

    protected final String singleEntity() {
        return className;
    }

    protected final String multipleEntities() {
        if (className.endsWith("y")) {
            return className.substring(0, className.length() - 1) + "ies";
        } else {
            return className + "s";
        }
    }
}
