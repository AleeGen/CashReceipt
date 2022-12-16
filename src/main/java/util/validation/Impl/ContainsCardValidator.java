package util.validation.Impl;

import util.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainsCardValidator implements Validator {

    private static final String REGEX = "card-\\d{4}?";

    @Override
    public boolean validate(String data) {
        Matcher matcher = Pattern.compile(REGEX).matcher(data);
        return matcher.find();
    }
}
