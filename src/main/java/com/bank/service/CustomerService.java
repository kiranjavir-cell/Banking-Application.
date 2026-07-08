package com.bank.service;


import com.bank.dto.RegisterRequest;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.dto.LoginRequest;
import java.time.LocalDateTime;
import com.bank.repository.AccountRepository;
import com.bank.dto.CreateAccountRequest;
import com.bank.entity.Account;
import com.bank.dto.DepositRequest;
import com.bank.dto.WithdrawRequest;
import com.bank.dto.TransferRequest;
import com.bank.repository.TransactionRepository;
import com.bank.entity.Transaction;
import java.util.List;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.CustomerNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bank.security.JwtUtil;



@Service
public class CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerCustomer(RegisterRequest request) {

        Customer customer = new Customer();

        customer.setCustomerName(request.getCustomerName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setMobileNumber(request.getMobileNumber());

        customer.setRole("CUSTOMER");
        customer.setCreatedAt(LocalDateTime.now());

        customerRepository.save(customer);

        return "Customer Registered Successfully";


    }

    public String login(LoginRequest request) {

        Customer customer = customerRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (customer == null) {
            return "Customer not found";
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                customer.getPassword())) {

            return "Invalid password";
        }
        String token = jwtUtil.generateToken(customer.getEmail());

        return token;

    }

    public String createAccount(CreateAccountRequest request) {

        Account account = new Account();

        account.setCustomerId(request.getCustomerId());

        account.setAccountType(request.getAccountType());

        account.setAccountNumber(
                "ACC" + System.currentTimeMillis()
        );

        account.setBalance(0.0);

        account.setCreatedAt(LocalDateTime.now());

        accountRepository.save(account);

        return "Account Created Successfully";
    }

    public String deposit(DepositRequest request) {

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found: " + request.getAccountNumber()
                        ));


        account.setBalance(
                account.getBalance() + request.getAmount()
        );

        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setRemarks("Amount Deposited");
        transactionRepository.save(transaction);

        return "Amount Deposited Successfully";
    }

    public String withdraw(WithdrawRequest request) {

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found: " + request.getAccountNumber()
                        ));


        if(account.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException(
                    "Insufficient Balance"
            );
        }

        account.setBalance(
                account.getBalance() - request.getAmount()
        );

        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setRemarks("Cash Withdrawal");
        transactionRepository.save(transaction);

        return "Amount Withdrawn Successfully";
    }

    public Double getBalance(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElse(null);

        if (account == null) {
            return null;
        }

        return account.getBalance();
    }

    public String transfer(TransferRequest request) {

        Account sender = accountRepository
                .findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Sender account not found"
                        ));



        Account receiver = accountRepository
                .findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Receiver account not found"
                        ));



        if (sender.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException(
                    "Insufficient Balance"
            );
        }
        sender.setBalance(
                sender.getBalance() - request.getAmount()
        );

        receiver.setBalance(
                receiver.getBalance() + request.getAmount()
        );

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = new Transaction();

        transaction.setAccountNumber(sender.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setRemarks(
                "Transferred to " + receiver.getAccountNumber()
        );
        transactionRepository.save(transaction);

        return "Fund Transfer Successful";
    }

    public Customer searchCustomer(Long customerId) {

        return customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with ID: " + customerId
                        ));
    }
    public Account searchAccount(String accountNumber) {

        return accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found: " + accountNumber
                        ));
    }

    public List<Transaction> getTransactions(String accountNumber) {

        return transactionRepository.findByAccountNumber(accountNumber);
    }

}