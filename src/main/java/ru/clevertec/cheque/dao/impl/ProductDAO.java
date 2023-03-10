package ru.clevertec.cheque.dao.impl;


import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.cheque.dao.EntityDAO;
import ru.clevertec.cheque.entity.Product;

import java.util.List;

@Repository
public class ProductDAO implements EntityDAO<Product> {
    private static final String QUERY = "from Product fetch all properties";
    private static final String DELETE = "delete from Product where id = :id";

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Product> getAll() {
        Session session = entityManager.unwrap(Session.class);
        List<Product> products = session.createQuery(QUERY).list();
        return products;
    }

    @Override
    public Product getById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Product product = session.get(Product.class, id);
        return product;
    }

    @Override
    public void save(Product product) {
        Session session = entityManager.unwrap(Session.class);
        session.save(product);
    }

    @Override
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.createQuery(DELETE).setParameter("id", id).executeUpdate();
    }

    @Override
    public void update(Product product) {
        Session session = entityManager.unwrap(Session.class);
        session.update(product);
    }

}
