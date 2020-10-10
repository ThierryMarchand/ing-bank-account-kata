package fr.ing.interview.bankaccountkata.entity;


import fr.ing.interview.bankaccountkata.enumType.TypeTransaction;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    @Column(name = "amount", precision = 12, scale = 2)
    @NonNull
    private BigDecimal amount;

    @Column(name = "date")
    @NonNull
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    @NonNull
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NonNull BigDecimal amount) {
        this.amount = amount;
    }

    @NonNull
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDateTime date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
