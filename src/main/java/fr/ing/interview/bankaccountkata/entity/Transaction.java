package fr.ing.interview.bankaccountkata.entity;


import fr.ing.interview.bankaccountkata.enumType.TypeTransaction;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="transaction")
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

    @Column(name="amount",precision=12, scale = 2)
    @NonNull
    private BigDecimal amount;

}
