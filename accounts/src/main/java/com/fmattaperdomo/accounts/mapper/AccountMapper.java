package com.fmattaperdomo.accounts.mapper;

import com.fmattaperdomo.accounts.dto.AccountCreateRequestDto;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.AccountUpdateRequestDto;
import com.fmattaperdomo.accounts.entity.Account;

public class AccountMapper {
    public static AccountCreateRequestDto mapToAccountRequestDto(Account account, AccountCreateRequestDto accountCreateRequestDto) {
        accountCreateRequestDto.setCustomerId(account.getCustomerId());
        accountCreateRequestDto.setAccountType(account.getAccountType());
        accountCreateRequestDto.setBranchName(account.getBranchName());
        return accountCreateRequestDto;
    }

    public static Account mapToAccountRequest(AccountCreateRequestDto accountCreateRequestDto, Account account) {
        account.setCustomerId(accountCreateRequestDto.getCustomerId());
        account.setAccountType(accountCreateRequestDto.getAccountType());
        account.setBranchName(accountCreateRequestDto.getBranchName());
        return account;
    }

    public static AccountUpdateRequestDto mapToAccountUpdateRequestDto(Account account, AccountUpdateRequestDto accountUpdateRequestDto) {
        accountUpdateRequestDto.setCustomerId(account.getCustomerId());
        accountUpdateRequestDto.setAccountType(account.getAccountType());
        accountUpdateRequestDto.setBranchName(account.getBranchName());
        accountUpdateRequestDto.setAccountStatus(account.getAccountStatus());
        return accountUpdateRequestDto;
    }

    public static Account mapToAccountUpdateRequest(AccountUpdateRequestDto accountUpdateRequestDto, Account account) {
        account.setCustomerId(accountUpdateRequestDto.getCustomerId());
        account.setAccountType(accountUpdateRequestDto.getAccountType());
        account.setBranchName(accountUpdateRequestDto.getBranchName());
        account.setAccountStatus(accountUpdateRequestDto.getAccountStatus());
        return account;
    }

    public static AccountResponseDto mapToAccountResponseDto(Account account, AccountResponseDto accountResponseDto) {
        accountResponseDto.setAccountId(account.getAccountId());
        accountResponseDto.setCustomerId(account.getCustomerId());
        accountResponseDto.setAccountNumber(account.getAccountNumber());
        accountResponseDto.setAccountStatus(account.getAccountStatus());
        accountResponseDto.setAccountType(account.getAccountType());
        accountResponseDto.setBranchName(account.getBranchName());
        accountResponseDto.setAccountStatus(account.getAccountStatus());
        return accountResponseDto;
    }

    public static Account mapToAccountResponse(AccountResponseDto accountResponseDto, Account account) {
        account.setAccountId(accountResponseDto.getAccountId());
        account.setCustomerId(accountResponseDto.getCustomerId());
        account.setAccountNumber(accountResponseDto.getAccountNumber());
        account.setAccountStatus(accountResponseDto.getAccountStatus());
        account.setAccountType(accountResponseDto.getAccountType());
        account.setBranchName(accountResponseDto.getBranchName());
        account.setAccountStatus(accountResponseDto.getAccountStatus());
        return account;
    }
}