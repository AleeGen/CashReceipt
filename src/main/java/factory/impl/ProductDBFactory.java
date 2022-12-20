package factory.impl;

import entity.Product;
import factory.EntityFactory;
import factory.MessageFactory;
import org.apache.logging.log4j.Level;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDBFactory implements EntityFactory<Integer, Product> {

    private static final Logger logger = LogManager.getLogger();
    private static final String QUERY = "from Product fetch all properties";

    @Override
    public Map<Integer, Product> getMapEntity(String path) {
        Map<Integer, Product> productMap = new HashMap<>();
        try (SessionFactory sessionFactory = new Configuration().configure(path)
                .addAnnotatedClass(Product.class).buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery(QUERY).list();
            for (Product product : products) {
                productMap.put(product.getId(), product);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.ERROR, String.format(MessageFactory.ERROR_READING_CONFIG, path));
        }
        return productMap;
    }
}
