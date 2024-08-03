package org.example.clientbank.employer.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmployerStatus {
    SUCCESS("Operation completed successfully."),
    EMPLOYER_NOT_FOUND("Employer not found."),
    NOTHING_TO_UPDATE("No updates found for the employer."),
    UNEXPECTED("An unforeseen error happened.");

    private final String message;
}
