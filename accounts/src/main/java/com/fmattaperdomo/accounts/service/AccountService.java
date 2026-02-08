package com.fmattaperdomo.accounts.service;

import com.fmattaperdomo.accounts.dto.AccountRequestDto;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;

import java.util.List;

public interface AccountService {
    /**
     *
     * @param customerRequestDto - CustomerRequestDto Object
     */
    void createAccount(AccountRequestDto customerRequestDto);
    /**
     *
     * @param customerRequestDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(AccountRequestDto customerRequestDto);
    /**
     *
     * @param accountId - Input Account ID
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(Long accountId);
    /**
     *
     * @param customerId - Input Customer ID
     * @return get Account indicating if the search of Account details is successful or not
     */
    AccountResponseDto getAccountByCustomerId(Long customerId);
    /**
     *
     * @param branchName - Input brand name
     * @return list Account indicating if the search of Account details is successful or not
     */
    List<AccountResponseDto> getAccountByBrandName(String branchName);
    /**
     *
     * @param accountType - Input brand name
     * @return list Account indicating if the search of Account details is successful or not
     */
    List<AccountResponseDto> getAccountByAccountType(String accountType);
    /**
     *
     * @param accountStatus - Input brand name
     * @return list Account indicating if the search of Account details is successful or not
     */
    List<AccountResponseDto> getAccountByAccountStatus(String accountStatus);
}