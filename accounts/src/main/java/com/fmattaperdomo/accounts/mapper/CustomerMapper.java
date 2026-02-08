package com.fmattaperdomo.accounts.mapper;

import com.fmattaperdomo.accounts.dto.AccountResponseDto;
import com.fmattaperdomo.accounts.dto.CustomerRequestDto;
import com.fmattaperdomo.accounts.dto.CustomerResponseDto;
import com.fmattaperdomo.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerRequestDto mapToCustomerRequestDto(Customer customer, CustomerRequestDto customerRequestDto) {
        customerRequestDto.setDocumentType(customer.getDocumentType());
        customerRequestDto.setDocumentNumber(customer.getDocumentNumber());
        customerRequestDto.setName(customer.getName());
        customerRequestDto.setEmail(customer.getEmail());
        customerRequestDto.setMobileNumber(customer.getMobileNumber());
        return customerRequestDto;
    }
    public static Customer mapToCustomerRequest(CustomerRequestDto customerRequestDto, Customer customer) {
        customer.setDocumentType(customerRequestDto.getDocumentType());
        customer.setDocumentNumber(customerRequestDto.getDocumentNumber());
        customer.setName(customerRequestDto.getName());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setMobileNumber(customerRequestDto.getMobileNumber());
        return customer;
    }
    public static CustomerResponseDto mapToCustomerResponseDto(Customer customer, CustomerResponseDto customerResponseDto, AccountResponseDto accountResponseDto) {
        customerResponseDto.setCustomerId(customer.getCustomerId());
        customerResponseDto.setDocumentType(customer.getDocumentType());
        customerResponseDto.setDocumentNumber(customer.getDocumentNumber());
        customerResponseDto.setName(customer.getName());
        customerResponseDto.setEmail(customer.getEmail());
        customerResponseDto.setMobileNumber(customer.getMobileNumber());
        customerResponseDto.setAccountResponseDto(accountResponseDto);
        return customerResponseDto;
    }
    public static Customer mapToCustomerResponse(CustomerResponseDto customerResponseDto, Customer customer) {
        customer.setCustomerId(customerResponseDto.getCustomerId());
        customer.setDocumentType(customerResponseDto.getDocumentType());
        customer.setDocumentNumber(customerResponseDto.getDocumentNumber());
        customer.setName(customerResponseDto.getName());
        customer.setEmail(customerResponseDto.getEmail());
        customer.setMobileNumber(customerResponseDto.getMobileNumber());
        return customer;
    }
}