package com.pramurta.movie.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "movieTheatres")
public class MovieTheatre {
    private String location;
    @Id
    private String movieTheatreName;
    private List<Integer> hallNumbers;
}
