package ru.clevertec.cheque.factory.impl;

import ru.clevertec.cheque.factory.EntityFactory;
import ru.clevertec.cheque.factory.MessageFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.cheque.entity.DiscountCard;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DiscountCardFactory implements EntityFactory<Short, DiscountCard> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Map<Short, DiscountCard> getMapEntity(String path) {
        Map<Short, DiscountCard> discountCards = new HashMap<>();
        try (FileReader reader = new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            DiscountCard[] discountCardsMas = mapper.readValue(reader, DiscountCard[].class);
            Arrays.stream(discountCardsMas).forEach(discountCard -> discountCards.put(discountCard.getNumber(), discountCard));
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, String.format(MessageFactory.ERROR_FILE_NOT_FOUND, path));
        } catch (IOException e) {
            logger.log(Level.ERROR, String.format(MessageFactory.ERROR_READING, path));
        }
        return discountCards;
    }
}
