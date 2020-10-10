package fr.ing.interview.bankaccountkata.service;

import fr.ing.interview.bankaccountkata.entity.Account;
import fr.ing.interview.bankaccountkata.enumType.TypeTransaction;
import fr.ing.interview.bankaccountkata.exception.AccountNotFound;
import fr.ing.interview.bankaccountkata.exception.InvalidParameters;
import fr.ing.interview.bankaccountkata.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;



import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    private Account myAccount;

    @BeforeEach
    void setUp() {
        myAccount = new Account();
        myAccount.setId(UUID.randomUUID());
        myAccount.setBalance(new BigDecimal("305.03"));
        myAccount.setName("myAccountName");
    }

    @Test
    void depositNormal() throws AccountNotFound, InvalidParameters {
        Mockito.when(accountRepository.findById(myAccount.getId())).thenReturn(Optional.of(myAccount));
        accountService.deposit(myAccount.getId(), new BigDecimal("0.01"));


        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(argument.capture());
        assertThat(argument.getValue().getBalance()).isEqualTo("305.04");

        verify(transactionService,times(1)).addTransaction(myAccount, TypeTransaction.DEPOSIT,new BigDecimal("0.01"));

    }

    @Test
    void depositThrowsNotFoundAccount() {
        Mockito.when(accountRepository.findById(myAccount.getId())).thenReturn(Optional.empty());
        assertThrows(AccountNotFound.class, () -> accountService.deposit(myAccount.getId(), new BigDecimal("0.01")));
    }

    @Test
    void depositThrowsInvalidParameters() {
        assertThrows(InvalidParameters.class, () -> accountService.deposit(myAccount.getId(), new BigDecimal("0.005")));
    }

    @Test
    void withdrawNormal() throws AccountNotFound, InvalidParameters {
        Mockito.when(accountRepository.findById(myAccount.getId())).thenReturn(Optional.of(myAccount));
        accountService.withdraw(myAccount.getId(), new BigDecimal("304.02"));


        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(argument.capture());
        assertThat(argument.getValue().getBalance()).isEqualTo("1.01");
        verify(transactionService,times(1)).addTransaction(myAccount, TypeTransaction.WITHDRAW,new BigDecimal("304.02"));


    }

    @Test
    void withdrawTooMuch(){
        Mockito.when(accountRepository.findById(myAccount.getId())).thenReturn(Optional.of(myAccount));
        assertThrows(InvalidParameters.class,() -> accountService.withdraw(myAccount.getId(), new BigDecimal("305.05")));
    }

    @Test
    void getBalance() throws InvalidParameters, AccountNotFound {
        Mockito.when(accountRepository.findById(myAccount.getId())).thenReturn(Optional.of(myAccount));
        assertThat(accountService.getBalance(myAccount.getId())).isEqualTo(new BigDecimal("305.03"));
    }
}
