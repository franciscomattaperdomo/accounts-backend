package com.fmattaperdomo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerRequestDto {
    @NotEmpty(message = "Document type can not be a null or empty")
    @Size(min = 2, max = 3, message = "The length of the document type should be between 2 and 3")
    @Schema(
            description = "Document type", example = "CC OR CE OR PAS"
    )
    private String documentType;
    @NotEmpty(message = "Document number can not be a null or empty")
    @Size(min = 8, max = 15, message = "The length of the document number should be between 8 and 15")
    @Schema(
            description = "Document number", example = "49555111"
    )
    private String documentNumber;
    @Schema(
            description = "Name of the customer", example = "Pepito Perez"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 100, message = "The length of the customer name should be between 5 and 100")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "pperez@correo.com"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;
    @Schema(
            description = "Mobile Number of the customer", example = "3204445555"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
}