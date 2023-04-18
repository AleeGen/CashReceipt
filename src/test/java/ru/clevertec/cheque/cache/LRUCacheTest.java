package ru.clevertec.cheque.cache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.clevertec.cheque.cache.algorithm.Cache;
import ru.clevertec.cheque.cache.algorithm.impl.LRUCache;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.impl.CardBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

    private static Cache<Integer, DiscountCard> cache;
    private static int capacity;

    @BeforeAll
    static void init() {
        capacity = 3;
        cache = new LRUCache<>(capacity);
        List.of(CardBuilder.aCard().withNumber(1).build(),
                        CardBuilder.aCard().withNumber(2).build(),
                        CardBuilder.aCard().withNumber(3).build())
                .forEach(h -> cache.put(h.getNumber(), h));
    }

    @Test
    void checkGetCapacityShouldReturnCapacity() {
        int expected = capacity;
        int actual = cache.getCapacity();
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,0", "4,1", "2,0", "5,3", "1,4"
    })
    void checkCacheShouldStoreCertainElements(int nextId, int remoteId) {
        DiscountCard nextCard = CardBuilder.aCard().withNumber(nextId).build();
        cache.put(nextId, nextCard);
        DiscountCard remoteCard = cache.get(remoteId);
        assertThat(remoteCard).isNull();
    }

    @Test
    void delete() {
        DiscountCard human = CardBuilder.aCard().withNumber(1).build();
        cache.put(human.getNumber(), human);
        boolean exist = cache.get(human.getNumber()) == null;
        assertThat(exist).isFalse();
        cache.delete(human.getNumber());
        exist = cache.get(human.getNumber()) == null;
        assertThat(exist).isTrue();
    }

}