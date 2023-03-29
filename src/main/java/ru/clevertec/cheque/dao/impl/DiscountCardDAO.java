package ru.clevertec.cheque.dao.impl;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.dao.EntityDAO;
import ru.clevertec.cheque.entity.DiscountCard;

import java.util.List;

@Repository
public class DiscountCardDAO implements EntityDAO<DiscountCard> {
    private static final String QUERY = "from DiscountCard fetch all properties";
    private static final String DELETE = "delete from DiscountCard where number = :number";
    @Autowired
    private EntityManager entityManager;

    public List<DiscountCard> getAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(QUERY).list();
    }

    @Cache(id = "number")
    @Override
    public DiscountCard getById(Integer id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(DiscountCard.class, id);
    }

    @Cache(id = "number")
    @Override
    public void save(DiscountCard discountCard) {
        Session session = entityManager.unwrap(Session.class);
        session.save(discountCard);
    }

    @Cache(id = "number")
    @Override
    public void delete(Integer number) {
        Session session = entityManager.unwrap(Session.class);
        session.createQuery(DELETE).setParameter("number", number).executeUpdate();
    }

    @Cache(id = "number")
    @Override
    public void update(DiscountCard discountCard) {
        Session session = entityManager.unwrap(Session.class);
        session.update(discountCard);
    }

}
