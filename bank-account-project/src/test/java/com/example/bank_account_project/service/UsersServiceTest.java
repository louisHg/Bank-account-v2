package com.example.bank_account_project.service;

import com.example.bank_account_project.dto.UserDto;
import com.example.bank_account_project.entity.User;
import com.example.bank_account_project.mappers.UserMapper;
import com.example.bank_account_project.repository.TransactionRepository;
import com.example.bank_account_project.repository.UserRepository;
import com.example.bank_account_project.repository.UserRepositoryClass;
import com.example.bank_account_project.service.impl.UserServiceImpl;
import com.example.bank_account_project.utils.DateUtils;
import com.example.bank_account_project.utils.validable.Status;
import com.example.bank_account_project.utils.validable.StatusHelper;
import com.example.bank_account_project.utils.validable.Validable;
import com.example.bank_account_project.utils.validable.input.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UserRepository usersRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepositoryClass userRepositoryClass;

    @Mock
    private UserMapper usersMapper;

    @InjectMocks
    private UserServiceImpl usersServiceImpl;

    private User user;

    private UserDto userDto;

    @Mock
    private DateUtils dateUtils;

    @Mock
    private UserInput userInput;

    @Mock
    private StatusHelper statusHelper;


    @BeforeEach
    void init() {
        this.user = User.builder()
                .userId(1L)
                .userName("louis")
                .userFirstName("huyghe")
                .userEmail("louishuyghe6@gmail.com")
                .userPhoneNumber("06 12 13 14 15")
                .userAddress("6 Main Street, Lille 59000, FR")
                .userJobTitle("Software Developer")
                .build();

        this.userDto = UserDto.builder()
                .userId(1L)
                .userName("louis")
                .userFirstName("huyghe")
                .userEmail("louishuyghe6@gmail.com")
                .userPhoneNumber("06 12 13 14 15")
                .userAddress("6 Main Street, Lille 59000, FR")
                .userJobTitle("Software Developer")
                .build();
    }

    @Test
    void createUserTestSuccess() {
        // given
        UserDto userDto = new UserDto();
        List<Status> statusList = new ArrayList<>();
        when(userInput.userInput(userDto)).thenReturn(statusList);

        User user = new User();
        when(usersMapper.userDtoToUser(userDto)).thenReturn(user);
        when(usersRepository.save(any(User.class))).thenReturn(user);
        when(usersMapper.userToUserDto(user)).thenReturn(userDto);

        // when
        Validable<UserDto> createdUserValidable = usersServiceImpl.createUser(userDto);

        // then
        assertEquals(userDto, createdUserValidable.getObject(), "Returned UserDto should match input UserDto");
        verify(userInput, times(1)).userInput(userDto);
        verify(usersMapper, times(1)).userDtoToUser(userDto);
        verify(usersRepository, times(1)).save(user);
        verify(usersMapper, times(1)).userToUserDto(user);
    }

    @Test
    void createUserTestFailure() {
        // given
        when(this.usersMapper.userDtoToUser(this.userDto)).thenReturn(this.user);
        when(this.usersRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.usersServiceImpl.createUser(this.userDto));
        assertEquals("Database error", exception.getMessage());

        // then
        verify(this.usersMapper, times(1)).userDtoToUser(this.userDto);
        verify(this.usersRepository, times(1)).save(this.user);
    }

    @Test
    void getUserByIdTestSuccess() {
        // given
        when(this.usersRepository.findById(1L)).thenReturn(Optional.ofNullable(this.user));
        when(this.usersMapper.userToUserDto(this.user)).thenReturn(this.userDto);

        // when
        Optional<UserDto> userDtoToVerify = this.usersServiceImpl.getUserById(1L);

        // then
        assertEquals(Optional.of(this.userDto), userDtoToVerify);
        verify(this.usersRepository, times(1)).findById(1L);
        verify(this.usersMapper, times(1)).userToUserDto(this.user);
    }

    @Test
    void getUserByIdTestFailure() {
        // given
        when(this.usersRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        Optional<UserDto> userDtoToVerify = this.usersServiceImpl.getUserById(1L);

        // then
        assertEquals(Optional.empty(), userDtoToVerify);
        verify(this.usersRepository, times(1)).findById(1L);
        verify(this.usersMapper, never()).userToUserDto(any(User.class));
    }

    @Test
    void searchUsersTestSuccess() {
        // Given
        Object[] mockRow;
        List<Object[]> queryResult  = new ArrayList<>();
        mockRow = new Object[]{
                1L, "louis", "huyghe", "louishuyghe6@gmail.com", "06 12 13 14 15",
                "6 Main Street, Lille 59000, FR", "Software Developer", new Date(), 100.0
        };
        queryResult.add(mockRow);
        when(dateUtils.convertDateToString((Date) mockRow[7])).thenReturn("01-01-2000");
        String search = "louis huyghe";
        List<String> searchList = Arrays.asList(search.split("\\s+"));
        when(userRepositoryClass.getCriteriaSearch(searchList)).thenReturn(queryResult);

        // When
        List<UserDto> userDtoList = usersServiceImpl.searchUsers(search);

        // Then
        assertNotNull(userDtoList);
        assertEquals(1, userDtoList.size());

        UserDto userDto = userDtoList.get(0);
        assertEquals(1L, userDto.getUserId());
        assertEquals("louis", userDto.getUserName());
        assertEquals("huyghe", userDto.getUserFirstName());
        assertEquals("louishuyghe6@gmail.com", userDto.getUserEmail());
        assertEquals("06 12 13 14 15", userDto.getUserPhoneNumber());
        assertEquals("6 Main Street, Lille 59000, FR", userDto.getUserAddress());
        assertEquals("Software Developer", userDto.getUserJobTitle());
        assertEquals("01-01-2000", userDto.getUserDateOfBirth());
        assertEquals(100.0, userDto.getUserAccountBalance());
        assertEquals("huyghe louis", userDto.getUserIdentity());

        verify(userRepositoryClass, times(1)).getCriteriaSearch(searchList);
        verify(dateUtils, times(1)).convertDateToString((Date) mockRow[7]);
    }

    @Test
    void searchUsersTestNoResults() {
        // Given
        String search = "nonexistent user";
        List<String> searchList = Arrays.asList(search.split("\\s+"));

        when(userRepositoryClass.getCriteriaSearch(searchList)).thenReturn(Arrays.asList());

        // When
        List<UserDto> userDtoList = usersServiceImpl.searchUsers(search);

        // Then
        assertNotNull(userDtoList);
        assertTrue(userDtoList.isEmpty());

        verify(userRepositoryClass, times(1)).getCriteriaSearch(searchList);
        verify(dateUtils, never()).convertDateToString(any(Date.class));
    }

    @Test
    void updateUserTestSuccess() {
        // given
        when(usersRepository.findById(userDto.getUserId())).thenReturn(Optional.of(user));
        when(usersMapper.updateUserFromDto(userDto, user)).thenReturn(user);
        when(usersRepository.save(any(User.class))).thenReturn(user);
        when(usersMapper.userToUserDto(user)).thenReturn(userDto);

        // when
        Validable<UserDto> result = usersServiceImpl.updateUser(userDto.getUserId(), userDto);

        // then
        assertEquals(userDto, result.getObject()); // Check if returned UserDto matches expected
        verify(usersRepository, times(1)).findById(userDto.getUserId());
        verify(usersMapper, times(1)).updateUserFromDto(userDto, user);
        verify(usersRepository, times(1)).save(user);
        verify(usersMapper, times(1)).userToUserDto(user);
    }


    @Test
    void updateUserTestFailure() {
        // given
        when(this.usersRepository.findById(this.userDto.getUserId())).thenReturn(Optional.empty());

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.usersServiceImpl.updateUser(this.userDto.getUserId(), this.userDto));
        assertEquals("User not found with id 1", exception.getMessage());

        // then
        verify(this.usersRepository, times(1)).findById(this.userDto.getUserId());
        verify(this.usersMapper, never()).updateUserFromDto(any(UserDto.class), any(User.class));
        verify(this.usersRepository, never()).save(any(User.class));
        verify(this.usersMapper, never()).userToUserDto(any(User.class));
    }

    @Test
    void deleteUserTestSuccess() {
        // given
        Long userId = this.user.getUserId();
        doNothing().when(transactionRepository).deleteByTransactionsUserId(userId);
        doNothing().when(usersRepository).deleteById(userId);

        // when
        this.usersServiceImpl.deleteUser(userId);

        // then
        verify(this.transactionRepository, times(1)).deleteByTransactionsUserId(userId);
        verify(this.usersRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUserTestFailure() {
        // given
        Long userId = this.user.getUserId();
        doThrow(new RuntimeException("Database error")).when(this.transactionRepository).deleteByTransactionsUserId(userId);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.usersServiceImpl.deleteUser(userId));
        assertEquals("Database error", exception.getMessage());

        // then
        verify(this.transactionRepository, times(1)).deleteByTransactionsUserId(userId);
    }
}
