package pl.training.bank

import groovy.json.JsonSlurper
import pl.training.bank.accounts.Account
import pl.training.bank.customers.Customer
import pl.training.bank.accounts.AccountNumberGenerator
import pl.training.bank.accounts.AccountsRepository
import pl.training.bank.accounts.AccountsService
import pl.training.bank.accounts.HashMapAccountsRepository
import pl.training.bank.utils.Observer
import pl.training.bank.accounts.AccountsTransactionLogger

class App {

    //insert psvm :) public jest zbędny, w świecie Groovy wszystko domyślnie jest public
    static void main(String[] args) {

        //new Account().setBalance(22G).setId(33L)

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
        AccountsService accountsService = new AccountsService(accountNumberGenerator: accountNumberGenerator,
                accountsRepository: accountsRepository)
        AccountsTransactionLogger accounts = new AccountsTransactionLogger(accounts: accountsService)


        accountsService.addObserver {
            println "Deposit limit: ${it.number}" //równoważna postać dla ${it.number} to użycie ${account.number}"
        }

        accountsService.addObserver(new Observer() {

            @Override
            void update(Object event) {
                println("Alert")
            }
        })

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

        //zapis do pliku json
        accountsService.exportToFile('data.json')

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