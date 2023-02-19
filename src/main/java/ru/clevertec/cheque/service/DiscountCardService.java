package ru.clevertec.cheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.cheque.dao.DiscountCardDao;
import ru.clevertec.cheque.entity.DiscountCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscountCardService {
    @Autowired
    private DiscountCardDao discountCardDao;

    public Map<Short, DiscountCard> getAll() {
        Map<Short, DiscountCard> discountCardMap = new HashMap<>();
        List<DiscountCard> discountCards = discountCardDao.getAll();
        for (DiscountCard card : discountCards) {
            discountCardMap.put(card.getNumber(), card);
        }
        return discountCardMap;
    }

    public void delete(DiscountCard card) {
        discountCardDao.delete(card);
    }
}