package org.example.clientbank.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.example.clientbank.customer.Customer;
import org.example.clientbank.AbstractEntity;
import org.example.clientbank.account.enums.Currency;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "accounts")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(exclude = "customer")
public class Account extends AbstractEntity {

    @Column(nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Customer customer;

    public Account(Currency currency, Customer customer) {
        this.id = null;
        this.number = UUID.randomUUID().toString();
        this.currency = currency;
        this.balance = 0.0;
        this.customer = customer;
    }
}
