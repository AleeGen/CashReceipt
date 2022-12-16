import entity.DiscountCard;
import entity.Organization;
import factory.EntityFactory;
import factory.impl.DiscountCardFactory;
import factory.impl.ProductFactory;
import entity.Product;
import util.validation.Impl.ContainsCardValidator;
import util.validation.Impl.OrderValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


public class CashRegister {

    private static final String PATH_PRODUCTS;
    private static final String PATH_DISCOUNT_CARDS;
    private static final Map<Integer, Product> products;
    private static final Map<Short, DiscountCard> discountCards;
    private static final Organization organization;
    private static final String EXIT_COMMAND = "exit";
    private static final int REQUIRED_NUMBER_PRODUCTS_TO_DISCOUNT = 5;
    private static final double DISCOUNT = 0.1;

    static {
        PATH_PRODUCTS = ClassLoader.getSystemResource("products.txt").getPath();
        EntityFactory<Integer, Product> productFactory = new ProductFactory();
        products = productFactory.getListEntity(PATH_PRODUCTS);
        PATH_DISCOUNT_CARDS = ClassLoader.getSystemResource("discount_cards.properties").getPath();
        EntityFactory<Short, DiscountCard> discountCardFactory = new DiscountCardFactory();
        discountCards = discountCardFactory.getListEntity(PATH_DISCOUNT_CARDS);
        organization = new Organization(
                "Clevertec",
                "Москва, Россия ул. Дубининская, д. 90",
                "info@clevertec.ru",
                "+7 (499) 653 94 51");

    }

    public static void main(String[] args) {
        System.out.println("Start");
        String[] arg = new String[]{"3-1", "2-5", "5-1", "card-5555"};
        StringBuilder str = new StringBuilder();
        for (String element : arg) {
            str.append(element).append(" ");
        }
        String data = str.substring(0, str.length() - 1);
        Optional<DiscountCard> card = Optional.empty();
        do {
            if (new OrderValidator().validate(data)) {
                if (new ContainsCardValidator().validate(data)) {
                    String[] mas = data.split("\s+");
                    String discountCard = mas[mas.length - 1];
                    card = Optional.of(getDiscountCard(discountCard));
                    data = data.replace(discountCard, "");
                }
                Map<Integer, Integer> productsToBuy = parseShoppingList(data);
                double totalCost = calculate(productsToBuy, card);
                System.out.println(totalCost);
            } else {
                System.out.printf((MessageCashRegister.INVALID_SHOPPING_LIST) + "%n", data);
            }
            card = Optional.empty();
        } while (!(data = new Scanner(System.in).nextLine()).equalsIgnoreCase(EXIT_COMMAND));
        System.out.println("End!");

    }

    private static Map<Integer, Integer> parseShoppingList(String data) {
        Map<Integer, Integer> productsToBuy = new HashMap<>();
        for (String element : data.split(" ")) {
            String[] couple = element.split("-");
            int id = Integer.parseInt(couple[0]);
            int quantity = Integer.parseInt(couple[1]);
            productsToBuy.put(id, quantity);
        }
        return productsToBuy;
    }

    private static DiscountCard getDiscountCard(String discountCard) {
        String[] couple = discountCard.split("-");
        short number = Short.parseShort(couple[1]);
        return discountCards.get(number);
    }

    private static double calculate(Map<Integer, Integer> productList, Optional<DiscountCard> cardOptional) {
        double totalCost = 0;
        for (int id : productList.keySet()) {
            Product product = products.get(id);
            double price = product.getPrice();
            int quantity = productList.get(id);
            double cost = price * quantity;
            if (product.isPromotional() && quantity > REQUIRED_NUMBER_PRODUCTS_TO_DISCOUNT) {
                cost = cost - cost * DISCOUNT;
            }
            totalCost += cost;
        }
        if (cardOptional.isPresent()) {
            double discount = cardOptional.get().getDiscount();
            totalCost = totalCost - totalCost * discount;
        }
        return totalCost;
    }

}
