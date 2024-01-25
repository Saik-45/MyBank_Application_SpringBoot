package com.sai.My_Bank_Experiment.Service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountNumberGenerationService {

    private static final long MIN_ACCOUNT_NUMBER = 98763214781L;
    private static final long MAX_ACCOUNT_NUMBER = 98799999999L;

    private final Set<Long> usedAccountNumbers = new HashSet<>();

    public synchronized Long generateNextAccountNumber() {
        long accountNumber;
        do {
            accountNumber = random(MIN_ACCOUNT_NUMBER, MAX_ACCOUNT_NUMBER);
        } while (usedAccountNumbers.contains(accountNumber));

        usedAccountNumbers.add(accountNumber);
        return accountNumber;
    }

    private long random(long minAccountNumber, long maxAccountNumber) {
        return ThreadLocalRandom.current().nextLong(minAccountNumber, maxAccountNumber + 1);
    }
}
