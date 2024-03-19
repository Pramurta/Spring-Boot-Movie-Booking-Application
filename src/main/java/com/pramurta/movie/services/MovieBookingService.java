package com.pramurta.movie.services;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.entities.MovieBooking;
import com.pramurta.movie.domain.models.MovieSeat;
import com.pramurta.movie.repositories.AvailableShowRepository;
import com.pramurta.movie.repositories.MovieBookingRepository;
import com.pramurta.movie.repositories.MovieTheatreRepository;
import com.pramurta.movie.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieBookingService {
    private final MovieBookingRepository movieBookingRepository;
    private final AvailableShowRepository availableShowRepository;
    private final PersonRepository personRepository;

    public MovieBookingService(MovieBookingRepository movieBookingRepository, AvailableShowRepository availableShowRepository, PersonRepository personRepository) {
        this.movieBookingRepository = movieBookingRepository;
        this.availableShowRepository = availableShowRepository;
        this.personRepository = personRepository;
    }

    public MovieBooking createMovieBooking(MovieBooking movieBookingPayload) {
        if(!availableShowRepository.existsById(movieBookingPayload.getShowId().toString())) {
            throw new RuntimeException(String.format("There's no show with the ID: %s"
                    ,movieBookingPayload.getShowId()));
        }
        if(!personRepository.existsById(movieBookingPayload.getPassportNumber())) {
            throw new RuntimeException(String.format("Person with passport number %s doesn't exist",movieBookingPayload.getPassportNumber()));
        }
        AvailableShow availableShow = availableShowRepository.findById(movieBookingPayload.getShowId().toString()).get();
        double actualPriceRequiredToBePaid = availableShow.getPerTicketPrice()*movieBookingPayload.getSeatNumbers().size();
        if(actualPriceRequiredToBePaid!=movieBookingPayload.getAmountPaid()) {
            throw new RuntimeException(String.format("Amount paid: %s, but you were required to pay: %s",
                    movieBookingPayload.getAmountPaid(),actualPriceRequiredToBePaid));
        }
        List<String> seatNumsBooked = movieBookingPayload.getSeatNumbers();
        for(String seatNum: seatNumsBooked) {
            for(MovieSeat movieSeat: availableShow.getSeats()) {
                if(movieSeat.getSeatNumber().equals(seatNum) && !movieSeat.getIsAvailable()) {
                    throw new RuntimeException(String.format("The seat: %s isn't available. Please re-select the seats",seatNum));
                }
            }
        }
        return movieBookingRepository.save(movieBookingPayload);
    }

    public void cancelBooking(String movieBookingId) {
        if(!movieBookingRepository.existsById(movieBookingId)) {
            throw new RuntimeException(String.format("Movie booking with ID: %s doesn't exist",movieBookingId));
        }
        movieBookingRepository.deleteById(movieBookingId);
    }
}
