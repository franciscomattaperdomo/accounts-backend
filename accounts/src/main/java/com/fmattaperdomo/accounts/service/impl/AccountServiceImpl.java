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
import com.fmattaperdomo.accounts.service.AccountService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     * @param accountCreateRequestDto - AccountCreateRequestDto Object
     */
    @Override
    public void createAccount(AccountCreateRequestDto accountCreateRequestDto) {
        Account account = AccountMapper.mapToAccountRequest(accountCreateRequestDto, new Account());
        Optional<Customer> optionalCustomer = customerRepository.findById(accountCreateRequestDto.getCustomerId());
        if(!optionalCustomer.isPresent()) {
            throw new ResourceNotFoundException("Customer","customerId",accountCreateRequestDto.getCustomerId().toString());
        }

        Optional<Account> optionalAccount = accountRepository.findByCustomerIdAndAccountStatus(accountCreateRequestDto.getCustomerId(),"Active");
        if(optionalAccount.isPresent()) {
            throw new AccountAlreadyExistsException("Account already registered with given customer id "
                    + accountCreateRequestDto.getCustomerId());
        }

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(randomAccNumber);
        account.setAccountStatus(AccountConstant.ACCOUNT_STATUS);
        accountRepository.save(account);
    }

    /**
     * @param1 accountUpdateRequestDto - CustomerDto Object
     * @param2 accountId - Long
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(AccountUpdateRequestDto accountUpdateRequestDto, Long accountId) {
        boolean isUpdated = false;
        if(accountUpdateRequestDto !=null ){
            Account account = accountRepository.findById(accountId).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountId", accountId.toString())
            );
            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            if (accountUpdateRequestDto.getCustomerId() != customerId){
                throw new AccountAlreadyExistsException("Bad request with customer id  "
                        + customerId.toString());
            }
            AccountMapper.mapToAccountUpdateRequest(accountUpdateRequestDto, account);
            accountRepository.save(account);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param accountId - Input Account id
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountId", accountId.toString())
        );
        accountRepository.deleteById(account.getAccountId());
        return true;
    }

    /**
     * @param customerId - Input Customer id
     * @return list of accounts details
     */
    @Override
    public List<AccountResponseDto> getAccountsByCustomerId(Long customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        if(accounts.isEmpty()) {
            throw new ResourceNotFoundException("Account","customerId",customerId.toString());
        }

        return accounts.stream()
                .map(account -> AccountMapper.mapToAccountResponseDto(account, new AccountResponseDto()))
                .toList();
    }

    /**
     * @param branchName - Input Branch name
     * @return list of accounts details
     */
    @Override
    public List<AccountResponseDto> getAccountsByBrandName(String branchName) {
        List<Account> accounts = accountRepository.findByBrandName(branchName);
        if(accounts.isEmpty()) {
            throw new ResourceNotFoundException("Account","brandName",branchName);
        }

        return accounts.stream()
                .map(account -> AccountMapper.mapToAccountResponseDto(account, new AccountResponseDto()))
                .toList();
    }

    /**
     * @param accountType - Input Account type
     * @return list of accounts details
     */
    @Override
    public List<AccountResponseDto> getAccountsByAccountType(String accountType) {
        List<Account> accounts = accountRepository.findByAccountType(accountType);
        if(accounts.isEmpty()) {
            throw new ResourceNotFoundException("Account","accountType",accountType);
        }

        return accounts.stream()
                .map(account -> AccountMapper.mapToAccountResponseDto(account, new AccountResponseDto()))
                .toList();
    }

    /**
     * @param accountStatus - Input Account status
     * @return list of accounts details
     */

    @Override
    public List<AccountResponseDto> getAccountsByAccountStatus(String accountStatus) {
        List<Account> accounts = accountRepository.findByAccountStatus(accountStatus);
        if(accounts.isEmpty()) {
            throw new ResourceNotFoundException("Account","accountType",accountStatus);
        }

        return accounts.stream()
                .map(account -> AccountMapper.mapToAccountResponseDto(account, new AccountResponseDto()))
                .toList();

    }
}
