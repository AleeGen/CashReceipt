package ru.clevertec.cheque.entity.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.EntityBuilder;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCard")
public class CardBuilder implements EntityBuilder<DiscountCard> {

    private int number = 0;
    private double discount = 10;

    @Override
    public DiscountCard build() {
        final DiscountCard card = new DiscountCard();
        card.setNumber(number);
        card.setDiscount(discount);
        return card;
    }

}