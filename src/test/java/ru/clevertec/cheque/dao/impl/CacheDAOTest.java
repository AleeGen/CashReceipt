package ru.clevertec.cheque.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.entity.Human;
import ru.clevertec.cheque.entity.impl.HumanBuilder;
import ru.clevertec.cheque.cache.Cache;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheDAOTest {

    private static int capacity;
    private static List<Human> humans;
    @Mock
    private Cache<Integer, Human> cache;
    @Mock
    private HumanDAO dao;
    @InjectMocks
    private CacheDAO cacheDAO;


    @BeforeAll
    static void init() {
        capacity = 3;
        humans = args().map(a -> a.get()[0]).map(id -> HumanBuilder.aHuman().withId((int) id).build()).toList();
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, false),
                Arguments.of(4, false),
                Arguments.of(5, false));
    }


    @Nested
    class CheckGetAll {
        @Test
        void shouldCallEntityDAO() {
            cacheDAO.getAll();
            verify(dao).getAll();
        }

        @Test
        void quantityCallCacheShouldIsNoMoreSizeCache() {
            doReturn(humans).when(dao).getAll();
            doReturn(capacity).when(cache).getCapacity();
            cacheDAO.getAll();
            verify(cache, Mockito.times(cache.getCapacity())).put(any(Integer.class), any(Human.class));
        }
    }


    @ParameterizedTest
    @MethodSource(value = "args")
    void checkGetByIdShouldCallCorrectOrder(int id, boolean isInCache) {
        Human human = HumanBuilder.aHuman().withId(id).build();
        var proviso = isInCache ?
                doReturn(human).when(cache).get(id) :
                doReturn(null).when(cache).get(id);
        cacheDAO.getById(id);
        verify(cache).get(id);
        var result = isInCache ?
                verify(dao, never()).getById(id) :
                verify(dao).getById(id);
    }

    @Test
    void checkSaveShouldCallCacheAndEntityDAO() {
        Human human = HumanBuilder.aHuman().build();
        cacheDAO.save(human);
        verify(dao).save(human);
        verify(cache).put(human.getId(), human);
    }

    @Test
    void checkDeleteShouldCallCacheAndEntityDAO() {
        int idHuman = 1;
        cacheDAO.delete(idHuman);
        verify(dao).delete(idHuman);
        verify(cache).delete(idHuman);
    }

    @Test
    void checkUpdateShouldCallCacheAndEntityDAO() {
        Human human = HumanBuilder.aHuman().build();
        cacheDAO.update(human);
        verify(dao).update(human);
        verify(cache).put(human.getId(), human);
    }
}