package com.moonproof.store;

import com.moonproof.domain.Experiment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ExperimentStoreImpl implements ExperimentStore{
    private final List<Experiment> store = new ArrayList<>();
    @Override
    public Optional<Experiment> get(String name, String token) {
        return store.stream()
                .filter(e -> token.equals(e.getToken()) && name.equals(e.getName()))
                .findAny();
    }

    @Override
    public List<Experiment> getAll() {
        return store;
    }

    @Override
    public void put(Experiment experiment) {
        store.add(experiment);
    }
}
