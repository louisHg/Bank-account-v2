package com.example.bank_account_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "transactions")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionsId;

    private Double transactionsAmount;

    private String transactionsMessage;

    @ManyToOne
    @JoinColumn(name = "transactions_user_id", referencedColumnName = "userId")
    private User user;

    private String transactionsRecherche;

}
