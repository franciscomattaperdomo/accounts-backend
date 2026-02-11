package com.fmattaperdomo.accounts.controller;

import com.fmattaperdomo.accounts.constant.AccountConstant;
import com.fmattaperdomo.accounts.dto.*;
import com.fmattaperdomo.accounts.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @InjectMocks
    AccountController accountController;

    @Mock
    private AccountService accountService;

    private AccountResponseDto mockAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountType("Cuenta de ahorros");
        accountResponseDto.setAccountStatus("Active");
        accountResponseDto.setAccountNumber(1234567890L);
        accountResponseDto.setBranchName("Bulevar Niza");
        accountResponseDto.setAccountId(1L);
        return accountResponseDto;
    }

    @Test
    void createAccount() {
        AccountCreateRequestDto accountCreateRequestDto = new AccountCreateRequestDto();
        accountCreateRequestDto.setCustomerId(1L);
        accountCreateRequestDto.setAccountType("Savings");
        accountCreateRequestDto.setBranchName("Bulevar Niza");

        doNothing().when(accountService).createAccount(any(AccountCreateRequestDto.class));

        ResponseEntity<ResponseDto> response = accountController.createAccount(accountCreateRequestDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(AccountConstant.STATUS_201);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(AccountConstant.MESSAGE_201);
    }

    @Test
    void getAccountsByCustomerId() {
        Long customerId = 1L;
        List<AccountResponseDto> accountList = new ArrayList<>();
        accountList.add(mockAccountResponseDto());
        when(accountService.getAccountsByCustomerId(anyLong())).thenReturn(accountList);

        ResponseEntity<List<AccountResponseDto>> response = accountController.getAccountsByCustomerId(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getAccountId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getAccountType()).isEqualTo("Cuenta de ahorros");
        assertThat(response.getBody().get(0).getAccountStatus()).isEqualTo("Active");
    }

    @Test
    void getAccountsByBranchName() {
        String branchName = "Bulevar Niza";
        List<AccountResponseDto> accountList = new ArrayList<>();
        accountList.add(mockAccountResponseDto());
        when(accountService.getAccountsByBranchName(anyString())).thenReturn(accountList);

        ResponseEntity<List<AccountResponseDto>> response = accountController.getAccountsByBranchName(branchName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getBranchName()).isEqualTo("Bulevar Niza");
        assertThat(response.getBody().get(0).getAccountType()).isEqualTo("Cuenta de ahorros");
    }

    @Test
    void getAccountsByAccountType() {
        String accountType = "Cuenta de ahorros";
        List<AccountResponseDto> accountList = new ArrayList<>();
        accountList.add(mockAccountResponseDto());
        when(accountService.getAccountsByAccountType(anyString())).thenReturn(accountList);

        ResponseEntity<List<AccountResponseDto>> response = accountController.getAccountsByAccountType(accountType);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getAccountType()).isEqualTo("Cuenta de ahorros");
        assertThat(response.getBody().get(0).getAccountId()).isEqualTo(1L);
    }

    @Test
    void getAccountsByAccountStatus() {
        String accountStatus = "Active";
        List<AccountResponseDto> accountList = new ArrayList<>();
        accountList.add(mockAccountResponseDto());
        when(accountService.getAccountsByAccountStatus(anyString())).thenReturn(accountList);

        ResponseEntity<List<AccountResponseDto>> response = accountController.getAccountsByAccountStatus(accountStatus);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getAccountStatus()).isEqualTo("Active");
        assertThat(response.getBody().get(0).getAccountId()).isEqualTo(1L);
    }

    @Test
    void getAccountByCustomerId() {
        Long customerId = 1L;
        AccountResponseDto accountResponseDto = mockAccountResponseDto();
        when(accountService.getAccountByCustomerId(anyLong())).thenReturn(accountResponseDto);

        ResponseEntity<AccountResponseDto> response = accountController.getAccountByCustomerId(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAccountId()).isEqualTo(1L);
        assertThat(response.getBody().getAccountType()).isEqualTo("Cuenta de ahorros");
        assertThat(response.getBody().getAccountStatus()).isEqualTo("Active");
        assertThat(response.getBody().getBranchName()).isEqualTo("Bulevar Niza");
    }

    @Test
    void getAccounts() {
        List<AccountResponseDto> accountList = new ArrayList<>();
        accountList.add(mockAccountResponseDto());
        when(accountService.getAccounts()).thenReturn(accountList);

        ResponseEntity<List<AccountResponseDto>> response = accountController.getAccounts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getAccountId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getAccountType()).isEqualTo("Cuenta de ahorros");
        assertThat(response.getBody().get(0).getAccountStatus()).isEqualTo("Active");
        assertThat(response.getBody().get(0).getBranchName()).isEqualTo("Bulevar Niza");
    }

    @Test
    void updateAccount() {
        Long accountId = 1L;
        AccountUpdateRequestDto accountUpdateRequestDto = new AccountUpdateRequestDto();
        accountUpdateRequestDto.setCustomerId(1L);
        accountUpdateRequestDto.setAccountType("Savings Actualizada");
        accountUpdateRequestDto.setBranchName("Bulevar Niza");
        accountUpdateRequestDto.setAccountStatus("Active");

        when(accountService.updateAccount(any(AccountUpdateRequestDto.class), anyLong())).thenReturn(true);

        ResponseEntity<ResponseDto> response = accountController.updateAccount(accountUpdateRequestDto, accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(AccountConstant.STATUS_200);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(AccountConstant.MESSAGE_200);
    }

    @Test
    void updateAccountFailure() {
        Long accountId = 1L;
        AccountUpdateRequestDto accountUpdateRequestDto = new AccountUpdateRequestDto();
        accountUpdateRequestDto.setCustomerId(1L);
        accountUpdateRequestDto.setAccountType("Savings");
        accountUpdateRequestDto.setBranchName("Bulevar Niza");
        accountUpdateRequestDto.setAccountStatus("Active");

        when(accountService.updateAccount(any(AccountUpdateRequestDto.class), anyLong())).thenReturn(false);

        ResponseEntity<ResponseDto> response = accountController.updateAccount(accountUpdateRequestDto, accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(AccountConstant.STATUS_417);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(AccountConstant.MESSAGE_417_UPDATE);
    }

    @Test
    void deleteAccount() {
        Long accountId = 1L;
        when(accountService.deleteAccount(anyLong())).thenReturn(true);

        ResponseEntity<ResponseDto> response = accountController.deleteAccount(accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(AccountConstant.STATUS_200);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(AccountConstant.MESSAGE_200);
    }

    @Test
    void deleteAccountFailure() {
        Long accountId = 1L;
        when(accountService.deleteAccount(anyLong())).thenReturn(false);

        ResponseEntity<ResponseDto> response = accountController.deleteAccount(accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(AccountConstant.STATUS_417);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(AccountConstant.MESSAGE_417_DELETE);
    }
}