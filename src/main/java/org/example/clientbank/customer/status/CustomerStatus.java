package org.example.clientbank.customer.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomerStatus {
    SUCCESS("Operation completed successfully."),
    CUSTOMER_NOT_FOUND("Customer could not be found."),
    NOTHING_TO_UPDATE("No modifications detected for the customer."),
    CARD_NOT_FOUND("Card not found"),
    UNEXPECTED("An unforeseen error happened.");

    private final String message;
}
