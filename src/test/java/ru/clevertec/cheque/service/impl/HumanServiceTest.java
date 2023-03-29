package ru.clevertec.cheque.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.dao.impl.HumanDAO;
import ru.clevertec.cheque.entity.Human;
import ru.clevertec.cheque.entity.impl.HumanBuilder;
import ru.clevertec.cheque.exception.HumanException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HumanServiceTest {

    @Mock
    private HumanDAO dao;
    @InjectMocks
    private HumanService service;


    @Test
    void checkGetAllShouldExecute() {
        service.getAll();
        verify(dao).getAll();
    }

    @Nested
    class CheckGetById {

        @Test
        void shouldExecute() {
            Human human = HumanBuilder.aHuman().build();
            doReturn(human).when(dao).getById(human.getId());
            service.getById(human.getId());
            verify(dao).getById(human.getId());
        }

        @Test
        void shouldThrowHumanException() {
            int id = 1;
            doReturn(null).when(dao).getById(1);
            assertThrows(HumanException.class, () -> service.getById(id));
        }
    }

    @Test
    void checkSaveShouldExecute() {
        Human human = HumanBuilder.aHuman().build();
        service.save(human);
        verify(dao).save(human);
    }

    @Test
    void checkDeleteShouldExecute() {
        int id = 1;
        service.delete(id);
        verify(dao).delete(id);
    }

    @Test
    void checkUpdateShouldExecute() {
        Human human = HumanBuilder.aHuman().build();
        service.update(human);
        verify(dao).update(human);
    }
}