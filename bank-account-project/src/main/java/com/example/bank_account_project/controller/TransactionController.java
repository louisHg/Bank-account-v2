package com.example.bank_account_project.controller;

import com.example.bank_account_project.dto.InitializeTransactionUserDto;
import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.dto.TransactionPageDto;
import com.example.bank_account_project.service.TransactionService;
import com.example.bank_account_project.utils.validable.Validable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<Validable<TransactionDto>> createTransaction(@RequestBody TransactionDto transactionsDto) {
        Validable<TransactionDto> createdTransaction = transactionService.createTransactions(transactionsDto);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransactions(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<InitializeTransactionUserDto> getTransactionsByUserId(@PathVariable Long userId) {
        InitializeTransactionUserDto initializeTransactionUserDto = transactionService.getAllTransactionsByTransactionUserId(userId);
        return ResponseEntity.ok(initializeTransactionUserDto);
    }

    @PostMapping("/transaction-page")
    public ResponseEntity<List<TransactionPageDto>> searchTransactionPage(@RequestParam("search") String search) {
        List<TransactionPageDto> transactionPageDtos = transactionService.searchTransactionPage(search);
        return ResponseEntity.ok(transactionPageDtos);
    }

}
