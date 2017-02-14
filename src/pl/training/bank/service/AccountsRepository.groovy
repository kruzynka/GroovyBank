package pl.training.bank.service

import pl.training.bank.entity.Account

import java.util.concurrent.atomic.AtomicLong

class AccountsRepository {

    private counter = new AtomicLong() //rownowazna forma private AtomicLong counter = new AtomicLong()
    private Map<String, Account> accounts = [:]

    Long save(Account account) {
        Long id = counter.incrementAndGet()
        account.id = id
        accounts[account.number] = account
        return id
    }

    Account getBy(String accountNumber) {
        if (!(accountNumber in accounts)) {
            throw new AccountNotFoundException()
        }
        accounts[accountNumber]
    }

    void update(Account account) {
        getBy(account.number)
        accounts[account.number] = account
    }

}
