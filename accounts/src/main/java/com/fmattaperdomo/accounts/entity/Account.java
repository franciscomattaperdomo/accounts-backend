package com.fmattaperdomo.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "accounts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long accountId;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="account_number")
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="account_status")
    private String accountStatus;
}