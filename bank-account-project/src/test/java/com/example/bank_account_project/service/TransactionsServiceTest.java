package com.example.bank_account_project.service;

import com.example.bank_account_project.dto.TransactionDto;
import com.example.bank_account_project.dto.TransactionPageDto;
import com.example.bank_account_project.entity.Transaction;
import com.example.bank_account_project.entity.User;
import com.example.bank_account_project.mappers.TransactionMapper;
import com.example.bank_account_project.repository.TransactionRepository;
import com.example.bank_account_project.repository.TransactionRepositoryClass;
import com.example.bank_account_project.repository.UserRepository;
import com.example.bank_account_project.service.impl.TransactionServiceImpl;
import com.example.bank_account_project.utils.DateUtils;
import com.example.bank_account_project.utils.validable.Status;
import com.example.bank_account_project.utils.validable.Validable;
import com.example.bank_account_project.utils.validable.input.TransactionInput;
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
public class TransactionsServiceTest {

    @Mock
    private TransactionRepositoryClass transactionRepositoryClass;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionsServiceImpl;

    private Transaction transaction;

    private TransactionDto transactionDto;

    @Mock
    private DateUtils dateUtils;

    @Mock
    private TransactionInput transactionInput;

    @BeforeEach
    void init() {
        this.transaction = Transaction.builder()
                .transactionsId(1L)
                .transactionsAmount(100.0)
                .transactionsMessage("Test transaction")
                .build();

        this.transactionDto = TransactionDto.builder()
                .transactionsId(1L)
                .transactionsAmount(100.0)
                .transactionsMessage("Test transaction")
                .build();
    }

    @Test
    void createTransactionTestSuccess() {
        // given
        List<Status> statusList = new ArrayList<>();
        when(transactionInput.TransactionInput(transactionDto)).thenReturn(statusList);
        when(transactionMapper.transactionDtoToTransactions(any(TransactionDto.class))).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(userService.updateAccountBalance(any(), any())).thenReturn(new User());
        when(transactionMapper.transactionToTransactionsDto(any(Transaction.class))).thenReturn(transactionDto);

        // when
        Validable<TransactionDto> result = transactionsServiceImpl.createTransactions(transactionDto);

        // then
        assertEquals(transactionDto, result.getObject());
        verify(transactionMapper, times(1)).transactionDtoToTransactions(transactionDto);
        verify(transactionRepository, times(1)).save(transaction);
        verify(userService, times(1)).updateAccountBalance(any(), any());
        verify(transactionMapper, times(1)).transactionToTransactionsDto(transaction);
    }


    @Test
    void createTransactionTestFailure() {
        // given
        when(this.transactionMapper.transactionDtoToTransactions(this.transactionDto)).thenReturn(this.transaction);
        when(this.transactionRepository.save(any(Transaction.class))).thenThrow(new RuntimeException("Database error"));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.transactionsServiceImpl.createTransactions(this.transactionDto));
        assertEquals("Database error", exception.getMessage());

        // then
        verify(this.transactionMapper, times(1)).transactionDtoToTransactions(this.transactionDto);
        verify(this.transactionRepository, times(1)).save(this.transaction);
    }

    @Test
    void getTransactionByIdTestSuccess() {
        // given
        when(this.transactionRepository.findById(1L)).thenReturn(Optional.ofNullable(this.transaction));
        when(this.transactionMapper.transactionToTransactionsDto(this.transaction)).thenReturn(this.transactionDto);

        // when
        Optional<TransactionDto> transactionDtoToVerify = this.transactionsServiceImpl.getTransactionsById(1L);

        // then
        assertEquals(Optional.of(this.transactionDto), transactionDtoToVerify);
        verify(this.transactionRepository, times(1)).findById(1L);
        verify(this.transactionMapper, times(1)).transactionToTransactionsDto(this.transaction);
    }

    @Test
    void getTransactionByIdTestFailure() {
        // given
        when(this.transactionRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        Optional<TransactionDto> transactionDtoToVerify = this.transactionsServiceImpl.getTransactionsById(1L);

        // then
        assertEquals(Optional.empty(), transactionDtoToVerify);
        verify(this.transactionRepository, times(1)).findById(1L);
        verify(this.transactionMapper, never()).transactionToTransactionsDto(any(Transaction.class));
    }

    @Test
    void getAllTransactionsTestSuccess() {
        // given
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(this.transaction);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(this.transactionDto);

        when(this.transactionRepository.findAll()).thenReturn(transactionList);
        when(this.transactionMapper.transactionToTransactionsDto(this.transaction)).thenReturn(this.transactionDto);

        // when
        List<TransactionDto> transactionDtoListToVerify = this.transactionsServiceImpl.getAllTransactions();

        // then
        assertEquals(transactionDtoList, transactionDtoListToVerify);
        verify(this.transactionRepository, times(1)).findAll();
        verify(this.transactionMapper, times(1)).transactionToTransactionsDto(this.transaction);
    }

    @Test
    void getAllTransactionsTestFailure() {
        // given
        when(this.transactionRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.transactionsServiceImpl.getAllTransactions());
        assertEquals("Database error", exception.getMessage());

        // then
        verify(this.transactionRepository, times(1)).findAll();
        verify(this.transactionMapper, never()).transactionToTransactionsDto(any(Transaction.class));
    }

    @Test
    void deleteTransactionTestSuccess() {
        // given
        Long transactionId = this.transaction.getTransactionsId();
        Transaction transaction = new Transaction();
        transaction.setTransactionsId(transactionId);
        transaction.setTransactionsAmount(-100.0);
        User user = new User();
        user.setUserId(1L);
        transaction.setUser(user);
        when(transactionRepository.getReferenceById(transactionId)).thenReturn(transaction);
        doNothing().when(transactionRepository).deleteById(transactionId);
        when(userService.updateAccountBalance(transaction.getUser().getUserId(), 100.0)).thenReturn(user);

        // when
        this.transactionsServiceImpl.deleteTransactions(transactionId);

        // then
        verify(this.transactionRepository, times(1)).deleteById(transactionId);
        verify(this.userService, times(1)).updateAccountBalance(transaction.getUser().getUserId(), 100.0);
    }


    @Test
    void deleteTransactionTestFailure() {
        // given
        Long transactionId = this.transaction.getTransactionsId();
        doThrow(new RuntimeException("Database error")).when(this.transactionRepository).getReferenceById(transactionId);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.transactionsServiceImpl.deleteTransactions(transactionId));
        assertEquals("Database error", exception.getMessage());

        // then
        verify(this.transactionRepository, times(1)).getReferenceById(transactionId);
    }

    @Test
    void searchTransactionPageTestSuccess() {
        // Given
        Object[] mockRow;
        List<Object[]> queryResult  = new ArrayList<>();
        mockRow = new Object[]{
                1L, 100.0, "Test Message", new Date(), 1L, "FirstName", "LastName", "email@example.com", 200.0
        };
        queryResult.add(mockRow);

        when(dateUtils.convertDateToString((Date) mockRow[3])).thenReturn("01-01-2000");
        String search = "test message";
        List<String> searchList = Arrays.asList(search.split("\\s+"));

        when(transactionRepositoryClass.getCriteriaSearch(searchList)).thenReturn(queryResult);

        // When
        List<TransactionPageDto> transactionPageDtoList = transactionsServiceImpl.searchTransactionPage(search);

        // Then
        assertNotNull(transactionPageDtoList);
        assertEquals(1, transactionPageDtoList.size());

        TransactionPageDto transactionPageDto = transactionPageDtoList.get(0);
        assertEquals(1L, transactionPageDto.getTransactionsId());
        assertEquals(100.0, transactionPageDto.getTransactionsAmount());
        assertEquals("Test Message", transactionPageDto.getTransactionsMessage());
        assertEquals("01-01-2000", transactionPageDto.getCreationDate());
        assertEquals(1L, transactionPageDto.getUserId());
        assertEquals("FirstName LastName", transactionPageDto.getUserIdentity());
        assertEquals("email@example.com", transactionPageDto.getUserEmail());
        assertEquals(200.0, transactionPageDto.getUserAccountBalance());

        verify(transactionRepositoryClass, times(1)).getCriteriaSearch(searchList);
        verify(dateUtils, times(1)).convertDateToString((Date) mockRow[3]);
    }

    @Test
    void searchTransactionPageTestNoResults() {
        // Given
        String search = "nonexistent transaction";
        List<String> searchList = Arrays.asList(search.split("\\s+"));

        when(transactionRepositoryClass.getCriteriaSearch(searchList)).thenReturn(Arrays.asList());

        // When
        List<TransactionPageDto> transactionPageDtoList = transactionsServiceImpl.searchTransactionPage(search);

        // Then
        assertNotNull(transactionPageDtoList);
        assertTrue(transactionPageDtoList.isEmpty());

        verify(transactionRepositoryClass, times(1)).getCriteriaSearch(searchList);
        verify(dateUtils, never()).convertDateToString(any(Date.class));
    }
}
