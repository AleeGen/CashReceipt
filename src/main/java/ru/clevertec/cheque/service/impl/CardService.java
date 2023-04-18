package ru.clevertec.cheque.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.dao.CardRepository;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.exception.CardException;
import ru.clevertec.cheque.service.EntityService;

import java.util.List;

@Service
public class CardService implements EntityService<DiscountCard> {

    private final CardRepository cardRep;
    @Value(value = "${pagination.page}")
    private int defaultPage;
    @Value(value = "${pagination.size}")
    private int defaultSize;

    public CardService(CardRepository cardRep) {
        this.cardRep = cardRep;
    }

    @Cache(id = "number")
    @Transactional
    @Override
    public List<DiscountCard> findAll(Integer page, Integer size) {
        page = page == null ? defaultPage : page;
        size = size == null ? defaultSize : size;
        return cardRep.findAll(PageRequest.of(page, size)).getContent();
    }

    @Cache(id = "number")
    @Transactional
    @Override
    public DiscountCard findById(int number) {
        return cardRep.findById(number).orElseThrow(() ->
                new CardException(String.format("Not found card with number: %d", number)));
    }

    @Cache(id = "number")
    @Transactional
    @Override
    public DiscountCard save(DiscountCard discountCard) {
        System.out.println(discountCard);
        return cardRep.save(discountCard);
    }

    @Cache(id = "number")
    @Transactional
    @Override
    public void deleteById(int number) {
        cardRep.deleteById(number);
    }

    @Cache(id = "number")
    @Transactional
    @Override
    public DiscountCard update(DiscountCard discountCard) {
        return cardRep.save(discountCard);
    }

}