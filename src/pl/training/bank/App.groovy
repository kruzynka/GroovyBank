package pl.training.bank

import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer
import pl.training.bank.service.AccountNumberGenerator
import pl.training.bank.service.AccountsRepository
import pl.training.bank.service.AccountsService
import pl.training.bank.service.HashMapAccountsRepository
import pl.training.bank.utils.Observer
import pl.training.bank.service.TransactionLogger

import java.util.function.LongToDoubleFunction

class App {

    //insert psvm :) public jest zbędny, w świecie Groovy wszystko domyślnie jest public
    static void main(String[] args) {

        /**
         * utworz nową insancję, nowego typu
         * operator new nie jest słuszny, ponieważ wprowadza z góry narzucone powiązanie
         */
        AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator()
        //dawniej AccountsRepository(), ale dodalismy interfejs, więc teraz korzystamy z HashMapAccountsRepository:
        AccountsRepository accountsRepository = new HashMapAccountsRepository()
        /**
         * zamieniamy AccountsService accountsService = new AccountsService przez
         * Accounts accountsService = new AccountsService
         * bo wdrożyliśmy interface
         */
        AccountsService accountsService = new AccountsService(
                accountNumberGenerator: accountNumberGenerator,
                accountsRepository: accountsRepository)
        TransactionLogger accounts = new TransactionLogger(accounts: accountsService)

        /*accountsService.addObserver {
            //równoważna postać println "Deposit limit: ${account.number}"
            println "Deposit limit: ${it.number}"
        }*/

        /*powyższą część można zastąpić poprzed FileLimitLogger*/
        accountsService.addObserver(new FileLimitLogger())

        /*------------------------------------------------------------------------------------------------------------*/

        def customerOne = new Customer(firstName: 'Jan', lastName: 'Kowalski')
        def customerTwo = new Customer(firstName: 'Marek', lastName: 'Nowak')
        def account = new Account(number: '1234567891011101234562')
        def accountOne = accounts.createAccount(customerOne)
        def accountTwo = accounts.createAccount(customerTwo)

        accounts.deposit(accountOne.number, 300)
        accounts.deposit(accountTwo.number, 400)
        accounts.transfer(accountOne.number, accountTwo.number, 200)

        accounts.deposit(accountOne.number, 20_001)
        accounts.deposit(accountOne.number, 19_000)

        //dokonujemy tranferu z pierwzego konta na drugie
        def transferFromAccountOne = accounts.&transfer.curry(accountOne.number)
        transferFromAccountOne(accountTwo.number, 30)


    }

}

/**
 * Powiadomienie do pliku
 */
class FileLimitLogger implements Observer<Account> {

    @Override
    void update(Account account) {
        new File('logs.txt').withWriter('utf-8') {
            writer -> writer.writeLine "Deposit limit on account: ${account.number}"
        }
    }
}

/*
Uogólniona forma interejsu:

interface Converter<Source, Destination> {
    Destination convert(Source source)
}

class StringToLongConverter implements Converter<String, Long> {

    @Override
    Long convert(String s) {
        return text as Long
    }
}

class Writer<Output extends OutputStream> {

    private Output output

    void write(byte[] bytes) {
        output.write(bytes)
    }
}

new Writer<PrintWriter>()
 */