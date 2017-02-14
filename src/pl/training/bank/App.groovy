package pl.training.bank

import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer
import pl.training.bank.service.AccountNumberGenerator
import pl.training.bank.service.AccountsRepository
import pl.training.bank.service.AccountsService

class App {

    //insert psvm :) public jest zbędny, w świecie Groovy wszystko jest public
    static void main(String[] args) {

        AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator()
        AccountsRepository accountsRepository = new AccountsRepository()
        AccountsService accountsService = new AccountsService(
                accountNumberGenerator: accountNumberGenerator,
                accountsRepository: accountsRepository)


        def customerOne = new Customer(firstName: 'Jan', lastName: 'Kowalski')
        def customerTwo = new Customer(firstName: 'Marek', lastName: 'Nowak')
        def account = new Account(number: '1234567891011101234562')
        def accountOne = accountsService.createAccount(customerOne)
        def accountTwo = accountsService.createAccount(customerTwo)


        accountsService.deposit(accountOne.number, 400)
        accountsService.deposit(accountTwo.number, 400)
        accountsService.transfer(accountOne.number, accountTwo.number, 200)

        def accounts = [accountOne, accountTwo]
        println accounts*.balance


//        account.addCustomer(customerOne) //to samo co account addCustomer(customerOne)
//        account.deposit(3000L) //to samo co account addCustomer(customerOne)
//        account.withdraw(200G) //to samo co account addCustomer(customerOne)
//
//        println(account.balance)
//        println(account.getCustomers())

    }
}
