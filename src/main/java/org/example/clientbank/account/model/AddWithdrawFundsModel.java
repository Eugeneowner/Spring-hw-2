package org.example.clientbank.account.model;

import jakarta.validation.constraints.NotBlank;

public record AddWithdrawFundsModel(
        @NotBlank(message = "Card number is required and cannot be blank")
        String cardNumber,

        @NotBlank(message = "Sum is required and cannot be blank")
        double sum) {
}
