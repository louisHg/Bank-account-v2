package com.example.bank_account_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userFirstName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    private String userPhoneNumber;

    private String userAddress;

    @Column(nullable = false)
    private String userJobTitle;

    private Date userDateOfBirth;

    private Double userAccountBalance;

    private String userRecherche;

}
