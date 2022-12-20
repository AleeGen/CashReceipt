package util.calculation;


import entity.CashReceipt;
import helper.HelperTest;
import org.testng.Assert;

import org.testng.annotations.Test;
import util.calculation.impl.DiscountCardCalculator;
import util.calculation.impl.PromotionalProductsCalculator;


public class CashReceiptCalculatorTest {


    @Test
    public void testPromotionalCalculator() {
        PromotionalProductsCalculator calculator = new PromotionalProductsCalculator();
        CashReceipt actual = calculator.calculate(HelperTest.getCashReceipt());
        Assert.assertEquals(actual.getTotalCost(), 48.665);
    }

    @Test
    public void testDiscountCalculator() {
        DiscountCardCalculator calculator = new DiscountCardCalculator();
        CashReceipt actual = calculator.calculate(HelperTest.getCashReceipt());
        Assert.assertEquals(actual.getTotalCost(), 39.688);
    }

}