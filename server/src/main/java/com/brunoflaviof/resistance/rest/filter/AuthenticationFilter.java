package com.brunoflaviof.resistance.rest.filter;

import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import com.brunoflaviof.resistance.rest.repository.data.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthenticationFilter extends HttpFilter {
    private final UserRepo users;
    private final JWTUtil jwtUtil;

    public AuthenticationFilter(UserRepo users, JWTUtil jwtUtil){
        this.users = users;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && !authHeader.isEmpty()){
            User u = validateAuthHeader(request, response, chain, authHeader);
            if(u != null) {
                String userId = request.getHeader("userId");
                if(userId != null && userId.equalsIgnoreCase(u.getUserID().toString())) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private User validateAuthHeader(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String authHeader) {
        String[] authHeaderArray = authHeader.split(" ");
        if(isAuthenticationHeaderValid(authHeaderArray)) {
            return getUserFomHeaderArray(authHeaderArray[1]);
        }
        return null;
    }

    private boolean isAuthenticationHeaderValid(String[] authHeaderArray) {
        return authHeaderArray.length > 1;
    }

    private User getUserFomHeaderArray(String headerArray) {
        UUID userID = jwtUtil.getUserIDFromToken(headerArray);
        return users.getByUserID(userID);
    }
}
