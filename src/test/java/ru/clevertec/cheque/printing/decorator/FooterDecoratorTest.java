package ru.clevertec.cheque.printing.decorator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.printing.decorator.console.FooterDecorator;
import ru.clevertec.cheque.printing.decorator.console.PrinterDecorator;
import ru.clevertec.cheque.provider.CashReceiptProvider;
import ru.clevertec.cheque.util.calculation.impl.DiscountCardCalculator;
import ru.clevertec.cheque.util.calculation.impl.PromotionalProductsCalculator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FooterDecoratorTest {
    private static List<CashReceipt> cashReceipts;

    @Nested
    class ConsolePrint {
        @InjectMocks
        private FooterDecorator decorator;
        @Mock
        private PrinterDecorator printer;

        @BeforeEach
        void setUp() {
            cashReceipts = CashReceiptProvider.getCashReceipts();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/console/footer_without_discount_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnWithoutDiscount(String expected, int indexCashReceipt) {
            CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            assertThat(actual).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/console/footer_with_discount_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnWithDiscount(String expected, int indexCashReceipt) {
            CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
            new PromotionalProductsCalculator().calculate(cashReceipt);
            new DiscountCardCalculator().calculate(cashReceipt);
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class HtmlPrint {
        @InjectMocks
        private ru.clevertec.cheque.printing.decorator.html.FooterDecorator decorator;
        @Mock
        private ru.clevertec.cheque.printing.decorator.html.PrinterDecorator printer;

        @BeforeEach
        void setUp() {
            cashReceipts = CashReceiptProvider.getCashReceipts();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/html/footer_without_discount_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnWithoutDiscount(String expected, int indexCashReceipt) {
            CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
            Mockito.doReturn("").when(printer).print(cashReceipt);
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
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            expected = expected.replaceAll("\r\n", "");
            assertThat(actual).isEqualTo(expected);
        }
    }

}