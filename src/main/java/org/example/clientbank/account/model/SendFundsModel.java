package org.example.clientbank.account.model;

import jakarta.validation.constraints.NotBlank;

public record SendFundsModel(
        @NotBlank(message = "Sender account number is required and cannot be blank")
        String numberFrom,

        @NotBlank(message = "Recipient account number is required and cannot be blank")
        String numberTo,

        @NotBlank(message = "Sum is required and cannot be blank")
        double sum) {
}