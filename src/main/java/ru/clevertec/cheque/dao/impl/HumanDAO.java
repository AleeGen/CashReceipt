package ru.clevertec.cheque.dao.impl;

import org.springframework.stereotype.Repository;
import ru.clevertec.cheque.dao.EntityDAO;
import ru.clevertec.cheque.entity.Human;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HumanDAO implements EntityDAO<Human> {

    private final Map<Integer, Human> db;

    public HumanDAO() {
        db = new HashMap<>(Map.of(
                 1, new Human(1, "male", 10, "Vanya", false),
                 2, new Human(2, "male", 20, "Petya", false),
                 3, new Human(3, "female", 25, "Vika", true),
                 4, new Human(4, "female", 30, "Dasha", true),
                 5, new Human(5, "male", 35, "Vasya", false)
        ));
    }

    @Override
    public List<Human> getAll() {
        return db.values().stream().toList();
    }

    @Override
    public Human getById(int id) {
        return db.get(id);
    }

    @Override
    public void save(Human human) {
        db.put(human.getId(), human);
    }

    @Override
    public void delete(int id) {
        db.remove(id);
    }

    @Override
    public void update(Human human) {
        db.put(human.getId(), human);
    }

}
