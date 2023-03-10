package ru.clevertec.cheque.cache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.clevertec.cheque.entity.Human;
import ru.clevertec.cheque.entity.impl.HumanBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

    private static Cache<Integer, Human> cache;
    private static int capacity;

    @BeforeAll
    static void init() {
        capacity = 3;
        cache = new LRUCache<>(capacity);
        List.of(HumanBuilder.aHuman().withId(1).build(),
                        HumanBuilder.aHuman().withId(2).build(),
                        HumanBuilder.aHuman().withId(3).build())
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
            "3,0", "4,1", "2,0", "5,3", "1,4"
    })
    void checkCacheShouldStoreCertainElements(int nextId, int remoteId) {
        Human nextHuman = HumanBuilder.aHuman().withId(nextId).build();
        cache.put(nextId, nextHuman);
        Human remoteHuman = cache.get(remoteId);
        assertThat(remoteHuman).isNull();
    }

    @Test
    void delete() {
        Human human = HumanBuilder.aHuman().withId(1).build();
        cache.put(human.getId(), human);
        boolean exist = cache.get(human.getId()) == null;
        assertThat(exist).isFalse();
        cache.delete(human.getId());
        exist = cache.get(human.getId()) == null;
        assertThat(exist).isTrue();
    }
}