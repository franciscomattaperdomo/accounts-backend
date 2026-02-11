package com.fmattaperdomo.accounts.service.impl;

import com.fmattaperdomo.accounts.constant.AccountConstant;
import com.fmattaperdomo.accounts.dto.AccountCreateRequestDto;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.AccountUpdateRequestDto;
import com.fmattaperdomo.accounts.entity.Account;
import com.fmattaperdomo.accounts.entity.Customer;
import com.fmattaperdomo.accounts.exception.AccountAlreadyExistsException;
import com.fmattaperdomo.accounts.exception.ResourceNotFoundException;
import com.fmattaperdomo.accounts.mapper.AccountMapper;
import com.fmattaperdomo.accounts.repository.AccountRepository;
import com.fmattaperdomo.accounts.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    private Account buildAccount() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setCustomerId(1L);
        account.setAccountNumber(1234567890L);
        account.setAccountType("Savings");
        account.setBranchName("Bulevar Niza");
        account.setAccountStatus("Active");
        return account;
    }

    private Customer buildCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setDocumentType("CC");
        customer.setDocumentNumber("11122233");
        customer.setName("Cliente 1");
        customer.setEmail("cliente1@correo.com");
        customer.setMobileNumber("1112223333");
        return customer;
    }

    private AccountCreateRequestDto buildAccountCreateRequestDto() {
        AccountCreateRequestDto dto = new AccountCreateRequestDto();
        dto.setCustomerId(1L);
        dto.setAccountType("Savings");
        dto.setBranchName("Bulevar Niza");
        return dto;
    }

    private AccountUpdateRequestDto buildAccountUpdateRequestDto() {
        AccountUpdateRequestDto dto = new AccountUpdateRequestDto();
        dto.setCustomerId(1L);
        dto.setAccountType("Savings Updated");
        dto.setBranchName("Bulevar Niza");
        dto.setAccountStatus("Active");
        return dto;
    }

    @Test
    void createAccount() {
        // Arrange
        AccountCreateRequestDto dto = buildAccountCreateRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(1L, "Active")).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(buildAccount());

        // Act & Assert
        assertDoesNotThrow(() -> accountService.createAccount(dto));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccount_CustomerNotFound() {
        // Arrange
        AccountCreateRequestDto dto = buildAccountCreateRequestDto();

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.createAccount(dto));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void createAccount_AccountAlreadyExists() {
        // Arrange
        AccountCreateRequestDto dto = buildAccountCreateRequestDto();
        Customer customer = buildCustomer();
        Account account = buildAccount();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(1L, "Active")).thenReturn(Optional.of(account));

        // Act & Assert
        assertThrows(AccountAlreadyExistsException.class, () -> accountService.createAccount(dto));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void updateAccount() {
        // Arrange
        Long accountId = 1L;
        AccountUpdateRequestDto dto = buildAccountUpdateRequestDto();
        Account account = buildAccount();
        Customer customer = buildCustomer();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        boolean result = accountService.updateAccount(dto, accountId);

        // Assert
        assertTrue(result);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void updateAccount_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        AccountUpdateRequestDto dto = buildAccountUpdateRequestDto();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.updateAccount(dto, accountId));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void updateAccount_CustomerNotFound() {
        // Arrange
        Long accountId = 1L;
        AccountUpdateRequestDto dto = buildAccountUpdateRequestDto();
        Account account = buildAccount();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.updateAccount(dto, accountId));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void updateAccount_InvalidCustomerId() {
        // Arrange
        Long accountId = 1L;
        AccountUpdateRequestDto dto = buildAccountUpdateRequestDto();
        dto.setCustomerId(2L); // Different customer ID
        Account account = buildAccount();
        Customer customer = buildCustomer();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(AccountAlreadyExistsException.class, () -> accountService.updateAccount(dto, accountId));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void deleteAccount() {
        // Arrange
        Long accountId = 1L;
        Account account = buildAccount();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        boolean result = accountService.deleteAccount(accountId);

        // Assert
        assertTrue(result);
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void deleteAccount_AccountNotFound() {
        // Arrange
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.deleteAccount(accountId));
        verify(accountRepository, never()).deleteById(accountId);
    }

    @Test
    void getAccountByCustomerId() {
        // Arrange
        Long customerId = 1L;
        Customer customer = buildCustomer();
        Account account = buildAccount();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(customerId, "Active")).thenReturn(Optional.of(account));

        // Act
        AccountResponseDto result = accountService.getAccountByCustomerId(customerId);

        // Assert
        assertNotNull(result);
        assertThat(result.getAccountId()).isEqualTo(1L);
        assertThat(result.getCustomerId()).isEqualTo(1L);
        assertThat(result.getAccountType()).isEqualTo("Savings");
    }

    @Test
    void getAccountByCustomerId_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountByCustomerId(customerId));
    }

    @Test
    void getAccountByCustomerId_AccountNotFound() {
        // Arrange
        Long customerId = 1L;
        Customer customer = buildCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(customerId, "Active")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountByCustomerId(customerId));
    }

    @Test
    void getAccountsByCustomerId() {
        // Arrange
        Long customerId = 1L;
        Account account = buildAccount();
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        when(accountRepository.findByCustomerId(customerId)).thenReturn(accountList);

        // Act
        List<AccountResponseDto> result = accountService.getAccountsByCustomerId(customerId);

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(1L);
        assertThat(result.get(0).getAccountType()).isEqualTo("Savings");
    }

    @Test
    void getAccountsByCustomerId_NotFound() {
        // Arrange
        Long customerId = 1L;

        when(accountRepository.findByCustomerId(customerId)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountsByCustomerId(customerId));
    }

    @Test
    void getAccountsByBranchName() {
        // Arrange
        String branchName = "Bulevar Niza";
        Account account = buildAccount();
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        when(accountRepository.findByBranchNameContainingIgnoreCase(branchName)).thenReturn(accountList);

        // Act
        List<AccountResponseDto> result = accountService.getAccountsByBranchName(branchName);

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBranchName()).isEqualTo("Bulevar Niza");
        assertThat(result.get(0).getAccountType()).isEqualTo("Savings");
    }

    @Test
    void getAccountsByBranchName_NotFound() {
        // Arrange
        String branchName = "NonExistent";

        when(accountRepository.findByBranchNameContainingIgnoreCase(branchName)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountsByBranchName(branchName));
    }

    @Test
    void getAccountsByAccountType() {
        // Arrange
        String accountType = "Savings";
        Account account = buildAccount();
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        when(accountRepository.findByAccountTypeContainingIgnoreCase(accountType)).thenReturn(accountList);

        // Act
        List<AccountResponseDto> result = accountService.getAccountsByAccountType(accountType);

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAccountType()).isEqualTo("Savings");
        assertThat(result.get(0).getAccountId()).isEqualTo(1L);
    }

    @Test
    void getAccountsByAccountType_NotFound() {
        // Arrange
        String accountType = "NonExistent";

        when(accountRepository.findByAccountTypeContainingIgnoreCase(accountType)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountsByAccountType(accountType));
    }

    @Test
    void getAccountsByAccountStatus() {
        // Arrange
        String accountStatus = "Active";
        Account account = buildAccount();
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        when(accountRepository.findByAccountStatusContainingIgnoreCase(accountStatus)).thenReturn(accountList);

        // Act
        List<AccountResponseDto> result = accountService.getAccountsByAccountStatus(accountStatus);

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAccountStatus()).isEqualTo("Active");
        assertThat(result.get(0).getAccountId()).isEqualTo(1L);
    }

    @Test
    void getAccountsByAccountStatus_NotFound() {
        // Arrange
        String accountStatus = "NonExistent";

        when(accountRepository.findByAccountStatusContainingIgnoreCase(accountStatus)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountsByAccountStatus(accountStatus));
    }

    @Test
    void getAccounts() {
        // Arrange
        Account account = buildAccount();
        Customer customer = buildCustomer();
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        when(accountRepository.findAll()).thenReturn(accountList);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        List<AccountResponseDto> result = accountService.getAccounts();

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAccountId()).isEqualTo(1L);
        assertThat(result.get(0).getCustomerName()).isEqualTo("Cliente 1");
        assertThat(result.get(0).getAccountType()).isEqualTo("Savings");
    }

    @Test
    void getAccounts_Empty() {
        // Arrange
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<AccountResponseDto> result = accountService.getAccounts();

        // Assert
        assertNotNull(result);
        assertThat(result).isEmpty();
    }
}