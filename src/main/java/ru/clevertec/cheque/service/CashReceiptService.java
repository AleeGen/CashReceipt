package ru.clevertec.cheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.dao.impl.DiscountCardDAO;
import ru.clevertec.cheque.dao.impl.ProductDAO;
import ru.clevertec.cheque.entity.*;
import ru.clevertec.cheque.exception.CashReceiptException;
import ru.clevertec.cheque.service.util.calculator.impl.DiscountCardCalculator;
import ru.clevertec.cheque.service.util.calculator.impl.PromotionalProductsCalculator;

import java.util.*;

@Service
public class CashReceiptService {
    private static final Organization organization;
    @Autowired
    private ProductDAO productDao;
    @Autowired
    private DiscountCardDAO discountCardDao;

    static {
        organization = new Organization(
                "Clevertec",
                "Москва, Россия ул. Дубининская, д. 90",
                "info@clevertec.ru",
                "+7 (499) 653 94 51");
    }

    @Transactional
    public CashReceipt getCashReceipt(Integer[] itemId, Integer numberCard) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer id : itemId) {
            if (map.containsKey(id)) {
                map.put(id, map.get(id) + 1);
            } else {
                map.put(id, 1);
            }
        }
        List<Position> positions = new ArrayList<>();
        for (Integer id : map.keySet()) {
            Product product = productDao.getById(id);
            if (product == null) {
                throw new CashReceiptException(String.format("Product with id = %d not found", id));
            }
            positions.add(new Position(map.get(id), product));
        }
        Optional<DiscountCard> card;
        if (numberCard == null) {
            card = Optional.empty();
        } else {
            DiscountCard c = discountCardDao.getById(numberCard);
            if (c == null) {
                throw new CashReceiptException(String.format("Discount card with number = %d not found", numberCard));
            }
            card = Optional.of(c);
        }
        CashReceipt cashReceipt = new CashReceipt.CashReceiptBuilder(positions)
                .addDiscountCard(card)
                .addOrganization(organization)
                .addDate(new Date()).build();
        new PromotionalProductsCalculator().calculate(cashReceipt);
        new DiscountCardCalculator().calculate(cashReceipt);
        return cashReceipt;
    }

}
