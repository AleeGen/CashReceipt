package ru.clevertec.cheque.util.parsing;

import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.Position;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.exception.OrderParserException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderParser {

    List<Position> parseShoppingList(String data, Map<Integer, Product> products) throws OrderParserException;

    Optional<DiscountCard> parseDiscountCard(String data, Map<Short, DiscountCard> discountCards) throws OrderParserException;

    String parseRequestParameters(String[] idItems, String card);
}
