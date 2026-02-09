package com.fmattaperdomo.accounts.repository;

import com.fmattaperdomo.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerIdAndAccountStatus(Long customerId, String accountStatus);
    List<Account> findByCustomerId(Long customerId);
    List<Account> findByBranchName(String branchName);
    List<Account> findByAccountType(String accountType);
    List<Account> findByAccountStatus(String accountStatus);

    @Transactional(readOnly = false)
    @Modifying
    void deleteByCustomerId(Long customerId);
}
