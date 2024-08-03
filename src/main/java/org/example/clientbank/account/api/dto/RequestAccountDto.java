package org.example.clientbank.account.api.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.example.clientbank.account.enums.Currency;

import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class RequestAccountDto {

    @NotBlank(message = "Number is required and cannot be blank")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
   private String number;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Currency cannot be blank")
    @Size(max = 3, message = "Currency code must not exceed 3 characters")
   private Currency currency;

    @NotBlank(message = "Balance is required and cannot be blank")
    @Size(max = 15, message = "Currency field cannot be longer than 15 characters")
   private Double balance;
}
