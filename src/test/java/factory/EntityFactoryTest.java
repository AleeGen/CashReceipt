package factory;

import app.CashRegister;
import entity.DiscountCard;
import entity.Product;
import factory.impl.DiscountCardDBFactory;
import factory.impl.DiscountCardFactory;
import factory.impl.ProductDBFactory;
import factory.impl.ProductFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class EntityFactoryTest {

    private Map<Integer, Product> products = new HashMap<>() {{
        put(0, new Product(0, "milk", 1.56, true));
        put(1, new Product(1, "bread", 0.87, false));
        put(2, new Product(2, "loaf", 0.90, false));
        put(3, new Product(3, "chicken", 6.45, true));
        put(4, new Product(4, "cookies", 11.30, true));
        put(5, new Product(5, "eggs", 3.05, false));
        put(6, new Product(6, "cottage cheese", 1.45, false));
        put(7, new Product(7, "marshmallows", 3.78, false));
        put(8, new Product(8, "sour cream", 4.15, true));
        put(9, new Product(9, "onion", 2.00, false));
        put(10, new Product(10, "cucumbers", 1.80, true));
        put(11, new Product(11, "tomatoes", 2.45, true));
        put(12, new Product(12, "watermelon", 1.78, false));
        put(13, new Product(13, "melon", 2.56, false));
        put(14, new Product(14, "garlic", 1.40, false));
    }};
    private Map<Short, DiscountCard> cards = new HashMap<>() {{
        put((short) 1111, new DiscountCard((short) 1111, 5));
        put((short) 2222, new DiscountCard((short) 2222, 10));
        put((short) 3333, new DiscountCard((short) 3333, 15));
        put((short) 4444, new DiscountCard((short) 4444, 20));
        put((short) 5555, new DiscountCard((short) 5555, 25));
    }};

    @Test
    public void testGetProduct() {
        ProductFactory factory = new ProductFactory();
        Map<Integer, Product> actual = factory.getMapEntity(CashRegister.PATH_PRODUCTS);
        Assert.assertEquals(actual, products);
    }

    @Test
    public void testGetDiscountCard() {
        DiscountCardFactory factory = new DiscountCardFactory();
        Map<Short, DiscountCard> actual = factory.getMapEntity(CashRegister.PATH_DISCOUNT_CARDS);
        Assert.assertEquals(actual, cards);
    }

    @Test
    public void testGetProductDB() {
        ProductDBFactory factory = new ProductDBFactory();
        Map<Integer, Product> actual = factory.getMapEntity(CashRegister.CONFIG);
        Assert.assertEquals(actual, products);
    }

    @Test
    public void testGetDiscountCardDB() {
        DiscountCardDBFactory factory = new DiscountCardDBFactory();
        Map<Short, DiscountCard> actual = factory.getMapEntity(CashRegister.CONFIG);
        Assert.assertEquals(actual, cards);
    }
}