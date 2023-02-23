package ru.clevertec.cheque.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import ru.clevertec.cheque.entity.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CashReceiptProvider {
    private static final String PATH_PRODUCTS = ClassLoader.getSystemResource("products.txt").getPath();
    public static final String PATH_DISCOUNT_CARDS = ClassLoader.getSystemResource("discount_cards.txt").getPath();

    public static List<CashReceipt> getCashReceipts() {
        List<Position> positions = getPosition();
        return List.of(
                new CashReceipt.CashReceiptBuilder(List.of(positions.get(0), positions.get(1), positions.get(2)))
                        .addDiscountCard(Optional.of(getCards().get((short) 1111)))
                        .addOrganization(new Organization("organization1", "address1", "email1", "phone1"))
                        .addDate(new Date(1))
                        .build(),
                new CashReceipt.CashReceiptBuilder(List.of(positions.get(3), positions.get(4), positions.get(5)))
                        .addDiscountCard(Optional.empty())
                        .addOrganization(new Organization("organization2", "address2", "email2", "phone2"))
                        .addDate(new Date(1))
                        .build());
    }

    public static List<Position> getPosition() {
        Map<Integer, Product> products = getProducts();
        return List.of(
                new Position(7, products.get(1)),
                new Position(8, products.get(2)),
                new Position(4, products.get(3)),
                new Position(6, products.get(4)),
                new Position(10, products.get(5)),
                new Position(3, products.get(6)),
                new Position(1, products.get(7)),
                new Position(2, products.get(8)),
                new Position(9, products.get(9)),
                new Position(5, products.get(10)));
    }

    public static Map<Integer, Product> getProducts() {
        try (FileReader reader = new FileReader(PATH_PRODUCTS)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(reader, new TypeReference<List<Product>>() {
            }).stream().collect(Collectors.toMap(Product::getId, o -> o));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Short, DiscountCard> getCards() {
        try (FileReader reader = new FileReader(PATH_DISCOUNT_CARDS)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(reader, new TypeReference<List<DiscountCard>>() {
            }).stream().collect(Collectors.toMap(DiscountCard::getNumber, o -> o));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
