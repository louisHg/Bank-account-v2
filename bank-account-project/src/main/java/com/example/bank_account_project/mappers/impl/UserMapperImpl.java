package com.example.bank_account_project.mappers.impl;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.entity.User;
import com.example.bank_account_project.mappers.UserMapper;
import com.example.bank_account_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final ModelMapper modelMapper;

    private final DateUtils dateUtils;

    public UserDto userToUserDto(User user) {
        UserDto usersDto = modelMapper.map(user, UserDto.class);
        usersDto.setUserDateOfBirth(dateUtils.convertDateToString(user.getUserDateOfBirth()));
        return usersDto;
    }

    public User userDtoToUser(UserDto userDto) {
        User users = modelMapper.map(userDto, User.class);
        users.setUserDateOfBirth(dateUtils.convertStringToDate(userDto.getUserDateOfBirth()));
        return users;
    }

    public User updateUserFromDto(UserDto userDto, User user) {
        modelMapper.map(userDto, user);
        return user;
    }

}
