package org.example.clientbank.employer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.clientbank.AbstractEntity;
import org.example.clientbank.customer.Customer;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "employers")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Employer extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "customers_employers",
            joinColumns = @JoinColumn(name = "employer_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
   private List<Customer> customers;

    public Employer(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
