package ru.clevertec.cheque.util.parsing.impl;

import ru.clevertec.cheque.app.MessageCashRegister;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.Position;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.exception.OrderParserException;
import ru.clevertec.cheque.util.parsing.OrderParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleOrderParser implements OrderParser {
    private static final String REGEX_FOR_PRODUCTS = "\\d+-\\d+";
    private static final String SEPARATOR = "-";
    private static final String REGEX_FOR_CARD = "card-\\d{4}";

    @Override
    public List<Position> parseShoppingList(String data, Map<Integer, Product> products) throws OrderParserException {
        List<Position> positions = new ArrayList<>();
        Matcher matcher = Pattern.compile(REGEX_FOR_PRODUCTS).matcher(data);
        while (matcher.find()) {
            String element = matcher.group();
            String[] couple = element.split(SEPARATOR);
            int id = Integer.parseInt(couple[0]);
            int quantity = Integer.parseInt(couple[1]);
            Product product = products.get(id);
            if (product == null) {
                throw new OrderParserException(String.format(MessageCashRegister.NOT_EXIST_PRODUCT, id));
            }
            positions.add(new Position(quantity, product));
        }
        return positions;
    }

    @Override
    public Optional<DiscountCard> parseDiscountCard(String data, Map<Short, DiscountCard> discountCards) throws OrderParserException {
        Optional<DiscountCard> cardOptional = Optional.empty();
        Matcher matcher = Pattern.compile(REGEX_FOR_CARD).matcher(data);
        if (matcher.find()) {
            String[] couple = matcher.group().split(SEPARATOR);
            short number = Short.parseShort(couple[1]);
            DiscountCard discountCard = discountCards.get(number);
            if (discountCard == null) {
                throw new OrderParserException(String.format(MessageCashRegister.NOT_EXIST_DISCOUNT_CARD, number));
            }
            cardOptional = Optional.of(discountCard);
        }
        return cardOptional;
    }

    @Override
    public String parseRequestParameters(String[] idItems, String card) {
        Map<String, Integer> map = new HashMap<>();
        for (String id : idItems) {
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
        if (card != null) {
            result.append(" card-").append(card);
        }
        return result.toString();
    }
}
