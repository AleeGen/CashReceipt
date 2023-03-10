package ru.clevertec.cheque.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.dao.impl.DiscountCardDAO;
import ru.clevertec.cheque.dao.impl.ProductDAO;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.exception.CashReceiptException;
import ru.clevertec.cheque.provider.CashReceiptProvider;
import ru.clevertec.cheque.service.util.calculator.impl.DiscountCardCalculator;
import ru.clevertec.cheque.service.util.calculator.impl.PromotionalProductsCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CashReceiptServiceTest {
    @Mock
    private DiscountCardDAO cardDao;
    @Mock
    private ProductDAO productDao;
    @InjectMocks
    private CashReceiptService service;
    private static CashReceipt cashReceipt;
    private static Integer[] itemId;
    private static Integer numberCard;

    @BeforeAll
    static void init() {
        cashReceipt = CashReceiptProvider.getCashReceipts().get(0);
        itemId = new Integer[]{1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3};
        numberCard = 1111;
        new PromotionalProductsCalculator().calculate(cashReceipt);
        new DiscountCardCalculator().calculate(cashReceipt);
    }

    @Test
    void checkGetCashReceiptShouldExpected() throws CashReceiptException {
        doReturn(cashReceipt.getPositions().get(0).getProduct()).when(productDao).getById(1);
        doReturn(cashReceipt.getPositions().get(1).getProduct()).when(productDao).getById(2);
        doReturn(cashReceipt.getPositions().get(2).getProduct()).when(productDao).getById(3);
        doReturn(cashReceipt.getDiscountCard().get()).when(cardDao).getById(numberCard);
        CashReceipt actual = service.getCashReceipt(itemId, numberCard);
        assertAll(
                () -> assertThat(actual.getCost()).isEqualTo(cashReceipt.getCost()),
                () -> assertThat(actual.getTotalCost()).isEqualTo(cashReceipt.getTotalCost()),
                () -> assertThat(actual.getPositions()).isEqualTo(cashReceipt.getPositions()),
                () -> assertThat(actual.getDiscountCard()).isEqualTo(cashReceipt.getDiscountCard())
        );
    }

    @Nested
    class CheckGetCashReceiptShouldThrowException {
        @Test
        void nonExistentProduct() {
            int nonExistentId = -1;
            int anyCard = 1111;
            doReturn(null).when(productDao).getById(nonExistentId);
            assertThrows(CashReceiptException.class, () -> service.getCashReceipt(new Integer[]{nonExistentId}, anyCard));
        }

        @Test
        void nonExistentCard() {
            int anyProduct = 1;
            int nonExistentCard = 1000;
            doReturn(new Product()).when(productDao).getById(anyProduct);
            doReturn(null).when(cardDao).getById(nonExistentCard);
            assertThrows(CashReceiptException.class, () -> service.getCashReceipt(new Integer[]{anyProduct}, nonExistentCard));
        }
    }

}