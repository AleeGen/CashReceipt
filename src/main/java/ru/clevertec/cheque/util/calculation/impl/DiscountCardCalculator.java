package ru.clevertec.cheque.util.calculation.impl;

import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.util.calculation.CashReceiptCalculator;

import java.util.Optional;

public class DiscountCardCalculator implements CashReceiptCalculator {
    @Override
    public CashReceipt calculate(CashReceipt cashReceipt) {
        Optional<DiscountCard> discountCard = cashReceipt.getDiscountCard();
        if (discountCard.isPresent()) {
            double discount = discountCard.get().getDiscount() / 100;
            double totalCost = cashReceipt.getTotalCost();
            cashReceipt.setTotalCost(totalCost - totalCost * discount);
        }
        return cashReceipt;
    }
}
