package com.pramurta.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private String movieName;
    private String theatreName;
    private int hallNumber;
    private List<String> seatsAvailable;
}
