package ru.clevertec.cheque.printing.decorator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.printing.decorator.console.HeaderDecorator;
import ru.clevertec.cheque.printing.decorator.console.PrinterDecorator;
import ru.clevertec.cheque.provider.CashReceiptProvider;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HeaderDecoratorTest {
    private static CashReceipt cashReceipt;

    @Nested
    class ConsolePrint {
        @InjectMocks
        private HeaderDecorator decorator;
        @Mock
        private PrinterDecorator printer;

        @BeforeAll
        static void init() {
            cashReceipt = CashReceiptProvider.getCashReceipts().get(0);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/console/header_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnExpected(String expected) {
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class HtmlPrint {
        @InjectMocks
        private ru.clevertec.cheque.printing.decorator.html.HeaderDecorator decorator;
        @Mock
        private ru.clevertec.cheque.printing.decorator.html.PrinterDecorator printer;

        @BeforeAll
        static void init() {
            cashReceipt = CashReceiptProvider.getCashReceipts().get(0);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/html/header_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnExpected(String expected) {
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            expected = expected.replaceAll("\r\n", "");
            assertThat(actual).isEqualTo(expected);
        }
    }

}