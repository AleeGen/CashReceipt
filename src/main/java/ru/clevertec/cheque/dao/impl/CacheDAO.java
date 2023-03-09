package ru.clevertec.cheque.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.dao.EntityDAO;
import ru.clevertec.cheque.entity.Human;

import java.util.List;

@Component
public class CacheDAO implements EntityDAO<Human> {
    @Autowired
    private Cache<Integer, Human> cache;
    @Autowired
    private HumanDAO dao;

    @Override
    public List<Human> getAll() {
        List<Human> humans = dao.getAll();
        int skip = Math.max(humans.size() - cache.getCapacity(), 0);
        humans.stream().skip(skip).forEach(h -> cache.put(h.getId(), h));
        return humans;
    }

    @Override
    public Human getById(int id) {
        Human human = cache.get(id);
        if (human == null) {
            human = dao.getById(id);
            if (human != null) {
                cache.put(human.getId(), human);
            }
        }
        return human;
    }

    @Override
    public void save(Human human) {
        dao.save(human);
        cache.put(human.getId(), human);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
        cache.delete(id);
    }

    @Override
    public void update(Human human) {
        dao.update(human);
        cache.put(human.getId(), human);
    }

}
