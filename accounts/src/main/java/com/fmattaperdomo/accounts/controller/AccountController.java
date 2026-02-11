package com.fmattaperdomo.accounts.controller;

import com.fmattaperdomo.accounts.constant.AccountConstant;
import com.fmattaperdomo.accounts.dto.*;
import com.fmattaperdomo.accounts.service.AccountService;
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

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Accounts",
        description = "CRUD REST APIs in Account to CREATE, UPDATE, GET AND DELETE account details"
)
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {
    private AccountService accountService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Account"
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
    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountCreateRequestDto accountCreateRequestDto) {
        accountService.createAccount(accountCreateRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch accounts  based on a customer id"
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
    @GetMapping("/accounts/customerId/{customerId}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByCustomerId(@PathVariable
                                                           Long customerId) {
        List<AccountResponseDto> accountsResponseDto = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsResponseDto);
    }
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch accounts  based on a branch name"
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
    @GetMapping("/accounts/branchName/{branchName}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByBranchName(@PathVariable
                                                                           String branchName) {
        List<AccountResponseDto> accountsResponseDto = accountService.getAccountsByBranchName(branchName);
        return ResponseEntity.status(HttpStatus.OK).body(accountsResponseDto);
    }
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch accounts  based on a account type"
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
    @GetMapping("/accounts/accountType/{accountType}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByAccountType(@PathVariable
                                                                            String accountType) {
        List<AccountResponseDto> accountsResponseDto = accountService.getAccountsByAccountType(accountType);
        return ResponseEntity.status(HttpStatus.OK).body(accountsResponseDto);
    }
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch accounts  based on a account type"
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
    @GetMapping("/accounts/accountStatus/{accountStatus}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByAccountStatus(@PathVariable
                                                                             String accountStatus) {
        List<AccountResponseDto> accountsResponseDto = accountService.getAccountsByAccountStatus(accountStatus);
        return ResponseEntity.status(HttpStatus.OK).body(accountsResponseDto);
    }
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Account  based on a customer id"
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
    @GetMapping("/accounts")
    public ResponseEntity<AccountResponseDto> getAccountByCustomerId(@RequestParam
                                                                      Long customerId) {
        AccountResponseDto accountResponseDto = accountService.getAccountByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponseDto);
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Accounts  "
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
    @GetMapping("/accounts/all")
    public ResponseEntity<List<AccountResponseDto>> getAccounts() {
        List<AccountResponseDto> accountsResponseDto = accountService.getAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(accountsResponseDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Account details based on a account id"
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
    }
    )
    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody AccountUpdateRequestDto accountUpdateRequestDto, @PathVariable
    Long accountId) {
        boolean isUpdated = accountService.updateAccount(accountUpdateRequestDto,accountId);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstant.STATUS_417, AccountConstant.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Delete Account  Details REST API",
            description = "REST API to delete Account details based on a account id"
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
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable
                                                            Long accountId) {
        boolean isDeleted = accountService.deleteAccount(accountId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstant.STATUS_417, AccountConstant.MESSAGE_417_DELETE));
        }
    }
}
