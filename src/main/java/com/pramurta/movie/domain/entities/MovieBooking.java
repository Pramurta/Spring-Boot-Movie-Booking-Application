package com.pramurta.movie.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movieBookings")
public class MovieBooking {
    @Id
    private ObjectId id;
    private String passportNumber;
    private ObjectId showId;
    private List<String> seatNumbers;
    private double amountPaid;
}
