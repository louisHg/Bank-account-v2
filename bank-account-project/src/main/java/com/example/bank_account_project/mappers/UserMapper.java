package com.example.bank_account_project.mappers;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.entity.User;

public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    User updateUserFromDto(UserDto userDto, User user);
}
