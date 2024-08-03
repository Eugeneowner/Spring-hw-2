package org.example.clientbank.customer.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class RequestCustomerDto {

    @NotBlank(message = "Name is required and cannot be blank")
    @Size(min = 2, max = 100, message = "Name length must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[\\w+_.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Email address is not in a valid format")
    @Size(max = 100, message = "Email length must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Age cannot be empty")
    @Min(value = 18, message = "You need to be 18 years old or older")
    @Size(max = 3, message = "Age must be no more than 3 characters long")
    private Integer age;
}
