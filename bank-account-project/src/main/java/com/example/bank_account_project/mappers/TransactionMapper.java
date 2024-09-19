package com.example.bank_account_project.mappers;

import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.entity.Transaction;

public interface TransactionMapper {

    Transaction transactionDtoToTransactions(TransactionDto transactionDto);

    TransactionDto transactionToTransactionsDto(Transaction transaction);

}
