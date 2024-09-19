package com.example.bank_account_project.utils.contrainte.exception;


import com.example.bank_account_project.utils.EnumUtils.Enum.TAILLE_FORM;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeErreurMetier {

    @Getter
    @AllArgsConstructor
    public enum TransactionDtoErrors {
        MESSAGE_TOO_LONG("Message field is too long"),
        MESSAGE_MANDATORY("Transaction message field is mandatory."),
        TRANSACTION_AMOUNT_OBLIGATOIRE("Transaction amount is mandatory");

        private final String codeException;
    }


    @Getter
    @AllArgsConstructor
    public enum UserDtoErrors {
        NAME_MANDATORY("Name field is mandatory."),
        FIRST_NAME_MANDATORY("First name field is mandatory."),
        EMAIL_MANDATORY("Email field is mandatory."),
        TELEPHONE_INVALIDE("The entered phone number is invalid."),
        ADDRESS_MANDATORY("Address field is mandatory."),
        ADDRESS_TOO_LONG("Address field is too long"),
        JOB_TITLE_TOO_LONG("Job field is too long"),
        TELEPHONE_TOO_LONG("Telephone field is too long"),
        EMAIL_TOO_LONG("Email field is too long"),
        FIRST_NAME_TOO_LONG("First name field is too long"),
        NAME_TOO_LONG("Name field is too long"),
        JOB_TITLE_MANDATORY("Job title field is mandatory."),
        DATE_OF_BIRTH_MANDATORY("Date of birth field is mandatory."),
        EMAIL_INVALIDE("The entered email is invalid."),
        DATE_OF_BIRTH_INVALIDE("The entered date of birth is invalid.");

        private final String codeException;
    }


}
