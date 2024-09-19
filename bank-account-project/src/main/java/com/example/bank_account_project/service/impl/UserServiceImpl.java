package com.example.bank_account_project.service.impl;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.entity.User;
import com.example.bank_account_project.mappers.UserMapper;
import com.example.bank_account_project.repository.TransactionRepository;
import com.example.bank_account_project.repository.UserRepository;
import com.example.bank_account_project.repository.UserRepositoryClass;
import com.example.bank_account_project.service.UserService;
import com.example.bank_account_project.utils.DateUtils;
import com.example.bank_account_project.utils.validable.Status;
import com.example.bank_account_project.utils.validable.StatusHelper;
import com.example.bank_account_project.utils.validable.Validable;
import com.example.bank_account_project.utils.validable.input.UserInput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;
    private final UserMapper usersMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepositoryClass userRepositoryClass;
    private final DateUtils dateUtils;
    private final UserInput userInput;
    private final StatusHelper statusHelper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Validable<UserDto> createUser(UserDto userDto) {
        Validable<UserDto> userDtoValidable = new Validable<>();
        List<Status> statusList = userInput.userInput(userDto);
        for (Status status : statusList){
            statusHelper.addStatus(userDtoValidable, status);
        }
        if(statusList.isEmpty()) {
            User users = usersRepository.save(usersMapper.userDtoToUser(userDto));
            userDtoValidable.setObject(usersMapper.userToUserDto(users));
            logger.info("User created successfully with id {}", users.getUserId());
        } else {
            logger.warn("User creation failed due to validation errors");
        }
        return userDtoValidable;
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        Optional<UserDto> optionalUserDto = usersRepository.findById(id)
                .map(usersMapper::userToUserDto);
        if (optionalUserDto.isPresent()) {
            logger.info("User retrieved successfully with id {}", id);
        } else {
            logger.warn("User not found with id {}", id);
        }
        return optionalUserDto;
    }


    @Override
    public List<UserDto> searchUsers(String search) {
        logger.info("Searching users with search string '{}'", search);
        List<String> searchList = Arrays.asList(search.split("\\s+"));
        List<Object[]> queryResult = userRepositoryClass.getCriteriaSearch(searchList);
        List<UserDto> userDtoList = new ArrayList<>();
        for (Object[] row : queryResult) {
            UserDto userDto = new UserDto();
            userDto.setUserId((Long) row[0]);
            userDto.setUserName((String) row[1]);
            userDto.setUserFirstName((String) row[2]);
            userDto.setUserEmail((String) row[3]);
            userDto.setUserPhoneNumber((String) row[4]);
            userDto.setUserAddress((String) row[5]);
            userDto.setUserJobTitle((String) row[6]);
            userDto.setUserDateOfBirth(dateUtils.convertDateToString((Date) row[7]));
            userDto.setUserAccountBalance((Double) row[8]);
            userDto.setUserIdentity(userDto.getUserFirstName() + " " + userDto.getUserName());
            userDtoList.add(userDto);
        }
        logger.info("Found {} users matching the search criteria '{}'", userDtoList.size(), search);
        return userDtoList;
    }

    @Override
    public Validable<UserDto> updateUser(Long id, UserDto userDto) {
        Validable<UserDto> validableUserDto = new Validable<>();
        List<Status> statusList = userInput.userInput(userDto);
        for (Status status : statusList) {
            statusHelper.addStatus(validableUserDto, status);
        }
        if (statusList.isEmpty()) {
            usersRepository.findById(id).map(user -> {
                User updatedUser = usersMapper.updateUserFromDto(userDto, user);
                usersRepository.save(updatedUser); // Save the updated user
                UserDto updatedUserDto = usersMapper.userToUserDto(updatedUser);
                validableUserDto.setObject(updatedUserDto);
                logger.info("User with id {} updated successfully", id);
                return updatedUserDto;
            }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        }
        return validableUserDto;
    }

    @Override
    public void deleteUser(final Long userId) {
        transactionRepository.deleteByTransactionsUserId(userId);
        usersRepository.deleteById(userId);
        logger.info("User with id {} deleted successfully", userId);
    }

    @Override
    public User updateAccountBalance(Long userId, Double transactionAmount) {
        return usersRepository.findById(userId).map(user -> {
            if (user.getUserAccountBalance() != null) {
                user.setUserAccountBalance(user.getUserAccountBalance() + transactionAmount);
            }
            else {
                user.setUserAccountBalance(transactionAmount);
            }
            User updatedUser = usersRepository.save(user);
            logger.info("Account balance updated for user with id {}. New balance: {}", userId, updatedUser.getUserAccountBalance());
            return updatedUser;
        }).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }
}
