# 🏦 Bank Account Management App

## Project Overview
A Java console application to manage bank accounts and transactions for a Moroccan bank.  
It allows clients to view their accounts and transaction history, and managers to manage clients, accounts, and transactions while tracking suspicious activity.

---

## Features
- View client accounts and transaction history  
- Filter and sort transactions by type, amount, or date  
- Create, modify, or delete clients and accounts  
- Add, modify, or delete transactions (deposit, withdrawal, transfer)  
- Automatic balance calculation and totals  
- Detect unusual or suspicious transactions  

---

## Technical Details
- Java 17 (records, sealed classes, Streams, Optional)  
- Console-based interface (UI)  
- Layered MVC architecture (Model, View, Controller, Service)  
- Collections (List, Map), Streams & Lambdas, Java Time API  

---

## Entities
- Client, Manager, Account, Transaction  
- Enums for AccountType (CURRENT, SAVINGS, TERM_DEPOSIT) and TransactionType (DEPOSIT, WITHDRAWAL, TRANSFER)  

---

## License
MIT License
