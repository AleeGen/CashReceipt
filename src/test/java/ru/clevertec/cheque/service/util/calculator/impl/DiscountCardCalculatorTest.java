package ru.clevertec.cheque.service.util.calculator.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.entity.CashReceiptProvider;
import ru.clevertec.cheque.service.util.calculator.CashReceiptCalculator;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountCardCalculatorTest {
    private static CashReceiptCalculator calculator;

    private static Stream<Arguments> args() {
        List<CashReceipt> cashReceipts = CashReceiptProvider.getCashReceipts();
        return Stream.of(
                Arguments.of(cashReceipts.get(0), 47.1295),
                Arguments.of(cashReceipts.get(1), 73.9));
    }

    @BeforeAll
    static void init() {
        calculator = new DiscountCardCalculator();
    }

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource(value = "args")
    void checkCalculateShouldReturnExpected(CashReceipt cashReceipt, double totalCost) {
        double actual = calculator.calculate(cashReceipt).getTotalCost();
        assertThat(actual).isEqualTo(totalCost);
    }

    @ParameterizedTest
    @NullSource
    void checkValidateShouldThrowNullPointerException(CashReceipt cashReceipt) {
        assertThrows(NullPointerException.class, () -> calculator.calculate(cashReceipt));
    }

}