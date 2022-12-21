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
import ru.clevertec.cheque.printing.decorator.console.BodyDecorator;
import ru.clevertec.cheque.printing.decorator.console.PrinterDecorator;
import ru.clevertec.cheque.provider.CashReceiptProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BodyDecoratorTest {
    private static List<CashReceipt> cashReceipts;

    @BeforeAll
    static void init() {
        cashReceipts = CashReceiptProvider.getCashReceipts();
    }

    @Nested
    class ConsolePrint {
        @InjectMocks
        private BodyDecorator decorator;
        @Mock
        private PrinterDecorator printer;

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/console/body_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnConsoleExpected(String expected, int indexCashReceipt) {
            CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class HtmlPrint {
        @InjectMocks
        private ru.clevertec.cheque.printing.decorator.html.BodyDecorator decorator;
        @Mock
        private ru.clevertec.cheque.printing.decorator.html.PrinterDecorator printer;

        @ParameterizedTest
        @CsvFileSource(resources = "/decorator/html/body_decorator.csv", lineSeparator = "|", delimiterString = "->",
                ignoreLeadingAndTrailingWhitespace = false)
        void checkPrintShouldReturnHtmlExpected(String expected, int indexCashReceipt) {
            CashReceipt cashReceipt = cashReceipts.get(indexCashReceipt);
            Mockito.doReturn("").when(printer).print(cashReceipt);
            String actual = decorator.print(cashReceipt);
            expected = expected.replaceAll("\r\n", "");
            assertThat(actual).isEqualTo(expected);
        }
    }

}