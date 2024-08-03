package org.example.clientbank.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.clientbank.AbstractEntity;
import org.example.clientbank.account.Account;
import org.example.clientbank.employer.Employer;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "customers")

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Customer extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ManyToMany(mappedBy = "customers", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Employer> employers;

    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.accounts = new ArrayList<>();
        this.employers = new ArrayList<>();
    }
}
