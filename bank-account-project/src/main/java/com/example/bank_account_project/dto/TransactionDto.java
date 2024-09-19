package com.example.bank_account_project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    private Long transactionsId;

    private Double transactionsAmount;

    private String transactionsMessage;

    private Long transactionsUserId;

    private String creationDate;

    private Long userId;

    private String userName;

    private String userEmail;

}
