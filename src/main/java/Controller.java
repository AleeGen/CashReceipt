import app.MessageCashRegister;
import entity.*;
<<<<<<< Updated upstream
import exception.CashReceiptException;
=======
import exception.OrderParserException;
>>>>>>> Stashed changes
import factory.impl.DiscountCardDBFactory;
import factory.impl.ProductDBFactory;

import printing.html.CashReceiptPrinter;
import printing.html.decorator.BodyDecorator;
import printing.html.decorator.FooterDecorator;
import printing.html.decorator.HeaderDecorator;
import util.calculation.impl.DiscountCardCalculator;
import util.calculation.impl.PromotionalProductsCalculator;
<<<<<<< Updated upstream
import util.validation.Impl.OrderValidator;
=======
import util.parsing.OrderParser;
import util.parsing.impl.ConsoleOrderParser;
import util.validation.impl.OrderValidator;
>>>>>>> Stashed changes
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
<<<<<<< Updated upstream
import java.util.regex.Matcher;
import java.util.regex.Pattern;
=======

>>>>>>> Stashed changes

@WebServlet(name = "check", value = "/check")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();
    private static final String CONFIG = "hibernate.cfg.xml";
<<<<<<< Updated upstream
    private static final String REGEX_FOR_PRODUCTS = "\\d+-\\d+";
    private static final String REGEX_FOR_CARD = "card-\\d{4}";
    private static final String SEPARATOR = "-";
=======
>>>>>>> Stashed changes
    private final Map<Integer, Product> products = new ProductDBFactory().getMapEntity(CONFIG);
    private final Map<Short, DiscountCard> discountCards = new DiscountCardDBFactory().getMapEntity(CONFIG);
    private final Organization organization = new Organization(
            "Clevertec",
            "Москва, Россия ул. Дубининская, д. 90",
            "info@clevertec.ru",
            "+7 (499) 653 94 51");


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=Windows-1251");
<<<<<<< Updated upstream
        String result = generate(parseRequestParameters(request));
=======
        String[] idItems = request.getParameterValues("itemId");
        String card = request.getParameter("card");
        String data = new ConsoleOrderParser().parseRequestParameters(idItems, card);
        String result = generate(data);
>>>>>>> Stashed changes
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    private String generate(String data) {
        String result = null;
        try {
            if (new OrderValidator().validate(data)) {
<<<<<<< Updated upstream
                List<Position> positions = parseShoppingList(data);
                Optional<DiscountCard> cardOptional = parseDiscountCard(data);
=======
                OrderParser parser = new ConsoleOrderParser();
                List<Position> positions = parser.parseShoppingList(data, products);
                Optional<DiscountCard> cardOptional = parser.parseDiscountCard(data, discountCards);
>>>>>>> Stashed changes
                CashReceipt cashReceipt = new CashReceipt.CashReceiptBuilder(positions)
                        .addOrganization(organization)
                        .addDate(new Date())
                        .addDiscountCard(cardOptional)
                        .build();
                new PromotionalProductsCalculator().calculate(cashReceipt);
                new DiscountCardCalculator().calculate(cashReceipt);
                result = new FooterDecorator(new BodyDecorator(new HeaderDecorator(new CashReceiptPrinter()))).print(cashReceipt);
                logger.log(Level.INFO, MessageCashRegister.SUCCESSFULLY);
            } else {
                logger.log(Level.WARN, String.format(MessageCashRegister.INVALID_SHOPPING_LIST, data));
            }
<<<<<<< Updated upstream
        } catch (CashReceiptException e) {
=======
        } catch (OrderParserException e) {
>>>>>>> Stashed changes
            logger.log(Level.WARN, e.getMessage());
        }
        return result;
    }
<<<<<<< Updated upstream

    private String parseRequestParameters(HttpServletRequest request) {
        String[] idList = request.getParameterValues("itemId");
        Map<String, Integer> map = new HashMap<>();
        for (String id : idList) {
            if (map.containsKey(id)) {
                map.put(id, map.get(id) + 1);
            } else {
                map.put(id, 1);
            }
        }
        StringBuilder result = new StringBuilder();
        for (String id : map.keySet()) {
            result.append(id).append("-").append(map.get(id)).append(" ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 1));
        String card = request.getParameter("card");
        if (card != null) {
            result.append(" card-").append(card);
        }
        return result.toString();
    }

    private List<Position> parseShoppingList(String data) throws CashReceiptException {
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

    private Optional<DiscountCard> parseDiscountCard(String data) throws CashReceiptException {
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

=======
>>>>>>> Stashed changes
}