package factory.impl;

import entity.DiscountCard;
import factory.EntityFactory;
import factory.MessageFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountCardDBFactory implements EntityFactory<Short, DiscountCard> {

    private static final Logger logger = LogManager.getLogger();
    private static final String QUERY = "from DiscountCard fetch all properties";

    @Override
    public Map<Short, DiscountCard> getMapEntity(String path) {
        Map<Short, DiscountCard> discountCardMap = new HashMap<>();
        try (SessionFactory sessionFactory = new Configuration().configure(path)
                .addAnnotatedClass(DiscountCard.class).buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<DiscountCard> discountCards = session.createQuery(QUERY).list();
            for (DiscountCard discountCard : discountCards) {
                discountCardMap.put(discountCard.getNumber(), discountCard);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.ERROR, String.format(MessageFactory.ERROR_READING_CONFIG, path));
        }
        return discountCardMap;
    }
}
