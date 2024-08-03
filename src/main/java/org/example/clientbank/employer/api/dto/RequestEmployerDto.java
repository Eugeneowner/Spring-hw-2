package org.example.clientbank.employer.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class RequestEmployerDto {
    @Size(min = 2, max = 100, message = "Name must have a length of 2 to 100 characters")
    @Size(max = 100, message = "Name length must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required and cannot be blank")
    @Email(message = "Email address is not in a valid format")
    @Size(max = 100, message = "Email length must not exceed 100 characters")
    private String address;
}
