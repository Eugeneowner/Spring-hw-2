package org.example.clientbank.employer.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmployerStatus {
    SUCCESS("Success operation."),
    EMPLOYER_NOT_FOUND("Employer not found."),
    NOTHING_TO_UPDATE("No changes detected for the employer."),
    UNEXPECTED("An unexpected error occurred.");

    private final String message;
}