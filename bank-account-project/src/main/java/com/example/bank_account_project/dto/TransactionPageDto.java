package com.example.bank_account_project.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionPageDto {

    private Long transactionsId;

    private Double transactionsAmount;

    private String transactionsMessage;

    private String creationDate;

    private Long userId;

    private String userIdentity;

    private String userEmail;

    private Double userAccountBalance;

}
