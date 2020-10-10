package fr.ing.interview.bankaccountkata.service;


import fr.ing.interview.bankaccountkata.entity.Account;
import fr.ing.interview.bankaccountkata.entity.Transaction;
import fr.ing.interview.bankaccountkata.enumType.TypeTransaction;
import fr.ing.interview.bankaccountkata.exception.InvalidParameters;
import fr.ing.interview.bankaccountkata.repository.AccountRepository;
import fr.ing.interview.bankaccountkata.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({TransactionService.class})
public class TransactionServiceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    private Account myAccount1;
    private Account myAccount2;

    @BeforeEach
    void setUp() {
        myAccount1 = new Account();
        myAccount1.setBalance(new BigDecimal("305.03"));
        myAccount1.setName("myAccount1Name");
        myAccount1 = accountRepository.save(myAccount1);

        myAccount2 = new Account();
        myAccount2.setBalance(new BigDecimal("25.065"));
        myAccount2.setName("myAccount2Name");
        myAccount2 = accountRepository.save(myAccount2);
    }

    @Test
    void testAddTransaction() throws InvalidParameters {
        transactionService.addTransaction(myAccount1, TypeTransaction.DEPOSIT,new BigDecimal("3"));
        transactionService.addTransaction(myAccount1, TypeTransaction.WITHDRAW,new BigDecimal("4"));
        transactionService.addTransaction(myAccount2, TypeTransaction.WITHDRAW,new BigDecimal("5"));
        transactionService.addTransaction(myAccount1, TypeTransaction.DEPOSIT,new BigDecimal("6"));

        Collection<Transaction> transactions = transactionService.getTransactions(myAccount1.getId());
        assertThat(transactions.size()).isEqualTo(3);
        assertThat(transactions.stream().map(t -> t.getAmount().toString()).collect(Collectors.toList())).containsExactlyInAnyOrder("6","3","4");

    }
}
