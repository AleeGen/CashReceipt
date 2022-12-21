package factory.impl;

import factory.EntityFactory;
import factory.MessageFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Product;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProductFactory implements EntityFactory<Integer, Product> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Map<Integer, Product> getMapEntity(String path) {
        Map<Integer, Product> products = new HashMap<>();
        try (FileReader reader = new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            Product[] productMas = mapper.readValue(reader, Product[].class);
            Arrays.stream(productMas).forEach(product -> products.put(product.getId(), product));
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, String.format(MessageFactory.ERROR_FILE_NOT_FOUND, path));
        } catch (IOException e) {
            logger.log(Level.ERROR, String.format(MessageFactory.ERROR_READING, path));
        }
        return products;
    }
}
