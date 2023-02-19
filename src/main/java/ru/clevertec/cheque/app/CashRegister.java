package ru.clevertec.cheque.app;

import ru.clevertec.cheque.exception.OrderParserException;
import ru.clevertec.cheque.factory.impl.DiscountCardFactory;
import ru.clevertec.cheque.factory.impl.ProductFactory;
import ru.clevertec.cheque.printing.decorator.console.CashReceiptPrinter;
import ru.clevertec.cheque.printing.decorator.console.BodyDecorator;
import ru.clevertec.cheque.printing.decorator.console.FooterDecorator;
import ru.clevertec.cheque.printing.decorator.console.HeaderDecorator;
import ru.clevertec.cheque.entity.*;
import ru.clevertec.cheque.util.calculation.impl.DiscountCardCalculator;
import ru.clevertec.cheque.util.calculation.impl.PromotionalProductsCalculator;
import ru.clevertec.cheque.util.parsing.OrderParser;
import ru.clevertec.cheque.util.parsing.impl.ConsoleOrderParser;
import ru.clevertec.cheque.util.validation.impl.OrderValidator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class CashRegister {
    private static final Logger logger = LogManager.getLogger();
    public static final String PATH_CASH_RECEIPTS = "src/main/resources/data/cash_receipts.txt";
    public static final String PATH_PRODUCTS = ClassLoader.getSystemResource("data/products.txt").getPath();
    public static final String PATH_DISCOUNT_CARDS = ClassLoader.getSystemResource("data/discount_cards.txt").getPath();
    private static final String EXIT_COMMAND = "exit";
    private static final Map<Integer, Product> products;
    private static final Map<Short, DiscountCard> discountCards;
    private static final Organization organization;

    static {
        products = new ProductFactory().getMapEntity(PATH_PRODUCTS);
        discountCards = new DiscountCardFactory().getMapEntity(PATH_DISCOUNT_CARDS);
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
                        OrderParser parser = new ConsoleOrderParser();
                        List<Position> positions = parser.parseShoppingList(data, products);
                        Optional<DiscountCard> cardOptional = parser.parseDiscountCard(data, discountCards);
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
                } catch (OrderParserException e) {
                    logger.log(Level.WARN, e.getMessage());
                }
                logger.log(Level.INFO, MessageCashRegister.ENTER_REQUEST);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, String.format(MessageCashRegister.ERROR_RECORD_CASH_RECEIPT, PATH_CASH_RECEIPTS));
        }
        logger.log(Level.INFO, MessageCashRegister.END);
    }
}
