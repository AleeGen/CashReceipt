package factory.impl;

import factory.MessageFactory;
import factory.EntityFactory;
import entity.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProductFactory implements EntityFactory<Integer, Product> {
    private static final String SEPARATOR = "\\s*\\|\\s*";

    @Override
    public Map<Integer, Product> getListEntity(String path) {
        Map<Integer, Product> products = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while (reader.ready()) {
                String[] data = reader.readLine().split(SEPARATOR);
                if (data.length != 4) {
                    throw new NumberFormatException();
                }
                int id = Integer.parseInt(data[0]);
                String description = data[1];
                double price = Double.parseDouble(data[2]);
                boolean promotional = Boolean.parseBoolean(data[3]);
                Product product = new Product(id, description, price);
                product.setPromotional(promotional);
                products.put(id, product);
            }
        } catch (FileNotFoundException e) {
            System.out.printf((MessageFactory.FILE_NOT_FOUND) + "%n", path);
        } catch (IOException e) {
            System.out.printf((MessageFactory.ERROR_READING) + "%n", path);
        } catch (NumberFormatException e) {
            System.out.printf((MessageFactory.INVALID_FORMAT) + "%n", path);
        }
        return products;
    }
}
