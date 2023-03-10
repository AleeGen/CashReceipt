package ru.clevertec.cheque.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.dao.impl.DiscountCardDAO;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.provider.CashReceiptProvider;
import ru.clevertec.cheque.service.impl.DiscountCardService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {
    private static List<DiscountCard> cards;
    @Mock
    private DiscountCardDAO dao;
    @InjectMocks
    private DiscountCardService service;
    @Captor
    private ArgumentCaptor<Integer> captor;

    @BeforeAll
    static void init() {
        cards = CashReceiptProvider.getCards();
    }

    @Test
    void checkGetAllShouldReturnExpected() {
        doReturn(cards).when(dao).getAll();
        List<DiscountCard> actual = service.getAll();
        assertThat(actual).isEqualTo(cards);
    }

    @Test
    void checkGetByIdShouldExecute() {
        int number = 1111;
        service.getById(number);
        verify(dao).getById(number);
    }

    @Test
    void checkSaveShouldExecute() {
        DiscountCard card = cards.get(0);
        service.save(card);
        verify(dao).save(card);
    }

    @Test
    void checkUpdateShouldExecute() {
        DiscountCard card = cards.get(0);
        service.update(card);
        verify(dao).update(card);
    }

    @Nested
    class CheckDelete {
        @Test
        void shouldExecute() {
            int number = 1111;
            service.delete(number);
            verify(dao).delete(number);
        }

        @Test
        void shouldSendCardToDao() {
            Integer numberCard = 1234;
            doNothing().when(dao).delete(any(Integer.class));
            service.delete(numberCard);
            verify(dao).delete(captor.capture());
            Integer actualNumber = captor.getValue();
            assertThat(actualNumber).isEqualTo(numberCard);
        }
    }

}