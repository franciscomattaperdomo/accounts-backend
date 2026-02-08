package com.fmattaperdomo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CustomerResponse",
        description = "Schema to hold customer response information"
)
public class CustomerResponseDto {
    @Schema(
            description = "Customer ID in the response"
    )
    private Long customerId;
    @Schema(
            description = "Document type in the response"
    )
    private String documentType;
    @Schema(
            description = "Document number in the response"
    )
    private String documentNumber;
    @Schema(
            description = "Name of the customer in the response"
    )
    private String name;
    @Schema(
            description = "Email address of the customer in the response"
    )
    private String email;
    @Schema(
            description = "Mobile Number of the customer"
    )
    private String mobileNumber;
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountResponseDto accountResponseDto;
}