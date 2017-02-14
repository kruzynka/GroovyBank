package pl.training.bank.service

import pl.training.bank.BankException
import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer

import javax.swing.text.NumberFormatter
import java.text.NumberFormat

/**
 * TransactionLogger musi udawać Accounts, więc musimy robić implementacjewszystkich metod
 */
class TransactionLogger implements Accounts {

    //definiuje statyczny separator
    private static final String SEPARATOR = "#" * 20
    private static final String SUCCESS_MESSAGE = "Status: Success"
    // w momencie użycia będzie widział ex, więc musi być przekazany przez -> :
    //private static final String EXCEPTION_MESSAGE = "Status: Failure (${ -> ex.class.simpleName})"
    //currencyInstance weźmie z ustawień systemowych
    private formatter = NumberFormat.currencyInstance

    //dodaje interface, który implementuje
    private Accounts accounts

    @Override
    Account createAccount(Customer customer) {
        Account account = accounts.createAccount(customer)
        println("New account: ${account.getNumber()}")
        return account
    }

    @Override
    void deposit(String accountNumber, BigDecimal funds) {
        try {
        accounts.deposit(accountNumber, funds)
            println("$accountNumber <= ${formatter.format((funds))}")
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${ -> ex.class.simpleName})")
        }
        println(SEPARATOR)
    }

    @Override
    void withdraw(String accountNumber, BigDecimal funds) {
        try {
            accounts.withdraw(accountNumber, funds)
            println("$accountNumber => ${formatter.format((funds))}")
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${ -> ex.class.simpleName})")
        }
        println(SEPARATOR)
    }

    @Override
    void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal funds) {
        try {
            accounts.transfer(sourceAccountNumber, destinationAccountNumber, funds)
            println("$sourceAccountNumber => ${formatter.format((funds))} =>  $destinationAccountNumber")
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${ -> ex.class.simpleName})")
        }
        println(SEPARATOR)
    }
}
