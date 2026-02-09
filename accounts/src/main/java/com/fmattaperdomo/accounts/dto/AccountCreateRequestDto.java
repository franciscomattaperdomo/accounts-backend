package com.fmattaperdomo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountCreateRequestDto {
    @NotEmpty(message = "Customer ID can not be a null or empty")
    @Schema(
            description = "Customer Id", example = "1"
    )
    private Long customerId;
    @NotEmpty(message = "Account type can not be a null or empty")
    @Schema(
            description = "Account type", example = "Savings or checking"
    )
    private String accountType;
    @NotEmpty(message = "Branch name can not be a null or empty")
    @Size(min = 5, max = 100, message = "The length of the branch name should be between 5 and 100")
    @Schema(
            description = "Branch name", example = "Castellana"
    )
    private String branchName;
}
