package com.example.bank_account_project.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeTransactionUserDto {

    private List<TransactionDto> transactionDtoList;

    private List<Double> transactionAmountList;

    private List<String> transactionCreationList;

}
