package ru.clevertec.cheque.util.parsing.impl;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.Position;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.exception.OrderParserException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import ru.clevertec.cheque.provider.CashReceiptProvider;
import ru.clevertec.cheque.util.parsing.OrderParser;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleOrderParserTest {
    private static OrderParser parser;

    @BeforeAll
    static void init() {
        parser = new ConsoleOrderParser();
    }

    @Nested
    @DisplayName("checkParseShoppingList")
    class ParseShoppingListTest {
        private static Map<Integer, Product> products;

        private static Stream<Arguments> args() {
            List<Position> positions = CashReceiptProvider.getPosition();
            return Stream.of(
                    Arguments.of("1-7 2-8", positions.subList(0, 2)),
                    Arguments.of("3-4 4-6 5-10", positions.subList(2, 5)),
                    Arguments.of("6-3 7-1 8-2 9-9 10-5", positions.subList(5, 10))
            );
        }

        @BeforeAll
        static void init() {
            products = CashReceiptProvider.getProducts();
        }

        @ParameterizedTest
        @MethodSource(value = "args")
        void shouldReturnExistProducts(String data, List<Position> positions) throws OrderParserException {
            List<Position> actual = parser.parseShoppingList(data, products);
            assertThat(actual).isEqualTo(positions);
        }

        @Test
        void shouldThrowOrderParserException() {
            String nonexistentProduct = "99999-1";
            assertThrows(OrderParserException.class, () -> parser.parseShoppingList(nonexistentProduct, products));
        }
    }

    @Nested
    @DisplayName("checkParseDiscountCard")
    class ParseDiscountCardTest {
        private static Map<Short, DiscountCard> cards;

        private static Stream<Arguments> args() {
            Map<Short, DiscountCard> cards = CashReceiptProvider.getCards();
            return Stream.of(
                    Arguments.of("1-1 card-1111", cards.get((short) 1111)),
                    Arguments.of("2-4 5-2 card-2222", cards.get((short) 2222)),
                    Arguments.of("card-3333", cards.get((short) 3333)),
                    Arguments.of("11-43 card-4444", cards.get((short) 4444)),
                    Arguments.of("8567-57321 card-5555", cards.get((short) 5555))
            );
        }

        @BeforeAll
        static void init() {
            cards = CashReceiptProvider.getCards();
        }

        @ParameterizedTest
        @MethodSource(value = "args")
        void shouldReturnExistCards(String data, DiscountCard card) throws OrderParserException {
            Optional<DiscountCard> actual = parser.parseDiscountCard(data, cards);
            assertThat(actual.get()).isEqualTo(card);
        }

        @Test
        void shouldReturnEmptyCard() throws OrderParserException {
            String withoutCard = "1-3 5-4 7-12";
            Optional<DiscountCard> expected = Optional.empty();
            Optional<DiscountCard> actual = parser.parseDiscountCard(withoutCard, cards);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldThrowOrderParserException() {
            String nonexistentCard = "1-3 card-9999";
            assertThrows(OrderParserException.class, () -> parser.parseDiscountCard(nonexistentCard, cards));
        }
    }

    @Nested
    @DisplayName("checkParseRequestParameters")
    class ParseRequestParametersTest {

        private static Stream<Arguments> args() {
            return Stream.of(
                    Arguments.of(new String[]{"1", "1", "5", "3", "1", "5"}, "1111", "1-3 3-1 5-2 card-1111"),
                    Arguments.of(new String[]{"9", "2", "3", "8", "9", "4", "2"}, "2222", "2-2 3-1 4-1 8-1 9-2 card-2222"),
                    Arguments.of(new String[]{"8", "3", "4", "7", "4", "4", "1"}, null, "1-1 3-1 4-3 7-1 8-1"),
                    Arguments.of(new String[]{"10", "10", "10",}, "5555", "10-3 card-5555"),
                    Arguments.of(new String[]{"5", "5"}, null, "5-2")
            );
        }

        @ParameterizedTest
        @MethodSource(value = "args")
        void shouldReturnExpected(String[] idItems, String card, String expected) {
            String actual = parser.parseRequestParameters(idItems, card);
            assertThat(actual).isEqualTo(expected);
        }

        @ParameterizedTest
        @NullSource
        void shouldNotThrowExceptions(String card) {
            String[] idItems = new String[]{"1", "1"};
            assertDoesNotThrow(() -> parser.parseRequestParameters(idItems, card));
        }
    }

}