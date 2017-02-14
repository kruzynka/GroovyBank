package pl.training.bank.service

import pl.training.bank.entity.Account
import pl.training.bank.entity.Customer
import pl.training.bank.utils.Observable

/** Stworzyliśmy Observable<T> i chcemy zaimpementować je do AccountService */
class AccountsService implements Accounts, Observable<Account> {

    // _ zwiększa czytelność, będzie ignorowane
    private static final DEPOSIT_LIMIT = 20_000

    private AccountsRepository accountsRepository
    private AccountNumberGenerator accountNumberGenerator
    //nie robimy paskudztwa: private AccountNumberGenerator accountNumberGenerator = new accountNumberGenerator

    /**
     * nie moze to byc jako void, tylko jako Account
     * przypiszemy kolejny numer konta, mozem równoważnie uzyc accountNumberGenerator.next:
     */
    Account createAccount(Customer customer) {
        //tworzymy nową instancję i inicjalizujemy nowym numerem:
        Account account = new Account(number: accountNumberGenerator.getNext())
        account.addCustomer(customer) // przypisujemy otrzymanego customera
        accountsRepository.save(account) // zapisujemy na stałe w repozytorium
        return account // zwracamy konto
    }

    /**
     * Deponujemy środki na konto
     * accountNumber parametr jest typowany
     * @param accountNumber
     * @param founds
     */
    void deposit(String accountNumber, BigDecimal funds) {
        Account account = accountsRepository.getBy(accountNumber) //jeżeli to konto jest...

        if (funds > DEPOSIT_LIMIT){
            notifyObservers(account)
        }
        account.deposit(funds) // depoujemy środki
        //account? bo dostaniemy nulla przy depozycie, ale ze mamy AccountNotFoundException, nie musimy już
        accountsRepository.update(account)
    }

    /**
     * Wypłać środki, pod warunkiem, że są środki
     * @param accountNumber
     * @param funds
     */
    void withdraw(String accountNumber, BigDecimal funds) {
        Account account = accountsRepository.getBy(accountNumber)
        if (account.balance < funds) { //gdy chcemy rzucic wyjatkiem >=
            throw new InsufficientFundsException()
        }
        account.withdraw(funds)
        accountsRepository.update(account)
    }

    /**
     * Dokonujemy transferu
     * @param sourceAccountNumber
     * @param destinationAccountNumber
     * @param funds
     */
    void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal funds) {
        withdraw(sourceAccountNumber, funds) //wypłacamy środki z 1. konta
        try {
            deposit(destinationAccountNumber, funds) // próbujemy zdeponować
        } catch (AccountNotFoundException ex) {
            deposit(sourceAccountNumber, funds) // jeżeli się nie uda, zwramy środki na 1. konto
        }
    }


}
