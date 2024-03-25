package com.pramurta.movie.controllers.helpers;

import com.pramurta.movie.domain.enums.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class ControllerHelper {

    public static final String USER_NOT_LOGGED_IN_MESSAGE = "You're not logged in!";
    public static boolean isUserLoggedIn(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        return username != null;
    }

    public static boolean isUserAppAdmin(HttpSession httpSession) {
        List<UserRole> userRoles = (List<UserRole>) httpSession.getAttribute("userRoles");
        return userRoles.contains(UserRole.APP_ADMIN);
    }

    public static boolean isUserTheatreAdmin(HttpSession httpSession) {
        List<UserRole> userRoles = (List<UserRole>) httpSession.getAttribute("userRoles");
        return userRoles.contains(UserRole.THEATRE_ADMIN);
    }

    public static boolean isUserMovieAdmin(HttpSession httpSession) {
        List<UserRole> userRoles = (List<UserRole>) httpSession.getAttribute("userRoles");
        return userRoles.contains(UserRole.MOVIE_ADMIN);
    }
}
