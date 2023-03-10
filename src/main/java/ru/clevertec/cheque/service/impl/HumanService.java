package ru.clevertec.cheque.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.cheque.dao.EntityDAO;
import ru.clevertec.cheque.entity.Human;
import ru.clevertec.cheque.exception.HumanException;
import ru.clevertec.cheque.service.EntityService;

import java.util.List;


@Service
public class HumanService implements EntityService<Human> {

    private static final String NOT_FOUND = "User with id=\"%d\" not found";
    @Autowired
    @Qualifier(value = "cacheDAO")
    private EntityDAO<Human> dao;

    @Override
    public List<Human> getAll() {
        return dao.getAll();
    }

    @Override
    public Human getById(int id) {
        Human human = dao.getById(id);
        if (human == null) {
            throw new HumanException(String.format(NOT_FOUND, id));
        }
        return human;
    }

    @Override
    public void save(Human human) {
        dao.save(human);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public void update(Human human) {
        dao.update(human);
    }
}
