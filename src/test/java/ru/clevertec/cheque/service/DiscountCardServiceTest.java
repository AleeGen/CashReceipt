package ru.clevertec.cheque.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.dao.DiscountCardDao;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.provider.CashReceiptProvider;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {
    private static List<DiscountCard> cards;
    @Mock
    private DiscountCardDao dao;
    @InjectMocks
    private DiscountCardService service;
    @Captor
    private ArgumentCaptor<DiscountCard> captor;

    @BeforeAll
    static void init() {
        cards = CashReceiptProvider.getCards().values().stream().toList();
    }

    @Test
    void checkGetAllShouldReturnExpected() {
        Mockito.doReturn(cards).when(dao).getAll();
        Map<Short, DiscountCard> actual = service.getAll();
        Map<Short, DiscountCard> expected = CashReceiptProvider.getCards();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkDeleteShouldExecute() {
        DiscountCard card = cards.get(0);
        Mockito.doNothing().when(dao).delete(card);
        service.delete(card);
        Mockito.verify(dao).delete(card);
    }

    @Test
    void checkDeleteShouldSendCardToDao() {
        DiscountCard extendCard = new DiscountCard((short) 1234, 15);
        Mockito.doNothing().when(dao).delete(any());
        service.delete(extendCard);
        Mockito.verify(dao).delete(captor.capture());
        DiscountCard actualCard = captor.getValue();
        assertThat(actualCard).isEqualTo(extendCard);
    }

}