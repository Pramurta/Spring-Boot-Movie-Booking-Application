package com.pramurta.movie.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePersonDto {
    private String passportNumber;
    private String name;
    private String oldPassword;
    private String newPassword;
}
