package com.fmattaperdomo.accounts.repository;

import com.fmattaperdomo.accounts.entity.Account;
import com.fmattaperdomo.accounts.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByMobileNumberContainingIgnoreCase(String mobileNumber);
    List<Customer> findByDocumentNumberContainingIgnoreCase(String documentNumber);
    List<Customer> findByEmailContainingIgnoreCase(String email);
    List<Customer> findByNameContainingIgnoreCase(String name);

    Optional<Customer> findByMobileNumber(String mobileNumber);
    Optional<Customer> findByDocumentNumber(String documentNumber);
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobileNumberAndCustomerIdNot(String mobileNumber,Long customerId);
    Optional<Customer> findByDocumentNumberAndCustomerIdNot(String documentNumber,Long customerId);
    Optional<Customer> findByEmailAndCustomerIdNot(String email,Long customerId);
}