package pl.training.bank.accounts

import groovy.sql.Sql

import javax.sql.DataSource
import java.util.concurrent.atomic.AtomicLong

import static java.lang.String.format

//generator numerów kont
class AccountNumberGenerator {

    private static final String SELECT_MAX_SQL = "select max(number) from accounts"

    /** równoważna postać private AtomicLong counter = new AtomicLong()
     * AtomicLong zapewnia wielowątkowość
     * private final counter = new AtomicLong() sprawi, że nie będę w stanie podstawić nowych wartości
     * final na metodzie - możemy dziedziczyć, ale nie możemy przedefiniować tej metody
     */
    private counter = new AtomicLong()

    AccountNumberGenerator(DataSource dataSource) {
        new Sql(dataSource).eachRow(SELECT_MAX_SQL) { row ->
            def lastAccountNumber = row[0]
            if (lastAccountNumber) {
                counter = new AtomicLong(lastAccountNumber as Long)
            }
        }
    }


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
