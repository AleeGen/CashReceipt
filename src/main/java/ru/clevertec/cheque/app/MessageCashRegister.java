package ru.clevertec.cheque.app;

public class MessageCashRegister {
    private MessageCashRegister() {
    }

    public static final String BEGIN = "The cash register has started. Enter a first request";
    public static final String ENTER_REQUEST = "Enter a next request";
    public static final String INVALID_SHOPPING_LIST = "%s: incorrect purchase request";
    public static final String ERROR_RECORD_CASH_RECEIPT = "%s: cash receipt recording error";
    public static final String SUCCESSFULLY = "The cash receipt was successfully recorded";
    public static final String END = "The cash register has finished";
    public static final String NOT_EXIST_PRODUCT = "There is no product with id: %s";
    public static final String NOT_EXIST_DISCOUNT_CARD = "There is no discount card with number: %s";
}
