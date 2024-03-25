package com.pramurta.movie.helpers;

import com.pramurta.movie.domain.responses.APIResponse;

public final class ResponseHelper{
    public static <T> APIResponse<T> constructSuccessResponse(T responseObject) {
        APIResponse<T> successfulApiResponse = new APIResponse<>();
        successfulApiResponse.setErrorMessage("");
        successfulApiResponse.setResult(responseObject);
        successfulApiResponse.setIsValid(true);
        return successfulApiResponse;
    }

    public static <T> APIResponse<T> constructFailureResponse(String errorMessage) {
        APIResponse<T> failedApiResponse = new APIResponse<>();
        failedApiResponse.setErrorMessage(errorMessage);
        failedApiResponse.setIsValid(false);
        return failedApiResponse;
    }
}
