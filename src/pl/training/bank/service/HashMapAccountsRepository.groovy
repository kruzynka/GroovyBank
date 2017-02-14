package pl.training.bank.service

import pl.training.bank.entity.Account

import java.util.concurrent.atomic.AtomicLong

/**
 * Logika do kont
 * Zmieniliśmy nazwę AccountsRepository na HashMapAccountsRepository i tworzymy inerfejs AccountsRepository
 * który następnie implementujemy
 * co do definicji HashMapAccountsRepository jest tej samej relacji co AccountsRepository
 */
class HashMapAccountsRepository implements AccountsRepository {

    private counter = new AtomicLong() //rownowazna forma private AtomicLong counter = new AtomicLong()
    private Map<String, Account> accounts = [:] //mapa, początkowo ustawiona jako pusta

    Long save(Account account) {
        Long id = counter.incrementAndGet()
        account.id = id
        accounts[account.number] = account //konto wstawia pod numerem do mapy
        return id
    }

    Account getBy(String accountNumber) { //sprawdzamy czy nr konta jest w mapie
        if (!(accountNumber in accounts)) { // jeżeli nie to wyjątkiem rzucamy
            throw new AccountNotFoundException()
        }
        accounts[accountNumber] //zwracamy konta z mapy
    }

    void update(Account account) {
        getBy(account.number) //sprawdzamy czy jest konto czy go nie ma
        accounts[account.number] = account //jeżeli konto było, dosłownie nadpisujemy konto z mapy
    }

}
