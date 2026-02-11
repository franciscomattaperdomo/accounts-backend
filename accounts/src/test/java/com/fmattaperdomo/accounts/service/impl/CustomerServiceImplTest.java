package com.fmattaperdomo.accounts.service.impl;

import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.CustomerRequestDto;
import com.fmattaperdomo.accounts.dto.CustomerResponseDto;
import com.fmattaperdomo.accounts.entity.Account;
import com.fmattaperdomo.accounts.entity.Customer;
import com.fmattaperdomo.accounts.exception.CustomerAlreadyExistsException;
import com.fmattaperdomo.accounts.exception.ResourceNotFoundException;
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
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

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

    private CustomerRequestDto buildCustomerRequestDto() {
        CustomerRequestDto dto = new CustomerRequestDto();
        dto.setDocumentType("CC");
        dto.setDocumentNumber("11122233");
        dto.setName("Cliente 1");
        dto.setEmail("cliente1@correo.com");
        dto.setMobileNumber("1112223333");
        return dto;
    }

    @Test
    void getCustomerByMobileNumber() {
        // Arrange
        String mobileNumber = "1112223333";
        Customer customer = buildCustomer();
        Account account = buildAccount();

        when(customerRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(1L, "Active")).thenReturn(Optional.of(account));

        // Act
        CustomerResponseDto result = customerService.getCustomerByMobileNumber(mobileNumber);

        // Assert
        assertNotNull(result);
        assertThat(result.getCustomerId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Cliente 1");
        assertThat(result.getMobileNumber()).isEqualTo("1112223333");
        assertThat(result.getAccountResponseDto()).isNotNull();
    }

    @Test
    void getCustomerByMobileNumber_NotFound() {
        // Arrange
        String mobileNumber = "9999999999";

        when(customerRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerByMobileNumber(mobileNumber));
    }

    @Test
    void getCustomerByEmail() {
        // Arrange
        String email = "cliente1@correo.com";
        Customer customer = buildCustomer();
        Account account = buildAccount();

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(1L, "Active")).thenReturn(Optional.of(account));

        // Act
        CustomerResponseDto result = customerService.getCustomerByEmail(email);

        // Assert
        assertNotNull(result);
        assertThat(result.getCustomerId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("cliente1@correo.com");
        assertThat(result.getName()).isEqualTo("Cliente 1");
        assertThat(result.getAccountResponseDto()).isNotNull();
    }

    @Test
    void getCustomerByEmail_NotFound() {
        // Arrange
        String email = "notexist@correo.com";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerByEmail(email));
    }

    @Test
    void getCustomerById() {
        // Arrange
        Long customerId = 1L;
        Customer customer = buildCustomer();
        Account account = buildAccount();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomerIdAndAccountStatus(customerId, "Active")).thenReturn(Optional.of(account));

        // Act
        CustomerResponseDto result = customerService.getCustomerById(customerId);

        // Assert
        assertNotNull(result);
        assertThat(result.getCustomerId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Cliente 1");
        assertThat(result.getEmail()).isEqualTo("cliente1@correo.com");
        assertThat(result.getAccountResponseDto()).isNotNull();
    }

    @Test
    void getCustomerById_NotFound() {
        // Arrange
        Long customerId = 999L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(customerId));
    }

    @Test
    void getCustomers() {
        // Arrange
        Customer customer = buildCustomer();
        Account account = buildAccount();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        when(customerRepository.findAll()).thenReturn(customerList);
        when(accountRepository.findByCustomerIdAndAccountStatus(1L, "Active")).thenReturn(Optional.of(account));

        // Act
        List<CustomerResponseDto> result = customerService.getCustomers();

        // Assert
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Cliente 1");
    }

    @Test
    void getCustomers_Empty() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<CustomerResponseDto> result = customerService.getCustomers();

        // Assert
        assertNotNull(result);
        assertThat(result).isEmpty();
    }

    @Test
    void createCustomer() {
        // Arrange
        CustomerRequestDto dto = buildCustomerRequestDto();

        when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumber(dto.getMobileNumber())).thenReturn(Optional.empty());
        when(customerRepository.findByDocumentNumber(dto.getDocumentNumber())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(buildCustomer());

        // Act & Assert
        assertDoesNotThrow(() -> customerService.createCustomer(dto));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_EmailAlreadyExists() {
        // Arrange
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(dto));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_MobileNumberAlreadyExists() {
        // Arrange
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumber(dto.getMobileNumber())).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(dto));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_DocumentNumberAlreadyExists() {
        // Arrange
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumber(dto.getMobileNumber())).thenReturn(Optional.empty());
        when(customerRepository.findByDocumentNumber(dto.getDocumentNumber())).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(dto));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomer() {
        // Arrange
        Long customerId = 1L;
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmailAndCustomerIdNot(dto.getEmail(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumberAndCustomerIdNot(dto.getMobileNumber(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByDocumentNumberAndCustomerIdNot(dto.getDocumentNumber(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        boolean result = customerService.updateCustomer(dto, customerId);

        // Assert
        assertTrue(result);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_EmailAlreadyExists() {
        // Arrange
        Long customerId = 1L;
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmailAndCustomerIdNot(dto.getEmail(), customerId)).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.updateCustomer(dto, customerId));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomer_MobileNumberAlreadyExists() {
        // Arrange
        Long customerId = 1L;
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmailAndCustomerIdNot(dto.getEmail(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumberAndCustomerIdNot(dto.getMobileNumber(), customerId)).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.updateCustomer(dto, customerId));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomer_DocumentNumberAlreadyExists() {
        // Arrange
        Long customerId = 1L;
        CustomerRequestDto dto = buildCustomerRequestDto();
        Customer customer = buildCustomer();

        when(customerRepository.findByEmailAndCustomerIdNot(dto.getEmail(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumberAndCustomerIdNot(dto.getMobileNumber(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByDocumentNumberAndCustomerIdNot(dto.getDocumentNumber(), customerId)).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.updateCustomer(dto, customerId));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomer_CustomerNotFound() {
        // Arrange
        Long customerId = 999L;
        CustomerRequestDto dto = buildCustomerRequestDto();

        when(customerRepository.findByEmailAndCustomerIdNot(dto.getEmail(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByMobileNumberAndCustomerIdNot(dto.getMobileNumber(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findByDocumentNumberAndCustomerIdNot(dto.getDocumentNumber(), customerId)).thenReturn(Optional.empty());
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(dto, customerId));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void deleteCustomer() {
        // Arrange
        Long customerId = 1L;
        Customer customer = buildCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        boolean result = customerService.deleteCustomer(customerId);

        // Assert
        assertTrue(result);
        verify(accountRepository, times(1)).deleteByCustomerId(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void deleteCustomer_CustomerNotFound() {
        // Arrange
        Long customerId = 999L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(customerId));
        verify(accountRepository, never()).deleteByCustomerId(customerId);
        verify(customerRepository, never()).deleteById(customerId);
    }
}

