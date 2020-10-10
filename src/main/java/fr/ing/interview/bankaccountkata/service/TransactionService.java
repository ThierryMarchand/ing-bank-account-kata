package fr.ing.interview.bankaccountkata.service;


import fr.ing.interview.bankaccountkata.entity.Account;
import fr.ing.interview.bankaccountkata.entity.Transaction;
import fr.ing.interview.bankaccountkata.enumType.TypeTransaction;
import fr.ing.interview.bankaccountkata.exception.InvalidParameters;
import fr.ing.interview.bankaccountkata.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(Account account, TypeTransaction type, BigDecimal amount) throws InvalidParameters {
        if (account == null || type == null || amount == null || account.getId() == null) {
            throw new InvalidParameters("Account or amount or type is null");
        }
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setTypeTransaction(type);
        transactionRepository.save(transaction);
    }


    public Collection<Transaction> getTransactions(UUID id_account) throws InvalidParameters {
        if (id_account == null) {
            throw new InvalidParameters("Account is null");
        }
        return transactionRepository.getAllTransactionByAccount(id_account);
    }
}
