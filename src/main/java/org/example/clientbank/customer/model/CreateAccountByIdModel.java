package org.example.clientbank.customer.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateAccountByIdModel(
        @NotNull(message = "ID cannot be empty or null")
        Long id,

        @NotNull(message = "Currency cannot be empty or null")
        @Pattern(regexp = "USD|EUR|UAH|CHF|GBP", message = "Currency must be one of USD, EUR, UAH, CHF, GBP")
        String currency) {
}
