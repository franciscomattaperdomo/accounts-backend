package com.fmattaperdomo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountRequestDto {
    @NotEmpty(message = "Account type can not be a null or empty")
    @Schema(
            description = "Account type", example = "Savings or checking"
    )
    private String accountType;
    @NotEmpty(message = "Branch address can not be a null or empty")
    @Schema(
            description = "Branch address", example = "123 NewYork"
    )
    private String branchAddress;
}
