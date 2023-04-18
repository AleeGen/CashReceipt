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
import ru.clevertec.cheque.entity.CashReceiptProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BodyDecoratorTest {
    private static List<CashReceipt> cashReceipts;
    @InjectMocks
    private BodyDecorator decorator;
    @Mock
    private PrinterDecorator printer;

    @BeforeAll
    static void init() {
        cashReceipts = CashReceiptProvider.getCashReceipts();
    }

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