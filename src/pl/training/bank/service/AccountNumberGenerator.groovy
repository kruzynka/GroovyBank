package pl.training.bank.service

import java.util.concurrent.atomic.AtomicLong

import static java.lang.String.format

class AccountNumberGenerator {

    private counter = new AtomicLong() // to samo co private AtomicLong counter = new AtomicLong()

    String getNext() {

        format("%026d", counter.incrementAndGet()) // to samo co String.format("%026d", counter.incrementAndGet())

    }
}
