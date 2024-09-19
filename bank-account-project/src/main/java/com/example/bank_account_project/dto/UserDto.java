package com.example.bank_account_project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    private String userName;

    private String userFirstName;

    private String userIdentity;

    private String userEmail;

    private String userPhoneNumber;

    private String userAddress;

    private String userJobTitle;

    private String userDateOfBirth;

    private Double userAccountBalance;

}
