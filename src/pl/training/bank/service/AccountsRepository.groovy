package pl.training.bank.service

import pl.training.bank.entity.Account

/**
 * Deklarujemy sygnatury metod, więc wrzucamy te same metody co w HashMapAccountsRepository
 * Również nie ma prawa wprowadzić np. pól
 * Chyba, że definiujemy default ... np. default void tes() { ... } - jest to klasa np. do której nie mamy źródeł
 * Przy interface może być tylko extends (ponieważ może tylko dziedziczyć, nie może implementować)
 */
interface AccountsRepository {

    Long save(Account account)

    Account getBy(String accountNumber)

    void update(Account account)

}