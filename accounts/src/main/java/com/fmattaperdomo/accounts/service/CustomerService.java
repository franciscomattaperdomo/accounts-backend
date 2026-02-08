package com.fmattaperdomo.accounts.service;

import com.fmattaperdomo.accounts.dto.CustomerRequestDto;
import com.fmattaperdomo.accounts.dto.CustomerResponseDto;

public interface CustomerService {
    /**
     *
     * @param mobileNumber - Input mobile number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerResponseDto getCustomerByMobileNumber(String mobileNumber);
    /**
     *
     * @param email - Input email
     * @return Customer Details based on a given email
     */
    CustomerResponseDto getCustomerByEmail(String email);
    /**
     *
     * @param customerId - Input customer ID
     * @return Customer Details based on a given email
     */
    CustomerResponseDto getCustomerById(Long customerId);
    /**
     *
     * @param customerRequestDto - CustomerRequestDto Object
     */
    void createCustomer(CustomerRequestDto customerRequestDto);
    /**
     *
     * @param customerRequestDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateCustomer(CustomerRequestDto customerRequestDto);
    /**
     *
     * @param customerId - Input customer ID
     * @return boolean indicating if the delete of customer details is successful or not
     */
    boolean deleteCustomer(Long customerId);
}
