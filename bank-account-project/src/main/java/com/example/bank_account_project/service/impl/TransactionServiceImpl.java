package com.example.bank_account_project.service.impl;

import com.example.bank_account_project.dto.InitializeTransactionUserDto;
import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.dto.TransactionPageDto;
import com.example.bank_account_project.entity.Transaction;
import com.example.bank_account_project.mappers.TransactionMapper;
import com.example.bank_account_project.repository.TransactionRepository;
import com.example.bank_account_project.repository.TransactionRepositoryClass;
import com.example.bank_account_project.service.TransactionService;
import com.example.bank_account_project.service.UserService;
import com.example.bank_account_project.utils.DateUtils;
import com.example.bank_account_project.utils.validable.Status;
import com.example.bank_account_project.utils.validable.StatusHelper;
import com.example.bank_account_project.utils.validable.Validable;
import com.example.bank_account_project.utils.validable.input.TransactionInput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionsRepository;
    private final TransactionMapper transactionMapper;
    private final UserService userService;
    private final DateUtils dateUtils;
    private final TransactionRepositoryClass transactionRepositoryClass;
    private final TransactionInput transactionInput;
    private final StatusHelper statusHelper;

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public Validable<TransactionDto> createTransactions(TransactionDto transactionsDto) {
        Validable<TransactionDto> transactionDtoValidable = new Validable<>();
        List<Status> statusList = transactionInput.TransactionInput(transactionsDto);
        for (Status status : statusList){
            statusHelper.addStatus(transactionDtoValidable, status);
        }
        if(statusList.isEmpty()) {
            Transaction transaction = transactionsRepository.save(transactionMapper.transactionDtoToTransactions(transactionsDto));
            userService.updateAccountBalance(transactionsDto.getTransactionsUserId(), transactionsDto.getTransactionsAmount());
            transactionDtoValidable.setObject(transactionMapper.transactionToTransactionsDto(transaction));
            logger.info("Transaction created successfully with id {}", transaction.getTransactionsId());
        }else {
            logger.warn("Transaction creation failed due to validation errors");
        }
        return transactionDtoValidable;
    }

    @Override
    public Optional<TransactionDto> getTransactionsById(Long id) {
        Optional<TransactionDto> optionalTransactionDto = transactionsRepository.findById(id)
                .map(transactionMapper::transactionToTransactionsDto);
        if (optionalTransactionDto.isPresent()) {
            logger.info("Transaction retrieved successfully with id {}", id);
        } else {
            logger.warn("Transaction not found with id {}", id);
        }
        return optionalTransactionDto;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactionsList =  transactionsRepository.findAll();
        List<TransactionDto> transactionDtoList = transactionsList.stream()
                .map(transactionMapper::transactionToTransactionsDto)
                .collect(Collectors.toList());
        logger.info("Retrieved {} transactions", transactionDtoList.size());
        return transactionDtoList;
    }

    @Override
    public InitializeTransactionUserDto getAllTransactionsByTransactionUserId(Long transactionsUserId) {
        InitializeTransactionUserDto initializeTransactionUserDto = new InitializeTransactionUserDto();
        List<Transaction> transactionsList =  transactionsRepository.findByTransactionsUserId(transactionsUserId);
        initializeTransactionUserDto.setTransactionDtoList(transactionsList.stream()
                .map(transactionMapper::transactionToTransactionsDto)
                .collect(Collectors.toList()));
        // Mapping all transaction's amount
        initializeTransactionUserDto.setTransactionAmountList(transactionsList.stream()
                .map(Transaction::getTransactionsAmount)
                .collect(Collectors.toList()));

        // Mapping all transactions creationDate
        initializeTransactionUserDto.setTransactionCreationList(transactionsList.stream()
                .map(transaction -> dateUtils.convertDateTimeToString(transaction.getCreationDate()))
                .collect(Collectors.toList()));
        logger.info("Retrieved {} transactions for user with id {}", transactionsList.size(), transactionsUserId);
        return initializeTransactionUserDto;
    }

    @Override
    public void deleteTransactions(final Long transactionId) {
        Transaction transaction =  transactionsRepository.getReferenceById(transactionId);
        transactionsRepository.deleteById(transaction.getTransactionsId());
        Double invertedAmount = transaction.getTransactionsAmount() * -1;
        userService.updateAccountBalance(transaction.getUser().getUserId(), invertedAmount);
        logger.info("Transaction with id {} deleted successfully", transactionId);
    }

    @Override
    public List<TransactionPageDto> searchTransactionPage(final String search) {
        List<String> searchList = Arrays.asList(search.split("\\s+"));
        List<Object[]> queryResult = transactionRepositoryClass.getCriteriaSearch(searchList);
        List<TransactionPageDto> transactionPageDtoList = new ArrayList<>();
        for (Object[] row : queryResult) {
            TransactionPageDto transactionPageDto = new TransactionPageDto();
            transactionPageDto.setTransactionsId((Long) row[0]);
            transactionPageDto.setTransactionsAmount((Double) row[1]);
            transactionPageDto.setTransactionsMessage((String) row[2]);
            transactionPageDto.setCreationDate(dateUtils.convertDateToString((Date) row[3]));
            transactionPageDto.setUserId((Long) row[4]);
            transactionPageDto.setUserIdentity((String) row[5] + ' ' + row[6]);
            transactionPageDto.setUserEmail((String) row[7]);
            transactionPageDto.setUserAccountBalance((Double) row[8]);
            transactionPageDtoList.add(transactionPageDto);
        }
        logger.info("Found {} transactions matching the search criteria '{}'", transactionPageDtoList.size(), search);
        return transactionPageDtoList;
    }
}
