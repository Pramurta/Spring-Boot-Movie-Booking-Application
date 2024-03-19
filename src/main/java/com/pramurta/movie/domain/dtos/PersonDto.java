package com.pramurta.movie.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String passportNumber;
    private String name;
    private String userRole;
    private String password;
}
