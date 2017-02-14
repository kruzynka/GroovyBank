package pl.training.bank.service

import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer

class AccountsService {

    private AccountsRepository accountsRepository
    private AccountNumberGenerator accountNumberGenerator
    //nie robimy paskudztwa private AccountNumberGenerator accountNumberGenerator = new accountNumberGenerator

    //nie moze to byc jako void, tylko jako Account
    //przypiszemy kolejny numer konta, mozem równoważnie uzyc accountNumberGenerator.next:
    Account createAccount(Customer customer) {
        Account account = new Account(number: accountNumberGenerator.getNext())
        account.addCustomer(customer)
        accountsRepository.save(account)
        return account
    }

    /**
     * 1. parametr jest typowany, 2. nie jest typowany
     * @param accountNumber
     * @param founds
     */
    void deposit(String accountNumber, BigDecimal funds) {
        Account account = accountsRepository.getBy(accountNumber)
        account.deposit(funds)
        //account? bo dostaniemy nulla przy depozycie, ale ze mamy AccountNotFoundException, nie musimy juz
        accountsRepository.update(account)
    }

    void withdraw(String accountNumber, BigDecimal funds) {
        Account account = accountsRepository.getBy(accountNumber)
        if (account.balance < funds) { //gdy chcemy rzucic wyjatkiem >=
            throw new InsufficientFundsException()
        }
        account.withdraw(funds)
        accountsRepository.update(account)
    }

    void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal funds) {
        withdraw(sourceAccountNumber, funds)
        try {
            deposit(destinationAccountNumber, funds)
        } catch (AccountNotFoundException ex) {
            deposit(sourceAccountNumber, funds)
        }
    }
}
