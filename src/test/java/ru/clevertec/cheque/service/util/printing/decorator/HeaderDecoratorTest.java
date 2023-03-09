package ru.clevertec.cheque.service.util.printing.decorator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.service.util.printing.decorator.HeaderDecorator;
import ru.clevertec.cheque.service.util.printing.decorator.PrinterDecorator;
import ru.clevertec.cheque.provider.CashReceiptProvider;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HeaderDecoratorTest {
    private static CashReceipt cashReceipt;
    @InjectMocks
    private HeaderDecorator decorator;
    @Mock
    private PrinterDecorator printer;

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