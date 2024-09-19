package com.example.bank_account_project.utils.contrainte.saisie;

import com.example.bank_account_project.utils.EnumUtils.Enum.TAILLE_FORM;
import com.example.bank_account_project.utils.contrainte.exception.CodeErreurMetier.UserDtoErrors;
import com.example.bank_account_project.utils.validable.Status;
import com.example.bank_account_project.utils.validable.StatusType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserConstraint extends Constraint {

    public List<Status> validateName(List<Status> statusList, String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.NAME_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (isTooLong(name, TAILLE_FORM.NORMAL.getId())) {
            statusList.add(new Status(UserDtoErrors.NAME_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }

    public List<Status> validateFirstName(List<Status> statusList, String firstName) {
        if (Objects.isNull(firstName) || firstName.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.FIRST_NAME_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (isTooLong(firstName, TAILLE_FORM.NORMAL.getId())) {
            statusList.add(new Status(UserDtoErrors.FIRST_NAME_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }

    public List<Status> validateEmail(List<Status> statusList, String email) {
        if (Objects.isNull(email) || email.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.EMAIL_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (Boolean.FALSE.equals( patternMatches(email, EMAIL_PATTERN) ) ) {
            statusList.add(new Status(UserDtoErrors.EMAIL_INVALIDE.getCodeException(), StatusType.FORM_ERREUR));
        } else if (isTooLong(email, TAILLE_FORM.NORMAL.getId())) {
            statusList.add(new Status(UserDtoErrors.EMAIL_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }

    public List<Status> validatePhoneNumber(List<Status> statusList, String phoneNumber) {
        if (Objects.isNull(phoneNumber) || phoneNumber.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.TELEPHONE_INVALIDE.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (Boolean.FALSE.equals( patternMatches(phoneNumber, TELEPHONE_PATTERN) ) ) {
            statusList.add(new Status(UserDtoErrors.TELEPHONE_INVALIDE.getCodeException(), StatusType.FORM_ERREUR));
        } else if (isTooLong(phoneNumber, TAILLE_FORM.NORMAL.getId())) {
            statusList.add(new Status(UserDtoErrors.TELEPHONE_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }

    public List<Status> validateAddress(List<Status> statusList, String address) {
        if (Objects.isNull(address) || address.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.ADDRESS_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (isTooLong(address, TAILLE_FORM.LONG.getId())) {
            statusList.add(new Status(UserDtoErrors.ADDRESS_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }

    public List<Status> validateJobTitle(List<Status> statusList, String jobTitle) {
        if (Objects.isNull(jobTitle) || jobTitle.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.JOB_TITLE_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (isTooLong(jobTitle, TAILLE_FORM.NORMAL.getId())) {
            statusList.add(new Status(UserDtoErrors.JOB_TITLE_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }

    public List<Status> validateDateOfBirth(List<Status> statusList, String dateOfBirth) {
        if (Objects.isNull(dateOfBirth) || dateOfBirth.isEmpty()) {
            statusList.add(new Status(UserDtoErrors.DATE_OF_BIRTH_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else {
            try {
                LocalDate.parse(dateOfBirth); // Validate date format
            } catch (DateTimeParseException e) {
                statusList.add(new Status(UserDtoErrors.DATE_OF_BIRTH_INVALIDE.getCodeException(), StatusType.FORM_ERREUR));
            }
        }
        return statusList;
    }

}
