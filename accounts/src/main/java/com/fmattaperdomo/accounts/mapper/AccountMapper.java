package com.fmattaperdomo.accounts.mapper;

import com.fmattaperdomo.accounts.dto.AccountRequestDto;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.entity.Account;

public class AccountMapper {
    public static AccountRequestDto mapToAccountRequestDto(Account account, AccountRequestDto accountRequestDto) {
        accountRequestDto.setAccountType(account.getAccountType());
        accountRequestDto.setBranchAddress(account.getBranchAddress());
        return accountRequestDto;
    }

    public static Account mapToAccountRequest(AccountRequestDto accountsDto, Account account) {
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());
        return account;
    }

    public static AccountResponseDto mapToAccountResponseDto(Account account, AccountResponseDto accountResponseDto) {
        accountResponseDto.setAccountId(account.getAccountId());
        accountResponseDto.setCustomerId(account.getCustomerId());
        accountResponseDto.setAccountNumber(account.getAccountNumber());
        accountResponseDto.setAccountStatus(account.getAccountStatus());
        accountResponseDto.setAccountType(account.getAccountType());
        accountResponseDto.setBranchAddress(account.getBranchAddress());
        return accountResponseDto;
    }

    public static Account mapToAccountResponse(AccountResponseDto accountResponseDto, Account account) {
        account.setAccountId(accountResponseDto.getAccountId());
        account.setCustomerId(accountResponseDto.getCustomerId());
        account.setAccountNumber(accountResponseDto.getAccountNumber());
        account.setAccountStatus(accountResponseDto.getAccountStatus());
        account.setAccountType(accountResponseDto.getAccountType());
        account.setBranchAddress(accountResponseDto.getBranchAddress());
        return account;
    }

}