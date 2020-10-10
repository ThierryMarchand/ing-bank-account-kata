package fr.ing.interview.bankaccountkata.repository;

import fr.ing.interview.bankaccountkata.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t where t.account.id = :uuid")
    Collection<Transaction> getAllTransactionByAccount(@Param("uuid") UUID uuid);

}
