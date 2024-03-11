package com.pramurta.movie.domain.userInputs;

import com.pramurta.movie.domain.MovieSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieBookingUserInput {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private String movieName;
    private String theatreName;
    private int hallNumber;
    private List<MovieSeat> seatsBooked;
    private double amountPaid;
    private String passportNumber;
}
