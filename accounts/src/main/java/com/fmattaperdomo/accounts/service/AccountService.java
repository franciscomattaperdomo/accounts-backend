package com.fmattaperdomo.accounts.service;

import com.fmattaperdomo.accounts.dto.AccountCreateRequestDto;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.AccountUpdateRequestDto;

import java.util.List;

public interface AccountService {
    /**
     *
     * @param accountCreateRequestDto - CustomerRequestDto Object
     */
    void createAccount(AccountCreateRequestDto accountCreateRequestDto);
    /**
     *
     * @param accountUpdateRequestDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(AccountUpdateRequestDto accountUpdateRequestDto, Long accountId);
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
    List<AccountResponseDto> getAccountsByCustomerId(Long customerId);
    /**
     *
     * @param branchName - Input brand name
     * @return list Account indicating if the search of Account details is successful or not
     */
    List<AccountResponseDto> getAccountsByBrandName(String branchName);
    /**
     *
     * @param accountType - Input brand name
     * @return list Account indicating if the search of Account details is successful or not
     */
    List<AccountResponseDto> getAccountsByAccountType(String accountType);
    /**
     *
     * @param accountStatus - Input brand name
     * @return list Account indicating if the search of Account details is successful or not
     */
    List<AccountResponseDto> getAccountsByAccountStatus(String accountStatus);
}