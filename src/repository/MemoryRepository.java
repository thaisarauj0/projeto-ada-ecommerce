package repository;

import java.util.*;
import java.util.function.Function;

public class MemoryRepository<T, ID> implements IRepository<T, ID> {
    private Map<ID, T> store = new HashMap<>();
    private java.util.function.Function<T, ID> idExtractor;

    public MemoryRepository(java.util.function.Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public void add(T entity) {
        ID id = idExtractor.apply(entity);
        if(store.containsKey(id)){
            throw new IllegalStateException("Entity already exists");
        }
        store.put(id, entity);
    }

    @Override
    public T findById(ID id) {
        return store.get(id);
    }

    @Override
    public List<T> listAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(T entity) {
        ID id = idExtractor.apply(entity);
        if(!store.containsKey(id)){
            throw new IllegalStateException("Entity does not exist");
        }
        store.put(id, entity);
    }
}
