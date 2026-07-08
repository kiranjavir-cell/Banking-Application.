package com.bank.controller;

import com.bank.dto.RegisterRequest;
import com.bank.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.dto.LoginRequest;
import com.bank.dto.CreateAccountRequest;
import com.bank.dto.DepositRequest;
import com.bank.dto.WithdrawRequest;
import com.bank.dto.TransferRequest;
import org.springframework.http.ResponseEntity;
import com.bank.entity.Customer;
import com.bank.entity.Account;
import java.util.List;
import com.bank.entity.Transaction;
import java.util.List;




@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public String registerCustomer(@Valid @RequestBody RegisterRequest request) {

        return customerService.registerCustomer(request);
    }

    @PostMapping("/login")
    public String loginCustomer(@Valid @RequestBody LoginRequest request) {

        return customerService.login(request);
    }

    @PostMapping("/create-account")
    public String createAccount(@Valid@RequestBody CreateAccountRequest request) {

        return customerService.createAccount(request);
    }

    @PostMapping("/deposit")
    public String deposit(@Valid@RequestBody DepositRequest request) {

        return customerService.deposit(request);
    }

    @PostMapping("/withdraw")
    public String withdraw(@Valid@RequestBody WithdrawRequest request) {

        return customerService.withdraw(request);
    }

    @GetMapping("/balance/{accountNumber}")
    public Double getBalance(@PathVariable String accountNumber) {

        return customerService.getBalance(accountNumber);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @Valid@RequestBody TransferRequest request) {

        return ResponseEntity.ok(
                customerService.transfer(request)
        );
    }

    @GetMapping("/search/customer/{customerId}")
    public Customer searchCustomer(
            @PathVariable Long customerId) {

        return customerService.searchCustomer(customerId);
    }

    @GetMapping("/search/account/{accountNumber}")
    public Account searchAccount(
            @PathVariable String accountNumber) {

        return customerService.searchAccount(accountNumber);
    }

    @GetMapping("/transactions/{accountNumber}")
    public List<Transaction> getTransactions(
            @PathVariable String accountNumber) {

        return customerService.getTransactions(accountNumber);
    }

}