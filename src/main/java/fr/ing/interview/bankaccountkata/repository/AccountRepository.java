package fr.ing.interview.bankaccountkata.repository;

import fr.ing.interview.bankaccountkata.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
