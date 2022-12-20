package util.validation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.validation.Impl.ContainsCardValidator;
import util.validation.Impl.OrderValidator;

import static org.testng.Assert.*;

public class ValidatorTest {

    private final OrderValidator orderValidator = new OrderValidator();
    private final ContainsCardValidator cardValidator = new ContainsCardValidator();

    @DataProvider(name = "orders")
    public Object[][] createDataOrders() {
        Object[][] data = new Object[10][2];
        data[0] = new Object[]{"", false};
        data[1] = new Object[]{"    ", false};
        data[2] = new Object[]{"1-a", false};
        data[3] = new Object[]{"1-2-3-4", false};
        data[4] = new Object[]{"-6-3", false};
        data[5] = new Object[]{"3-4 card1234", false};
        data[6] = new Object[]{"card-1234", false};
        data[7] = new Object[]{"1-1", true};
        data[8] = new Object[]{"1-2 23-6 5-13", true};
        data[9] = new Object[]{"3-1 2-5 5-1 card-1234", true};
        return data;
    }

    @DataProvider(name = "cards")
    public Object[][] createDataCards() {
        Object[][] data = new Object[6][2];
        data[0] = new Object[]{"", false};
        data[1] = new Object[]{"3-4 card1234", false};
        data[2] = new Object[]{"1-1", false};
        data[3] = new Object[]{"1-2 23-6 5-13", false};
        data[4] = new Object[]{"card-1234", true};
        data[5] = new Object[]{"3-1 2-5 5-1 card-1234", true};
        return data;
    }

    @Test(dataProvider = "orders")
    public void testValidateOrder(String data, boolean expected) {
        boolean actual = orderValidator.validate(data);
        assertEquals(actual, expected, data + " - Invalid!");
    }

    @Test(dataProvider = "cards")
    public void testValidateCards(String data, boolean expected) {
        boolean actual = cardValidator.validate(data);
        assertEquals(actual, expected, data + " - Invalid!");
    }


}