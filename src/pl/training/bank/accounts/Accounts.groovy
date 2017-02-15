package pl.training.bank.accounts

import pl.training.bank.customers.Customer

interface Accounts {

    Account createAccount(Customer customer)

    void deposit(String accountNumber, BigDecimal funds)

    void withdraw(String accountNumber, BigDecimal funds)

    void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal funds)

}