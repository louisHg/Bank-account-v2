package com.example.bank_account_project.utils.validable.input;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.utils.contrainte.saisie.UserConstraint;
import com.example.bank_account_project.utils.validable.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserInput {

    private final UserConstraint userContrainte;

    public List<Status> userInput(final UserDto user) {
        List<Status> statusList = new ArrayList<>();
        statusList = this.userContrainte.validateName(statusList, user.getUserName());
        statusList = this.userContrainte.validateFirstName(statusList, user.getUserFirstName());
        statusList = this.userContrainte.validateEmail(statusList, user.getUserEmail());
        statusList = this.userContrainte.validatePhoneNumber(statusList, user.getUserPhoneNumber());
        statusList = this.userContrainte.validateAddress(statusList, user.getUserAddress());
        statusList = this.userContrainte.validateJobTitle(statusList, user.getUserJobTitle());
        statusList = this.userContrainte.validateDateOfBirth(statusList, user.getUserDateOfBirth());

        return statusList;
    }
}
