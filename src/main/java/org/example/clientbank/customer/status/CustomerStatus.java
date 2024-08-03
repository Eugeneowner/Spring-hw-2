package org.example.clientbank.customer.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomerStatus {
    SUCCESS("Success operation."),
    CUSTOMER_NOT_FOUND("Customer not found."),
    NOTHING_TO_UPDATE("No changes detected for the customer."),
    CARD_NOT_FOUND("Card not found"),
    UNEXPECTED("An unexpected error occurred.");

    private final String message;
}
