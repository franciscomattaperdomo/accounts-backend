package com.fmattaperdomo.accounts.service.impl;

import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.CustomerRequestDto;
import com.fmattaperdomo.accounts.dto.CustomerResponseDto;
import com.fmattaperdomo.accounts.entity.Account;
import com.fmattaperdomo.accounts.entity.Customer;
import com.fmattaperdomo.accounts.exception.CustomerAlreadyExistsException;
import com.fmattaperdomo.accounts.exception.ResourceNotFoundException;
import com.fmattaperdomo.accounts.mapper.AccountMapper;
import com.fmattaperdomo.accounts.mapper.CustomerMapper;
import com.fmattaperdomo.accounts.repository.AccountRepository;
import com.fmattaperdomo.accounts.repository.CustomerRepository;
import com.fmattaperdomo.accounts.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerResponseDto> getCustomersByName(String name) {
        List<Customer> customers = customerRepository.findByNameContainingIgnoreCase(name);

        return customers.stream()
                .map(customer -> {
                    Optional<Account> optionalAccount = accountRepository.findByCustomerIdAndAccountStatus(customer.getCustomerId(),"Active");
                    AccountResponseDto accountResponseDto = new AccountResponseDto();
                    if(optionalAccount.isPresent()) {
                        accountResponseDto = AccountMapper.mapToAccountResponseDto(optionalAccount.get(), new AccountResponseDto());
                    }
                    CustomerResponseDto customerResponseDto = CustomerMapper.mapToCustomerResponseDto(customer, new CustomerResponseDto(),accountResponseDto);
                    return customerResponseDto;
                })
                .toList();
    }

    @Override
    public List<CustomerResponseDto> getCustomersByMobileNumber(String mobileNumber) {
        List<Customer> customers = customerRepository.findByMobileNumberContainingIgnoreCase(mobileNumber);

        return customers.stream()
                .map(customer -> {
                    Optional<Account> optionalAccount = accountRepository.findByCustomerIdAndAccountStatus(customer.getCustomerId(),"Active");
                    AccountResponseDto accountResponseDto = new AccountResponseDto();
                    if(optionalAccount.isPresent()) {
                        accountResponseDto = AccountMapper.mapToAccountResponseDto(optionalAccount.get(), new AccountResponseDto());
                    }
                    CustomerResponseDto customerResponseDto = CustomerMapper.mapToCustomerResponseDto(customer, new CustomerResponseDto(),accountResponseDto);
                    return customerResponseDto;
                })
                .toList();
    }

    @Override
    public List<CustomerResponseDto> getCustomersByEmail(String email) {
        List<Customer> customers = customerRepository.findByEmailContainingIgnoreCase(email);

        return customers.stream()
                .map(customer -> {
                    Optional<Account> optionalAccount = accountRepository.findByCustomerIdAndAccountStatus(customer.getCustomerId(),"Active");
                    AccountResponseDto accountResponseDto = new AccountResponseDto();
                    if(optionalAccount.isPresent()) {
                        accountResponseDto = AccountMapper.mapToAccountResponseDto(optionalAccount.get(), new AccountResponseDto());
                    }
                    CustomerResponseDto customerResponseDto = CustomerMapper.mapToCustomerResponseDto(customer, new CustomerResponseDto(),accountResponseDto);
                    return customerResponseDto;
                })
                .toList();
    }

    @Override
    public CustomerResponseDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
        );
        Optional<Account> optionalAccount = accountRepository.findByCustomerIdAndAccountStatus(customer.getCustomerId(),"Active");
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        if(optionalAccount.isPresent()) {
            accountResponseDto = AccountMapper.mapToAccountResponseDto(optionalAccount.get(), new AccountResponseDto());
        }
        CustomerResponseDto customerResponseDto = CustomerMapper.mapToCustomerResponseDto(customer, new CustomerResponseDto(),accountResponseDto);
        return customerResponseDto;
    }

    @Override
    public List<CustomerResponseDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(customer -> {
                    Optional<Account> optionalAccount = accountRepository.findByCustomerIdAndAccountStatus(customer.getCustomerId(),"Active");
                    AccountResponseDto accountResponseDto = new AccountResponseDto();
                    if(optionalAccount.isPresent()) {
                        accountResponseDto = AccountMapper.mapToAccountResponseDto(optionalAccount.get(), new AccountResponseDto());
                    }
                    CustomerResponseDto customerResponseDto = CustomerMapper.mapToCustomerResponseDto(customer, new CustomerResponseDto(),accountResponseDto);
                    return customerResponseDto;
                })
                .toList();
    }

    @Transactional(readOnly = false)
    @Modifying
    @Override
    public void createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = CustomerMapper.mapToCustomerRequest(customerRequestDto, new Customer());

        Optional<Customer> optionalCustomer;
        optionalCustomer = customerRepository.findByEmail(customerRequestDto.getEmail());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given email "
                    +customerRequestDto.getEmail());
        }
        optionalCustomer = customerRepository.findByMobileNumber(customerRequestDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    +customerRequestDto.getMobileNumber());
        }
        optionalCustomer = customerRepository.findByDocumentNumber(customerRequestDto.getDocumentNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given document number "
                    +customerRequestDto.getDocumentNumber());
        }

        customerRepository.save(customer);
    }

    @Transactional(readOnly = false)
    @Modifying
    @Override
    public boolean updateCustomer(CustomerRequestDto customerRequestDto,Long customerId) {
        boolean isUpdated = false;
        if(customerRequestDto !=null ){
            Optional<Customer> optionalCustomer;
            optionalCustomer = customerRepository.findByEmailAndCustomerIdNot(customerRequestDto.getEmail(),customerId);
            if(optionalCustomer.isPresent()) {
                throw new CustomerAlreadyExistsException("Customer already registered with given email "
                        +customerRequestDto.getEmail());
            }
            optionalCustomer = customerRepository.findByMobileNumberAndCustomerIdNot(customerRequestDto.getMobileNumber(),customerId);
            if(optionalCustomer.isPresent()) {
                throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                        +customerRequestDto.getMobileNumber());
            }
            optionalCustomer = customerRepository.findByDocumentNumberAndCustomerIdNot(customerRequestDto.getDocumentNumber(),customerId);
            if(optionalCustomer.isPresent()) {
                throw new CustomerAlreadyExistsException("Customer already registered with given document number "
                        +customerRequestDto.getDocumentNumber());
            }
            optionalCustomer = customerRepository.findById(customerId);
            if(!optionalCustomer.isPresent()) {
                throw new ResourceNotFoundException("Customer", "CustomerID", customerId.toString());
            }
            Customer customer = CustomerMapper.mapToCustomerRequest(customerRequestDto, optionalCustomer.get());
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Transactional(readOnly = false)
    @Modifying
    @Override
    public boolean deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
        );
        accountRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
