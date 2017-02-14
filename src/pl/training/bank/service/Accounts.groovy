package pl.training.bank.service

import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer

interface Accounts {

    Account createAccount(Customer customer)

    void deposit(String accountNumber, BigDecimal funds)

    void withdraw(String accountNumber, BigDecimal funds)

    void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal funds)

}