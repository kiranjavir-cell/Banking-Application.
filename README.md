# 🏦 Banking Management System

A Spring Boot based Banking Management System that provides secure customer registration, authentication using JWT, bank account management, and transaction processing. The project is designed using layered architecture and follows RESTful API principles.

---

## 📌 Project Overview

The Banking Management System allows customers to:

- Register securely
- Login using JWT Authentication
- Create bank accounts
- Deposit money
- Withdraw money
- Transfer funds
- Check account balance
- Search customers
- Search bank accounts
- View transaction history

The project follows a clean architecture using Controller, Service, Repository, DTO, Entity, Exception Handling, and Security layers.

---

## 🚀 Features

### Authentication
- Customer Registration
- Customer Login
- JWT Token Generation
- Secure API Access

### Customer Management
- Register Customer
- Search Customer
- Customer Details

### Account Management
- Create Bank Account
- Search Bank Account
- Balance Inquiry

### Transaction Management
- Deposit Money
- Withdraw Money
- Fund Transfer
- Transaction History

### Security
- Spring Security
- JWT Authentication
- Password Encryption (BCrypt)

### Exception Handling
- Global Exception Handler
- Custom Exceptions

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT Authentication
- Hibernate
- Maven

## Database

- MySQL

## API Testing

- Postman

## Build Tool

- Maven

## IDE

- IntelliJ IDEA

## Version Control

- Git
- GitHub

---

# 📂 Project Structure

```
src
│
├── controller
│      CustomerController
│
├── dto
│      RegisterRequest
│      LoginRequest
│      CreateAccountRequest
│      DepositRequest
│      WithdrawRequest
│      TransferRequest
│
├── entity
│      Customer
│      Account
│      Transaction
│
├── repository
│      CustomerRepository
│      AccountRepository
│      TransactionRepository
│
├── service
│      CustomerService
│
├── security
│      JwtUtil
│      JwtAuthenticationFilter
│      CustomUserDetailsService
│
├── exception
│      GlobalExceptionHandler
│      CustomerNotFoundException
│      AccountNotFoundException
│      InsufficientBalanceException
│
└── BankingSystemApplication
```

---

# 🔐 Authentication

The application uses JWT (JSON Web Token) authentication.

### Registration

```
POST /api/customers/register
```

### Login

```
POST /api/customers/login
```

Returns

```
JWT Token
```

Use the generated token for all secured APIs.

Example Header

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📖 REST APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /api/customers/register | Register Customer |
| POST | /api/customers/login | Customer Login |
| POST | /api/customers/create-account | Create Bank Account |
| POST | /api/customers/deposit | Deposit Money |
| POST | /api/customers/withdraw | Withdraw Money |
| POST | /api/customers/transfer | Fund Transfer |
| GET | /api/customers/balance/{accountNumber} | Check Balance |
| GET | /api/customers/search/customer/{customerId} | Search Customer |
| GET | /api/customers/search/account/{accountNumber} | Search Account |
| GET | /api/customers/transactions/{accountNumber} | Transaction History |

---

# ⚙ Database

MySQL Database

```
Database Name:
banking_system
```

Configure database credentials inside

```
application.properties
```

# 👨‍💻 Author

**Kiran Javir**

Java Full Stack Developer

