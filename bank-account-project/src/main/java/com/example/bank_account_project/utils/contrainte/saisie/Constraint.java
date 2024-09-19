package com.example.bank_account_project.utils.contrainte.saisie;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class Constraint {
    protected static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    protected static final String TELEPHONE_PATTERN = "(0|\\+33|0033)[1-9][0-9]{8}";
    protected static final String URL_PATTERN = "https://(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,}){1,}(\\/\\S*)?";

    protected static boolean patternMatches(String toVerify, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(toVerify).matches();
    }

    protected static boolean isNegatif(Integer toVerify) {
        return toVerify < 0;
    }

    protected static boolean isTooLong(String toVerify, Integer sixeMax) {
        return toVerify.length() > sixeMax;
    }

}
