package ru.clevertec.cheque.cache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.clevertec.cheque.cache.algorithm.Cache;
import ru.clevertec.cheque.cache.algorithm.impl.LFUCache;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.entity.impl.ProductBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LFUCacheTest {

    private static Cache<Integer, Product> cache;
    private static int capacity;

    @BeforeAll
    static void init() {
        capacity = 3;
        cache = new LFUCache<>(capacity);
        List.of(ProductBuilder.aProduct().withId(1).build(),
                        ProductBuilder.aProduct().withId(2).build(),
                        ProductBuilder.aProduct().withId(3).build())
                .forEach(h -> cache.put(h.getId(), h));
    }

    @Test
    void checkGetCapacityShouldReturnCapacity() {
        int expected = capacity;
        int actual = cache.getCapacity();
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,0", "4,1", "2,0", "5,4", "1,5"
    })
    void checkCacheShouldStoreCertainElements(int nextId, int remoteId) {
        Product nextProduct = ProductBuilder.aProduct().withId(nextId).build();
        cache.put(nextId, nextProduct);
        Product remoteProduct = cache.get(remoteId);
        assertThat(remoteProduct).isNull();
    }

    @Test
    void delete() {
        Product product = ProductBuilder.aProduct().withId(1).build();
        cache.put(product.getId(), product);
        boolean exist = cache.get(product.getId()) == null;
        assertThat(exist).isFalse();
        cache.delete(product.getId());
        exist = cache.get(product.getId()) == null;
        assertThat(exist).isTrue();
    }

}