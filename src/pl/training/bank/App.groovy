package pl.training.bank

import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer
import pl.training.bank.service.AccountNumberGenerator
import pl.training.bank.service.Accounts
import pl.training.bank.service.AccountsRepository
import pl.training.bank.service.AccountsService
import pl.training.bank.service.HashMapAccountsRepository
import pl.training.bank.service.TransactionLogger

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
        /*------------------------------------------------------------------------------------------------------------*/

        def customerOne = new Customer(firstName: 'Jan', lastName: 'Kowalski')
        def customerTwo = new Customer(firstName: 'Marek', lastName: 'Nowak')
        def account = new Account(number: '1234567891011101234562')
        def accountOne = accounts.createAccount(customerOne)
        def accountTwo = accounts.createAccount(customerTwo)

        accounts.deposit(accountOne.number, 400)
        accounts.deposit(accountTwo.number, 400)
        accounts.transfer(accountOne.number, accountTwo.number, 200)


//        def accounts = [accountOne, accountTwo]
//        println accounts*.balance



//        account.addCustomer(customerOne) //to samo co account addCustomer(customerOne)
//        account.deposit(3000L) //to samo co account addCustomer(customerOne)
//        account.withdraw(200G) //to samo co account addCustomer(customerOne)
//
//        println(account.balance)
//        println(account.getCustomers())

        //println(logger)

    }
}
