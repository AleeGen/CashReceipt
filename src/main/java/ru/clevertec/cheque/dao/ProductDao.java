package ru.clevertec.cheque.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.cheque.entity.Product;

import java.util.List;

@Repository
public class ProductDao {
    private static final String QUERY = "from Product fetch all properties";
    @Autowired
    private SessionFactory sessionFactory;

    public List<Product> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery(QUERY).list();
        session.getTransaction().commit();
        return products;
    }

}
