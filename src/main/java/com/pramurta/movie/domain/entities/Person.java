package com.pramurta.movie.domain.entities;


import com.pramurta.movie.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "persons")
public class Person {
    @Id
    private String passportNumber;
    private String name;
    private List<UserRole> userRoles;
    private String password;
}
