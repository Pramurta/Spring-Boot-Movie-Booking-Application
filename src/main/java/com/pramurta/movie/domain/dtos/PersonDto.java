package com.pramurta.movie.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String passportNumber;
    private String name;
    private List<String> userRoles;
    private String password;
}
