package org.example.clientbank.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class ResponseAccountDto {
    private Long id;
    private String number;
    private String currency;
    private Double balance;
}
