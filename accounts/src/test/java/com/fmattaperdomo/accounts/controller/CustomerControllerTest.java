package com.fmattaperdomo.accounts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.fmattaperdomo.accounts.constant.CustomerConstant;
import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.CustomerRequestDto;
import com.fmattaperdomo.accounts.dto.CustomerResponseDto;
import com.fmattaperdomo.accounts.dto.ResponseDto;
import com.fmattaperdomo.accounts.entity.Customer;
import com.fmattaperdomo.accounts.mapper.CustomerMapper;
import com.fmattaperdomo.accounts.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @InjectMocks
    CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private CustomerResponseDto buildCustomerResponseDto() {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setCustomerId(1L);
        customerResponseDto.setDocumentType("CC");
        customerResponseDto.setDocumentNumber("11122233");
        customerResponseDto.setName("Cliente 1");
        customerResponseDto.setEmail("cliente1@correo.com");
        customerResponseDto.setMobileNumber("1112223333");

        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setCustomerId(1L);
        accountResponseDto.setAccountNumber(1234567890L);
        accountResponseDto.setAccountType("Savings");
        accountResponseDto.setBranchName("Main Branch");
        accountResponseDto.setAccountStatus("Active");
        accountResponseDto.setCustomerName("Cliente 1");

        customerResponseDto.setAccountResponseDto(accountResponseDto);
        return customerResponseDto;
    }

    @Test
    void createCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setDocumentType("CC");
        customer.setDocumentNumber("11122233");
        customer.setName("Cliente 1");
        customer.setEmail("cliente1@correo.com");
        customer.setMobileNumber("1112223333");

        CustomerRequestDto customerRequestDto = CustomerMapper.mapToCustomerRequestDto(customer, new CustomerRequestDto());

        doNothing().when(customerService).createCustomer(any(CustomerRequestDto.class));

        ResponseEntity<ResponseDto> response = customerController.createCustomer(customerRequestDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(CustomerConstant.STATUS_201);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(CustomerConstant.MESSAGE_201);
    }

    @Test
    void getCustomerById() {
        Long customerId = 1L;
        CustomerResponseDto customerResponseDto = buildCustomerResponseDto();
        when(customerService.getCustomerById(anyLong())).thenReturn(customerResponseDto);

        ResponseEntity<CustomerResponseDto> response = customerController.getCustomerById(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Cliente 1");
        assertThat(response.getBody().getEmail()).isEqualTo("cliente1@correo.com");
        assertThat(response.getBody().getMobileNumber()).isEqualTo("1112223333");
        assertThat(response.getBody().getAccountResponseDto()).isNotNull();
    }

    @Test
    void getCustomerByMobileNumber() {
        String mobileNumber = "1112223333";
        CustomerResponseDto customerResponseDto = buildCustomerResponseDto();
        when(customerService.getCustomerByMobileNumber("1112223333")).thenReturn(customerResponseDto);

        ResponseEntity<CustomerResponseDto> response = customerController.getCustomerByMobileNumber(mobileNumber);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().getMobileNumber()).isEqualTo("1112223333");
        assertThat(response.getBody().getName()).isEqualTo("Cliente 1");
    }

    @Test
    void getCustomerByEmail() {
        String email = "cliente1@correo.com";
        CustomerResponseDto customerResponseDto = buildCustomerResponseDto();
        when(customerService.getCustomerByEmail("cliente1@correo.com")).thenReturn(customerResponseDto);

        ResponseEntity<CustomerResponseDto> response = customerController.getCustomerByEmail(email);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().getEmail()).isEqualTo("cliente1@correo.com");
        assertThat(response.getBody().getName()).isEqualTo("Cliente 1");
    }

    @Test
    void getCustomer() {
        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        CustomerResponseDto customerResponseDto = buildCustomerResponseDto();
        customerResponseDtoList.add(customerResponseDto);
        when(customerService.getCustomers()).thenReturn(customerResponseDtoList);

        ResponseEntity<List<CustomerResponseDto>> response = customerController.getCustomer();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getCustomerId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Cliente 1");
        assertThat(response.getBody().get(0).getEmail()).isEqualTo("cliente1@correo.com");
        assertThat(response.getBody().get(0).getMobileNumber()).isEqualTo("1112223333");
        assertThat(response.getBody().get(0).getAccountResponseDto()).isNotNull();
    }

    @Test
    void updateCustomer() {
        Long customerId = 1L;
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setDocumentType("CC");
        customerRequestDto.setDocumentNumber("11122233");
        customerRequestDto.setName("Cliente 1 Actualizado");
        customerRequestDto.setEmail("cliente1@correo.com");
        customerRequestDto.setMobileNumber("1112223333");

        when(customerService.updateCustomer(any(CustomerRequestDto.class), anyLong())).thenReturn(true);

        ResponseEntity<ResponseDto> response = customerController.updateCustomer(customerRequestDto, customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(CustomerConstant.STATUS_200);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(CustomerConstant.MESSAGE_200);
    }

    @Test
    void updateCustomerFailure() {
        Long customerId = 1L;
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setDocumentType("CC");
        customerRequestDto.setDocumentNumber("11122233");
        customerRequestDto.setName("Cliente 1");
        customerRequestDto.setEmail("cliente1@correo.com");
        customerRequestDto.setMobileNumber("1112223333");

        when(customerService.updateCustomer(any(CustomerRequestDto.class), anyLong())).thenReturn(false);

        ResponseEntity<ResponseDto> response = customerController.updateCustomer(customerRequestDto, customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(CustomerConstant.STATUS_417);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(CustomerConstant.MESSAGE_417_UPDATE);
    }

    @Test
    void deleteCustomer() {
        Long customerId = 1L;
        when(customerService.deleteCustomer(anyLong())).thenReturn(true);

        ResponseEntity<ResponseDto> response = customerController.deleteCustomer(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(CustomerConstant.STATUS_200);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(CustomerConstant.MESSAGE_200);
    }

    @Test
    void deleteCustomerFailure() {
        Long customerId = 1L;
        when(customerService.deleteCustomer(anyLong())).thenReturn(false);

        ResponseEntity<ResponseDto> response = customerController.deleteCustomer(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.EXPECTATION_FAILED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatusCode()).isEqualTo(CustomerConstant.STATUS_417);
        assertThat(response.getBody().getStatusMsg()).isEqualTo(CustomerConstant.MESSAGE_417_DELETE);
    }
}