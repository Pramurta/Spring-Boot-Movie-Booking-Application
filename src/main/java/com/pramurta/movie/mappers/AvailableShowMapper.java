package com.pramurta.movie.mappers;

import com.pramurta.movie.domain.entities.AvailableShow;
import com.pramurta.movie.domain.dtos.AvailableShowDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class AvailableShowMapper implements Mapper<AvailableShow, AvailableShowDto> {

    @Override
    public AvailableShow mapToEntity(AvailableShowDto availableShowDto) {
        if (availableShowDto == null) {
            return null;
        }

        AvailableShow entity = new AvailableShow();
        entity.setId(new ObjectId());
        entity.setFromTime(parseLocalDateTime(availableShowDto.getFromTime()));
        entity.setToTime(parseLocalDateTime(availableShowDto.getToTime()));
        entity.setMovieName(availableShowDto.getMovieName());
        entity.setTheatreName(availableShowDto.getTheatreName());
        entity.setHallNumber(availableShowDto.getHallNumber());
        entity.setPerTicketPrice(availableShowDto.getPerTicketPrice());
        entity.setSeats(availableShowDto.getSeats());

        return entity;
    }

    public LocalDateTime parseLocalDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date-time format. Please use 'yyyy-MM-dd HH:mm'");
        }
    }

    @Override
    public AvailableShowDto mapToDto(AvailableShow availableShow) {
        if(availableShow==null) {
            return null;
        }
        AvailableShowDto dto = new AvailableShowDto();
        dto.setFromTime(availableShow.getFromTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setToTime(availableShow.getToTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setMovieName(availableShow.getMovieName());
        dto.setTheatreName(availableShow.getTheatreName());
        dto.setHallNumber(availableShow.getHallNumber());
        dto.setPerTicketPrice(availableShow.getPerTicketPrice());
        dto.setSeats(availableShow.getSeats());

        return dto;
    }
}
