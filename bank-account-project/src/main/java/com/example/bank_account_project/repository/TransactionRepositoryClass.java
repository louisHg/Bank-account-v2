package com.example.bank_account_project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryClass {

    @PersistenceContext
    private EntityManager entityManager;

    public List getCriteriaSearch(List<String> search) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.transactions_id as transactionsId, " +
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
                "WHERE 1=1 \n");
        for (String criteria : search) {
            sb.append("AND (LOWER(t.transactions_recherche) LIKE '%' || lower(unaccent(trim('"+ criteria +"'))) || '%') ");
        }
        sb.append("ORDER BY t.creation_date DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        return query.getResultList();
    }

}
