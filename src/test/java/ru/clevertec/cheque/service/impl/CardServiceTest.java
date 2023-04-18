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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.cheque.dao.CardRepository;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.impl.CardBuilder;
import ru.clevertec.cheque.entity.CashReceiptProvider;
import ru.clevertec.cheque.exception.CardException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    private static List<DiscountCard> cards;
    @Mock
    private CardRepository rep;
    @Mock
    private Page<DiscountCard> page;
    @InjectMocks
    private CardService service;
    @Captor
    private ArgumentCaptor<Integer> captor;

    @BeforeAll
    static void init() {
        cards = CashReceiptProvider.getCards();
    }

    @Test
    void checkFindAllShouldReturnExpected() {
        int nPage = 0;
        int size = 20;
        doReturn(cards).when(page).getContent();
        doReturn(page).when(rep).findAll(any(PageRequest.class));
        List<DiscountCard> actual = service.findAll(nPage, size);
        assertThat(actual).isEqualTo(cards);
    }

    @Nested
    class CheckFindById {
        @Test
        void ShouldExecute() {
            int number = 1111;
            doReturn(Optional.of(CardBuilder.aCard().build())).when(rep).findById(number);
            service.findById(number);
            verify(rep).findById(number);
        }

        @Test
        void ShouldThrowCardException() {
            int number = -1;
            doReturn(Optional.empty()).when(rep).findById(number);
            assertThrows(CardException.class, () -> service.findById(number));
        }
    }

    @Test
    void checkSaveShouldExecute() {
        DiscountCard card = cards.get(0);
        service.save(card);
        verify(rep).save(card);
    }

    @Test
    void checkUpdateShouldExecute() {
        DiscountCard card = cards.get(0);
        service.update(card);
        verify(rep).save(card);
    }

    @Nested
    class CheckDeleteById {
        @Test
        void shouldExecute() {
            int number = 1111;
            service.deleteById(number);
            verify(rep).deleteById(number);
        }

        @Test
        void shouldSendCardToDao() {
            Integer numberCard = 1111;
            service.deleteById(numberCard);
            verify(rep).deleteById(captor.capture());
            Integer actualNumber = captor.getValue();
            assertThat(actualNumber).isEqualTo(numberCard);
        }
    }

}