package ru.clevertec.cheque.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.dao.impl.DiscountCardDAO;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.service.EntityService;

import java.util.List;

@Service
public class DiscountCardService implements EntityService<DiscountCard> {
    @Autowired
    private DiscountCardDAO discountCardDao;

    @Transactional
    @Override
    public List<DiscountCard> getAll() {
        return discountCardDao.getAll();
    }

    @Transactional
    @Override
    public DiscountCard getById(int id) {
        return discountCardDao.getById(id);
    }

    @Transactional
    @Override
    public void save(DiscountCard discountCard) {
        discountCardDao.save(discountCard);
    }

    @Transactional
    @Override
    public void delete(int id) {
        discountCardDao.delete(id);
    }

    @Transactional
    @Override
    public void update(DiscountCard discountCard) {
        discountCardDao.update(discountCard);
    }

}