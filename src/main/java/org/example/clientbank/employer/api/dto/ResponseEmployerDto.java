package org.example.clientbank.employer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;


@EqualsAndHashCode
@AllArgsConstructor
@Data
public class ResponseEmployerDto {
    private Long id;
    private String name;
    private String address;
}
