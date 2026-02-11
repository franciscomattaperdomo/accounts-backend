package com.fmattaperdomo.accounts.controller;

import com.fmattaperdomo.accounts.constant.CustomerConstant;
import com.fmattaperdomo.accounts.dto.*;
import com.fmattaperdomo.accounts.service.CustomerService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Customers",
        description = "CRUD REsEST APIs in Customer to CREATE, UPDATE, GET AND DELETE customer details"
)
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CustomerController {
    private CustomerService customerService;

    @Operation(
            summary = "Create Customer REST API",
            description = "REST API to create new Customer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/customers")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        customerService.createCustomer(customerRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CustomerConstant.STATUS_201, CustomerConstant.MESSAGE_201));
    }
    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer  based on a customer id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable
                                                                            Long customerId) {
        CustomerResponseDto customerResponseDto = customerService.getCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDto);
    }
    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer  based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/customers/mobileNumber/{mobileNumber}")
    public ResponseEntity<List<CustomerResponseDto>> getCustomersByMobileNumber(@PathVariable
                                                                             String mobileNumber) {
        List<CustomerResponseDto> customersResponseDto = customerService.getCustomersByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customersResponseDto);
    }
    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer  based on a email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/customers/email/{email}")
    public ResponseEntity<List<CustomerResponseDto>> getCustomersByEmail(@PathVariable
                                                                  String email) {
        List<CustomerResponseDto> customersResponseDto = customerService.getCustomersByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(customersResponseDto);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/customers/name/{name}")
    public ResponseEntity<List<CustomerResponseDto>> getCustomersByName(@PathVariable
                                                                         String name) {
        List<CustomerResponseDto> customersResponseDto = customerService.getCustomersByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(customersResponseDto);
    }
    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customers  "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        List<CustomerResponseDto> customersResponseDto = customerService.getCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customersResponseDto);
    }
    @Operation(
            summary = "Update Customer Details REST API",
            description = "REST API to update Customer details based on a customer id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/customers/{customerId}")
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto, @PathVariable
    Long customerId) {
        boolean isUpdated = customerService.updateCustomer(customerRequestDto,customerId);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CustomerConstant.STATUS_200, CustomerConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CustomerConstant.STATUS_417, CustomerConstant.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Delete Customer  Details REST API",
            description = "REST API to delete Customer details based on a customer id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<ResponseDto> deleteCustomer(@PathVariable
                                                     Long customerId) {
        boolean isDeleted = customerService.deleteCustomer(customerId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CustomerConstant.STATUS_200, CustomerConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CustomerConstant.STATUS_417, CustomerConstant.MESSAGE_417_DELETE));
        }
    }
}
