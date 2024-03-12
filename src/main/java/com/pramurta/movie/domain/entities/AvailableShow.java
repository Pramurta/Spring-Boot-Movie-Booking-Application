package com.pramurta.movie.domain.entities;

import com.pramurta.movie.domain.models.MovieSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "availableShows")
public class AvailableShow {
    @Id
    private ObjectId id;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private String movieName;
    private String theatreName;
    private int hallNumber;
    private List<MovieSeat> seatsAvailable;
    private double perTicketPrice;
}
