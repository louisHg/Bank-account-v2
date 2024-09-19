package com.example.bank_account_project.utils.validable.input;

import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.utils.contrainte.saisie.TransactionConstraint;
import com.example.bank_account_project.utils.validable.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TransactionInput {

    private final TransactionConstraint transactionConstraint;

    public List<Status> TransactionInput(final TransactionDto transactionDto) {
        List<Status> statusList = new ArrayList<>();
        statusList = this.transactionConstraint.validateTransactionsAmount(statusList, transactionDto.getTransactionsAmount());
        statusList = this.transactionConstraint.validateTransactionsMessage(statusList, transactionDto.getTransactionsMessage());
        return statusList;
    }

}
