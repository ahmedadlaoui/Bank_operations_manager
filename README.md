# 🏦 Bank Account Management & Transaction Monitoring

## Project Context
A Moroccan bank wants to digitalize the management of client accounts and transactions.  
The goal is to provide clients and account managers with a simple, reliable, and secure tool to manage bank accounts (current, savings, term deposit) and track all transactions (deposits, withdrawals, transfers), while ensuring precise monitoring and detailed statistics.

This project consists of a **Java console application** simulating bank account management and transactions, following **object-oriented best practices**, using **collections**, **Java Time API**, and **Streams**.

---

## Main Features

### For Clients
- View personal information and bank accounts  
- Display transaction history (deposit, withdrawal, transfer)  
- Filter and sort transactions by type, amount, or date  
- Calculate total balance and total deposited/withdrawn amounts  

### For Account Managers
- Create, modify, or delete a client and their accounts  
- Add, modify, or delete transactions:  
  - Deposits and withdrawals: for all accounts  
  - Transfers: only if the account has sufficient balance  
- View and filter transactions by account or client  
- Automatically calculate balances and totals by client and transaction type  
- Identify unusual or suspicious transactions (high amounts, repeated operations)  

---

## Business Rules
- Each client can have multiple bank accounts  
- Each account belongs to a single client  
- An account can have multiple transactions  
- Transfers only allowed if the account has sufficient balance  
- Exception handling:  
  - Negative amount → `IllegalArgumentException`  
  - Client or account not found → `NoSuchElementException`  
  - Invalid transaction → `IllegalStateException`  
  - Insufficient balance for a transfer → `ArithmeticException`  

---

## Entity Modeling (OOP / UML-ready)
- **Person (abstract class):** name, surname, email, password  
- **Client:** clientId, accounts (`ArrayList<Account>`)  
- **Manager:** managerId, department, clients (`ArrayList<Client>`)  
- **Account:** accountId, accountType (Enum), balance, transactions (`ArrayList<Transaction>`), client  
- **Transaction:** transactionId, transactionType (DEPOSIT, WITHDRAWAL, TRANSFER), amount, date, reason, sourceAccount, destinationAccount (optional for transfers)  
- **Enums:**  
  - AccountType: CURRENT, SAVINGS, TERM_DEPOSIT  
  - TransactionType: DEPOSIT, WITHDRAWAL, TRANSFER  

---

## Technical Architecture (MVC)
- **Model:** Classes for Client, Account, Transaction with attributes and business methods  
- **View:** Console interface for menus and display  
- **Controller:** Handles user interactions, validation, and business rule enforcement  
- **Service:** Contains advanced business logic for account operations, reports, and centralizes rules to avoid duplication  

**Additional Features:**
- Collections: List, Map to store clients, accounts, and transactions  
- Streams & Lambdas: filtering, sorting, aggregation  
- Functional Interfaces: Predicate, Function, Consumer, Supplier  
- Optional for handling missing values  
- Java Time API for transaction dates  

---

## User Stories

### For Clients
- As a client, I want to view my personal information and accounts  
- As a client, I want to see my full transaction history  
- As a client, I want to filter and sort transactions by type, amount, or date  
- As a client, I want to calculate my total balance and total deposited/withdrawn amounts  

### For Managers
- As a manager, I want to create, modify, or delete a client and their accounts  
- As a manager, I want to add, modify, or delete transactions for an account  
- As a manager, I want to view and filter client transactions  
- As a manager, I want to identify suspicious transactions  
