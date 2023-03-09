package ru.clevertec.cheque.service.util.printing.decorator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.service.util.printing.decorator.FooterDecorator;
import ru.clevertec.cheque.service.util.printing.decorator.PrinterDecorator;
import ru.clevertec.cheque.provider.CashReceiptProvider;
import ru.clevertec.cheque.service.util.calculator.impl.DiscountCardCalculator;
import ru.clevertec.cheque.service.util.calculator.impl.PromotionalProductsCalculator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FooterDecoratorTest {
    private static List<CashReceipt> cashReceipts;
    @InjectMocks
    private FooterDecorator decorator;
    @Mock
    private PrinterDecorator printer;

    @BeforeEach
    void setUp() {
        cashReceipts = CashReceiptProvider.getCashReceipts();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/decorator/html/footer_without_discount_decorator.csv", lineSeparator = "|", delimiterString = "->",
            ignoreLeadingAndTrailingWhitespace = false)
    void checkPrintShouldReturnWithoutDiscount(String expected, int indexCashReceipt) {
        CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
        doReturn("").when(printer).print(cashReceipt);
        String actual = decorator.print(cashReceipt);
        expected = expected.replaceAll("\r\n", "");
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/decorator/html/footer_with_discount_decorator.csv", lineSeparator = "|", delimiterString = "->",
            ignoreLeadingAndTrailingWhitespace = false)
    void checkPrintShouldReturnWithDiscount(String expected, int indexCashReceipt) {
        CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
        new PromotionalProductsCalculator().calculate(cashReceipt);
        new DiscountCardCalculator().calculate(cashReceipt);
        doReturn("").when(printer).print(cashReceipt);
        String actual = decorator.print(cashReceipt);
        expected = expected.replaceAll("\r\n", "");
        assertThat(actual).isEqualTo(expected);
    }


}