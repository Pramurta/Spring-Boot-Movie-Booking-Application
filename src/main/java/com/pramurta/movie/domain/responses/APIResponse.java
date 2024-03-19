package com.pramurta.movie.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.json.JsonObject;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse<A> {
    private Boolean isValid;
    private A result;
    private String errorMessage;
}
