package factory.impl;

import entity.DiscountCard;
import factory.EntityFactory;
import factory.MessageFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DiscountCardFactory implements EntityFactory<Short, DiscountCard> {
    private static final String PART_OF_KEY = "Card-";
    private static final String PART_OF_VALUE = "%";

    @Override
    public Map<Short, DiscountCard> getListEntity(String path) {
        Map<Short, DiscountCard> discountCards = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Properties properties = new Properties();
            properties.load(reader);
            for (var o : properties.entrySet()) {
                String key = ((String) o.getKey()).replace(PART_OF_KEY, "");
                short number = Short.parseShort(key);
                String value = ((String) o.getValue()).replace(PART_OF_VALUE, "");
                double discount = Double.parseDouble(value) / 100;
                discountCards.put(number, new DiscountCard(number, discount));
            }
        } catch (FileNotFoundException e) {
            System.out.printf((MessageFactory.FILE_NOT_FOUND) + "%n", path);
        } catch (IOException e) {
            System.out.printf((MessageFactory.ERROR_READING) + "%n", path);
        } catch (NumberFormatException e) {
            System.out.printf((MessageFactory.INVALID_FORMAT) + "%n", path);
        }
        return discountCards;
    }
}
