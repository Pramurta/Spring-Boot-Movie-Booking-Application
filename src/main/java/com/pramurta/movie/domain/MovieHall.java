package com.pramurta.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "movieHalls")
@CompoundIndex(name = "movieHallIndex", def = "{'theatreName': 1, 'hallNumber': 1}")
public class MovieHall {
    private String hallNumber;
    private String theatreName;
    private List<MovieSeat> movieSeats;
}
