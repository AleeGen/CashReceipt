package util.calculation.impl;

import entity.CashReceipt;
import entity.DiscountCard;
import util.calculation.CashReceiptCalculator;

import java.util.Optional;

public class DiscountCardCalculator implements CashReceiptCalculator {

    @Override
    public CashReceipt calculate(CashReceipt cashReceipt) {
        Optional<DiscountCard> discountCard = cashReceipt.getDiscountCard();
        if (discountCard.isPresent()) {
<<<<<<< Updated upstream
            double discount = discountCard.get().getDiscount()/100;
=======
            double discount = discountCard.get().getDiscount() / 100;
>>>>>>> Stashed changes
            double totalCost = cashReceipt.getTotalCost();
            cashReceipt.setTotalCost(totalCost - totalCost * discount);
        }
        return cashReceipt;
    }
}
