package util.parsing;

import entity.DiscountCard;
import entity.Position;
import entity.Product;
import exception.OrderParserException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderParser {

    List<Position> parseShoppingList(String data, Map<Integer, Product> products) throws OrderParserException;

    Optional<DiscountCard> parseDiscountCard(String data, Map<Short, DiscountCard> discountCards) throws OrderParserException;

    String parseRequestParameters(String[] idItems, String card);
}
