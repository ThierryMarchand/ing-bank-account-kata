package fr.ing.interview.bankaccountkata.service;

import fr.ing.interview.bankaccountkata.entity.Account;
import fr.ing.interview.bankaccountkata.exception.AccountNotFound;
import fr.ing.interview.bankaccountkata.exception.InvalidParameters;
import fr.ing.interview.bankaccountkata.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void deposit(UUID accountId, BigDecimal amount) throws AccountNotFound, InvalidParameters {
        if (accountId == null || amount == null) {
            throw new InvalidParameters("AccountId or amount is null");
        } else if (amount.compareTo(new BigDecimal("0.01"))  < 0) {
            throw new InvalidParameters("Amount shall be greater than 0.01");
        }
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFound("Account UUID Not Found: " + accountId.toString()));
        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

    }

    @Transactional
    public void withdraw(UUID accountId, BigDecimal amount) throws AccountNotFound, InvalidParameters {
        if (accountId == null || amount == null) {
            throw new InvalidParameters("AccountId or amount is null");
        }

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFound("Account UUID Not Found: " + accountId.toString()));

        if (amount.compareTo(account.getBalance())  > 0) {
            throw new InvalidParameters("Amount shall be lower than balance");
        }
        account.setBalance(account.getBalance().add(amount.negate()));

        accountRepository.save(account);
    }

    public BigDecimal getBalance(UUID accountId) throws InvalidParameters, AccountNotFound {
        if (accountId == null) {
            throw new InvalidParameters("AccountId is null");
        }

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFound("Account UUID Not Found: " + accountId.toString()));

        return account.getBalance();
    }
}
