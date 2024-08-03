package org.example.clientbank.customer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class ResponseCustomerDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
