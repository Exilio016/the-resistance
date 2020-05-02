package com.brunoflaviof.resistance.rest.filter;

import com.brunoflaviof.resistance.rest.jwt.JWTUtil;
import com.brunoflaviof.resistance.rest.repository.UserRepo;
import com.brunoflaviof.resistance.rest.repository.data.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthenticationFilter extends HttpFilter {
    private UserRepo users;

    public AuthenticationFilter(UserRepo users){
        this.users = users;
    }

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && !authHeader.isEmpty()){
            validateAuthHeader(request, response, chain, authHeader);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void validateAuthHeader(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String authHeader) throws IOException, ServletException {
        String[] authHeaderArray = authHeader.split(" ");
        if(isAuthenticationHeaderValid(authHeaderArray)) {
            User user = getUserFomHeaderArray(authHeaderArray[1]);
            if(user != null){
                chain.doFilter(request, response);
            }
        }
    }

    private boolean isAuthenticationHeaderValid(String[] authHeaderArray) {
        return authHeaderArray.length > 1;
    }

    private User getUserFomHeaderArray(String headerArray) {
        String token = headerArray;
        UUID userID = JWTUtil.getUserIDFromToken(token);
        return users.getUser(userID);
    }
}
