package com.pramurta.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movieBookings")
public class MovieBooking {
    private String passportNumber;
    @Id
    private String showId;
    private List<String> seatNumbers;
}
