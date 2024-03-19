package com.pramurta.movie.services.validators;

import com.pramurta.movie.domain.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonValidationResult {
    private String validationMessage;
    private Boolean isValid;
    private Person person;
}
