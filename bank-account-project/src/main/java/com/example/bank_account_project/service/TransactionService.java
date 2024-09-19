package com.example.bank_account_project.service;

import com.example.bank_account_project.dto.InitializeTransactionUserDto;
import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.dto.TransactionPageDto;
import com.example.bank_account_project.utils.validable.Validable;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Validable<TransactionDto> createTransactions(final TransactionDto transactionsDto);

    Optional<TransactionDto> getTransactionsById(final Long id);

    List<TransactionDto> getAllTransactions();

    InitializeTransactionUserDto getAllTransactionsByTransactionUserId(final Long transactionsUserId);

    void deleteTransactions(final Long transactionId);

    List<TransactionPageDto> searchTransactionPage(final String search);

}
