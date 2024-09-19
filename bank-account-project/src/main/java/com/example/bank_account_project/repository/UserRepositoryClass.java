package com.example.bank_account_project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryClass {

    @PersistenceContext
    private EntityManager entityManager;

    public List getCriteriaSearch(List<String> search) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.user_id as userId, " +
                "u.user_name as userName, " +
                "u.user_first_name as userFirstName, " +
                "u.user_email as userEmail, " +
                "u.user_phone_number as userPhoneNumber, " +
                "u.user_address as userAddress, " +
                "u.user_job_title as userJobTitle, " +
                "u.user_date_of_birth as userDateOfBirth, " +
                "u.user_account_balance as userAccountBalance " +
                "FROM users u " +
                "WHERE 1=1 \n");
        for (String criteria : search) {
            sb.append("AND (LOWER(u.user_recherche) LIKE '%' || lower(unaccent(trim('"+ criteria +"'))) || '%') ");
        }
        sb.append("ORDER BY u.creation_date DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        return query.getResultList();
    }

}
