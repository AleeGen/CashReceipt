package ru.clevertec.cheque.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.cheque.app.MessageCashRegister;
import ru.clevertec.cheque.entity.*;
import ru.clevertec.cheque.exception.OrderParserException;
import ru.clevertec.cheque.printing.html.CashReceiptPrinter;
import ru.clevertec.cheque.printing.html.decorator.*;
import ru.clevertec.cheque.service.*;
import ru.clevertec.cheque.util.calculation.impl.*;
import ru.clevertec.cheque.util.parsing.OrderParser;
import ru.clevertec.cheque.util.parsing.impl.ConsoleOrderParser;
import ru.clevertec.cheque.util.validation.impl.OrderValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CashReceiptController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ProductService productService;
    @Autowired
    private DiscountCardService discountCardService;
    private Map<Integer, Product> products;
    private Map<Short, DiscountCard> discountCards;
    private final Organization organization;

    {
        organization = new Organization(
                "Clevertec",
                "Москва, Россия ул. Дубининская, д. 90",
                "info@clevertec.ru",
                "+7 (499) 653 94 51");
        products = productService.getAll();
        discountCards = discountCardService.getAll();
    }


    @GetMapping("/cheque")
    public String getCheque(@RequestParam String[] itemId, @RequestParam(required = false) String card) {
        String data = new ConsoleOrderParser().parseRequestParameters(itemId, card);
        return generate(data);
    }

    private String generate(String data) {
        String result = null;
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
                result = new FooterDecorator(new BodyDecorator(new HeaderDecorator(new CashReceiptPrinter())))
                        .print(cashReceipt);
                logger.log(Level.INFO, MessageCashRegister.SUCCESSFULLY);
            } else {
                logger.log(Level.WARN, String.format(MessageCashRegister.INVALID_SHOPPING_LIST, data));
            }
        } catch (OrderParserException e) {
            logger.log(Level.WARN, e.getMessage());
        }
        return result;
    }

}
