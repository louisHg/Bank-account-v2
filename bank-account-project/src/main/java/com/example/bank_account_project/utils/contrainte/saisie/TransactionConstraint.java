package com.example.bank_account_project.utils.contrainte.saisie;

import com.example.bank_account_project.utils.EnumUtils.Enum.TAILLE_FORM;
import com.example.bank_account_project.utils.contrainte.exception.CodeErreurMetier.TransactionDtoErrors;
import com.example.bank_account_project.utils.validable.Status;
import com.example.bank_account_project.utils.validable.StatusType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class TransactionConstraint extends Constraint {

    public List<Status> validateTransactionsAmount(List<Status> statusList, Double transactionsAmount) {
        if (Objects.isNull(transactionsAmount) || transactionsAmount.equals(0.0)) {
            statusList.add(new Status(TransactionDtoErrors.TRANSACTION_AMOUNT_OBLIGATOIRE.getCodeException(), StatusType.CHAMP_MANDATORY));
        }
        return statusList;
    }

    public List<Status> validateTransactionsMessage(List<Status> statusList, String transactionsMessage) {
        if (Objects.isNull(transactionsMessage) || transactionsMessage.isEmpty()) {
            statusList.add(new Status(TransactionDtoErrors.MESSAGE_MANDATORY.getCodeException(), StatusType.CHAMP_MANDATORY));
        } else if (isTooLong(transactionsMessage, TAILLE_FORM.NORMAL.getId())) {
            statusList.add(new Status(TransactionDtoErrors.MESSAGE_TOO_LONG.getCodeException(), StatusType.FORM_ERREUR));
        }
        return statusList;
    }
}
