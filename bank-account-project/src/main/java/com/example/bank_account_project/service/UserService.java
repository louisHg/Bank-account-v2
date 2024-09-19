package com.example.bank_account_project.service;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.entity.User;
import com.example.bank_account_project.utils.validable.Validable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

    Validable<UserDto> createUser(final UserDto userDto);

    Optional<UserDto> getUserById(final Long id);

    List<UserDto> searchUsers(final String search);

    Validable<UserDto> updateUser(final Long id, final UserDto userDto);

    void deleteUser(final Long id);

    User updateAccountBalance(final Long userId, final Double transactionAmount);
}
