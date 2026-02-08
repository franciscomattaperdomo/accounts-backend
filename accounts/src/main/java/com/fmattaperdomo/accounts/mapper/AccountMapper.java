package com.fmattaperdomo.accounts.mapper;

import com.fmattaperdomo.accounts.dto.AccountRequestDto;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.entity.Account;

public class AccountMapper {
    public static AccountRequestDto mapToAccountRequestDto(Account account, AccountRequestDto accountRequestDto) {
        accountRequestDto.setAccountType(account.getAccountType());
        accountRequestDto.setBranchName(account.getBranchName());
        return accountRequestDto;
    }

    public static Account mapToAccountRequest(AccountRequestDto accountsDto, Account account) {
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchName(accountsDto.getBranchName());
        return account;
    }

    public static AccountResponseDto mapToAccountResponseDto(Account account, AccountResponseDto accountResponseDto) {
        accountResponseDto.setAccountId(account.getAccountId());
        accountResponseDto.setCustomerId(account.getCustomerId());
        accountResponseDto.setAccountNumber(account.getAccountNumber());
        accountResponseDto.setAccountStatus(account.getAccountStatus());
        accountResponseDto.setAccountType(account.getAccountType());
        accountResponseDto.setBranchName(account.getBranchName());
        return accountResponseDto;
    }

    public static Account mapToAccountResponse(AccountResponseDto accountResponseDto, Account account) {
        account.setAccountId(accountResponseDto.getAccountId());
        account.setCustomerId(accountResponseDto.getCustomerId());
        account.setAccountNumber(accountResponseDto.getAccountNumber());
        account.setAccountStatus(accountResponseDto.getAccountStatus());
        account.setAccountType(accountResponseDto.getAccountType());
        account.setBranchName(accountResponseDto.getBranchName());
        return account;
    }
}