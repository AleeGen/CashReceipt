package ru.clevertec.cheque.util.validation.impl;

import ru.clevertec.cheque.util.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderValidator implements Validator {
    private static final String REGEX = "^\\d+-\\d+(\\s+\\d+-\\d+)*(\\s+card-\\d{4}\s*)?$";

    public OrderValidator() {
    }

    @Override
    public boolean validate(String data) {
        Matcher matcher = Pattern.compile(REGEX).matcher(data);
        return matcher.matches();
    }
}
