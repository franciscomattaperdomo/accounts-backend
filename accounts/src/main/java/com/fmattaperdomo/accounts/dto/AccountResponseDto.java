package com.fmattaperdomo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "AccountResponse",
        description = "Schema to hold account response information"
)
public class AccountResponseDto {
    @Schema(
            description = "Account ID in the response"
    )
    private Long accountId;
    @Schema(
            description = "Customer ID in the response"
    )
    private Long customerId;
    @Schema(
            description = "Account number in the response"
    )
    private Long accountNumber;
    @Schema(
            description = "Account type in the response"
    )
    private String accountType;
    @Schema(
            description = "Branch address in the response"
    )
    private String branchAddress;
    @Schema(
            description = "Account status in the response"
    )
    private String accountStatus;
}
