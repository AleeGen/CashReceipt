package app;

import exception.CashReceiptException;
import factory.impl.DiscountCardDBFactory;
import factory.impl.ProductDBFactory;
import printing.CashReceiptPrinter;
import printing.decorator.BodyDecorator;
import printing.decorator.FooterDecorator;
import printing.decorator.HeaderDecorator;
import util.calculation.impl.DiscountCardCalculator;
import util.calculation.impl.PromotionalProductsCalculator;
import entity.*;
import util.validation.Impl.OrderValidator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class CashRegister {

    public static final String PATH_CASH_RECEIPTS = "src/main/resources/data/cash_receipts.txt";
    public static final String PATH_PRODUCTS = ClassLoader.getSystemResource("data/products.txt").getPath();
    public static final String PATH_DISCOUNT_CARDS = ClassLoader.getSystemResource("data/discount_cards.txt").getPath();
    public static final String CONFIG = "hibernate.cfg.xml";

    private static final Logger logger = LogManager.getLogger();

    private static final String EXIT_COMMAND = "exit";
    private static final String REGEX_FOR_PRODUCTS = "\\d+-\\d+";
    private static final String REGEX_FOR_CARD = "card-\\d{4}";
    private static final String SEPARATOR = "-";

    private static final Map<Integer, Product> products;
    private static final Map<Short, DiscountCard> discountCards;
    private static final Organization organization;

    static {
        products = new ProductDBFactory().getMapEntity(CONFIG);
        discountCards = new DiscountCardDBFactory().getMapEntity(CONFIG);
        organization = new Organization(
                "Clevertec",
                "Москва, Россия ул. Дубининская, д. 90",
                "info@clevertec.ru",
                "+7 (499) 653 94 51");
    }

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_CASH_RECEIPTS, true));
             Scanner scanner = new Scanner(System.in)) {
            String data;
            logger.log(Level.INFO, MessageCashRegister.BEGIN);
            while (!(data = scanner.nextLine()).equalsIgnoreCase(EXIT_COMMAND)) {
                try {
                    if (new OrderValidator().validate(data)) {
                        List<Position> positions = parseShoppingList(data);
                        Optional<DiscountCard> cardOptional = parseDiscountCard(data);
                        CashReceipt cashReceipt = new CashReceipt.CashReceiptBuilder(positions)
                                .addOrganization(organization)
                                .addDate(new Date())
                                .addDiscountCard(cardOptional)
                                .build();
                        new PromotionalProductsCalculator().calculate(cashReceipt);
                        new DiscountCardCalculator().calculate(cashReceipt);
                        String result = new FooterDecorator(new BodyDecorator(new HeaderDecorator(new CashReceiptPrinter()))).print(cashReceipt);
                        writer.write(result);
                        logger.log(Level.INFO, MessageCashRegister.SUCCESSFULLY);
                    } else {
                        logger.log(Level.WARN, String.format(MessageCashRegister.INVALID_SHOPPING_LIST, data));
                    }
                } catch (CashReceiptException e) {
                    logger.log(Level.WARN, e.getMessage());
                }
                logger.log(Level.INFO, MessageCashRegister.ENTER_REQUEST);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, String.format(MessageCashRegister.ERROR_RECORD_CASH_RECEIPT, PATH_CASH_RECEIPTS));
        }
        logger.log(Level.INFO, MessageCashRegister.END);
    }

    private static List<Position> parseShoppingList(String data) throws CashReceiptException {
        List<Position> positions = new ArrayList<>();
        Matcher matcher = Pattern.compile(REGEX_FOR_PRODUCTS).matcher(data);
        while (matcher.find()) {
            String element = matcher.group();
            String[] couple = element.split(SEPARATOR);
            int id = Integer.parseInt(couple[0]);
            int quantity = Integer.parseInt(couple[1]);
            Product product = products.get(id);
            if (product == null) {
                throw new CashReceiptException(String.format(MessageCashRegister.NOT_EXIST_PRODUCT, id));
            }
            positions.add(new Position(quantity, product));
        }
        return positions;
    }

    private static Optional<DiscountCard> parseDiscountCard(String data) throws CashReceiptException {
        Optional<DiscountCard> cardOptional = Optional.empty();
        Matcher matcher = Pattern.compile(REGEX_FOR_CARD).matcher(data);
        if (matcher.find()) {
            String[] couple = matcher.group().split(SEPARATOR);
            short number = Short.parseShort(couple[1]);
            DiscountCard discountCard = discountCards.get(number);
            if (discountCard == null) {
                throw new CashReceiptException(String.format(MessageCashRegister.NOT_EXIST_DISCOUNT_CARD, number));
            }
            cardOptional = Optional.of(discountCard);
        }
        return cardOptional;
    }

}
