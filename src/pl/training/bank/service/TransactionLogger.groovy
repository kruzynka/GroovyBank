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

    private process(Closure<Void> callback) {
        try {
            callback()
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${-> ex.class.simpleName})")
        }
        println(SEPARATOR)
    }

    @Override
    Account createAccount(Customer customer) {
        Account account = accounts.createAccount(customer)
        println("New account: ${account.getNumber()}")
        return account
    }

    /**
     * Przy użyciu process zastąpimy część wspólną dla trzech metod
     * @param accountNumber
     * @param funds
     */
    @Override
    void deposit(String accountNumber, BigDecimal funds) {

        /*możemy użyć process w ramach metody:*/
        /*Closure<Void> depositOperation = {
            accounts.deposit(accountNumber, funds)
            println("$accountNumber <= ${formatter.format((funds))}")
        }
        process {depositOperation}*/

        /*możemy użyć bezpośrednio process, gdzie process posiada swoją definicję poza metodą deposit:*/
        process {
            accounts.deposit(accountNumber, funds)
            println("$accountNumber <= ${formatter.format((funds))}")
        }

        /*try {
            accounts.deposit(accountNumber, funds)
            println("$accountNumber <= ${formatter.format((funds))}")
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${-> ex.class.simpleName})")
        }
        println(SEPARATOR)*/

    }

    @Override
    void withdraw(String accountNumber, BigDecimal funds) {
        process {
            accounts.withdraw(accountNumber, funds)
            println("$accountNumber => ${formatter.format((funds))}")
        }

        /*try {
            accounts.withdraw(accountNumber, funds)
            println("$accountNumber => ${formatter.format((funds))}")
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${ -> ex.class.simpleName})")
        }
        println(SEPARATOR)*/
    }

    @Override
    void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal funds) {
        process {
            accounts.transfer(sourceAccountNumber, destinationAccountNumber, funds)
            println("$sourceAccountNumber => ${formatter.format((funds))} =>  $destinationAccountNumber")
        }

        /*try {
            accounts.transfer(sourceAccountNumber, destinationAccountNumber, funds)
            println("$sourceAccountNumber => ${formatter.format((funds))} =>  $destinationAccountNumber")
            println(SUCCESS_MESSAGE)
        } catch (BankException ex) {
            println("Status: Failure (${ -> ex.class.simpleName})")
        }
        println(SEPARATOR)*/
    }
}
