package pl.training.bank.service

import java.util.concurrent.atomic.AtomicLong

import static java.lang.String.format

//generator numerów kont
class AccountNumberGenerator {

    private counter = new AtomicLong()
    // równoważna postać private AtomicLong counter = new AtomicLong()
    //AtomiCLong zapewnia wielowątkowość,

    String getNext() {

        /** %026d sposób na podstawianie wartości w Stringu
         * % w tym miejscu chcemy wstawić wartość
         *  %d wstawiłby wartość numeryczną, d - liczba
         *  026 uzupełniamy z lewej strony do 26 miejsc zerami
         *  AtomicLong zaczyna od 0
         */
        format("%026d", counter.incrementAndGet()) // to samo co String.format("%026d", counter.incrementAndGet())

    }
}
