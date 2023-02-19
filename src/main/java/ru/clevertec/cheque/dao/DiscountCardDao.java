package ru.clevertec.cheque.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.cheque.entity.DiscountCard;
import java.util.List;

@Repository
public class DiscountCardDao {
    private static final String QUERY = "from DiscountCard fetch all properties";
    @Autowired
    private SessionFactory sessionFactory;

    public List<DiscountCard> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<DiscountCard> discountCard = session.createQuery(QUERY).list();
        session.getTransaction().commit();
        return discountCard;
    }

    public void delete(DiscountCard discountCard) {
    }
}
