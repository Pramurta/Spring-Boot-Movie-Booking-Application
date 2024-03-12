package com.pramurta.movie.domain.dtos;

import com.pramurta.movie.domain.models.MovieSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableShowDto {
    private String fromTime;
    private String toTime;
    private String movieName;
    private String theatreName;
    private int hallNumber;
    private List<MovieSeat> seatsAvailable;
    private double perTicketPrice;
}
