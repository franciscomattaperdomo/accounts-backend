package com.fmattaperdomo.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "customers")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Customer extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long customerId;
    @Column(name="document_type")
    private String documentType;
    @Column(name="document_number")
    private String documentNumber;
    private String name;
    private String email;
    @Column(name="mobile_number")
    private String mobileNumber;
}