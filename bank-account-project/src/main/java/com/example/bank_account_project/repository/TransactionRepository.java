package com.example.bank_account_project.repository;

import com.example.bank_account_project.dto.TransactionPageDto;
import com.example.bank_account_project.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions WHERE transactions_user_id = :transactionsUserId ORDER BY creation_date", nativeQuery = true)
    List<Transaction> findByTransactionsUserId(@Param("transactionsUserId") Long transactionsUserId);

    @Modifying
    @Query(value = "DELETE FROM transactions WHERE transactions_user_id = :userId", nativeQuery = true)
    void deleteByTransactionsUserId(@Param("userId") Long userId);

    @Query(value = "SELECT t.transactions_id as transactionsId, " +
            "t.transactions_amount as transactionsAmount, " +
            "t.transactions_message as transactionsMessage, " +
            "t.creation_date as creationDate, " +
            "u.user_id as userId, " +
            "u.user_name as userName, " +
            "u.user_first_name as userFirstName, " +
            "u.user_email as userEmail, " +
            "u.user_account_balance as userAccountBalance " +
            "FROM transactions t " +
            "INNER JOIN users u ON t.transactions_user_id = u.user_id " +
            "ORDER BY t.creation_date", nativeQuery = true)
    List<TransactionPageDto> findTransactionsWithUserDetails();

}
