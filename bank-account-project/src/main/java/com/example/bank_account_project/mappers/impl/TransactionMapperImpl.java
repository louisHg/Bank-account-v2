package com.example.bank_account_project.mappers.impl;

import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.entity.Transaction;
import com.example.bank_account_project.entity.User;
import com.example.bank_account_project.mappers.TransactionMapper;
import com.example.bank_account_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {

    private final ModelMapper modelMapper;

    private final DateUtils dateUtils;

    public Transaction transactionDtoToTransactions(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        User user = new User();
        user.setUserId(transactionDto.getTransactionsUserId());
        transaction.setUser(user);
        if(transactionDto.getCreationDate() != null) {
            transaction.setCreationDate(dateUtils.convertStringToDate(transactionDto.getCreationDate()));
        }
        return transaction;
    }

    public TransactionDto transactionToTransactionsDto(Transaction transaction) {
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        transactionDto.setTransactionsUserId(transaction.getUser().getUserId());
        transactionDto.setCreationDate(dateUtils.convertDateToString(transaction.getCreationDate()));
        return transactionDto;
    }

}
