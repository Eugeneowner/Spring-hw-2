package org.example.clientbank.customer.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.example.clientbank.account.Account;
import org.example.clientbank.employer.Employer;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class ResponseCustomerAllDataDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Account> accounts;
    private List<Employer> employers;
}
