package com.wasd.messenger.config.filter;

import com.wasd.messenger.entity.User;
import com.wasd.messenger.service.UserService;
import com.wasd.messenger.utils.JwtTokenProvider;
import com.wasd.messenger.utils.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Order(4)
@Component
@RequiredArgsConstructor
public class UserHolderFilter extends HttpFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService      userService;
    private final List<String>     allowedUri = getAllowedUriList();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (allowedUri(request.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            String jwt = jwtTokenProvider.resolveToken(request);

            if (Objects.isNull(jwt) || !jwtTokenProvider.validateToken(jwt)) {
                throw new ServletException("Invalid JWT Token");
            }

            String username = jwtTokenProvider.getUsernameFromToken(jwt);
			UUID userId = UUID.fromString(username);
			
			User user = userService.findById(userId)
				.orElseThrow(() -> new ServletException("User not found"));

			UserContextHolder.setUser(user);

            chain.doFilter(request, response);
        }
    }

    private boolean allowedUri(String uri) {
        return uri.startsWith("/healthz") || allowedUri.stream().anyMatch(uri::startsWith);
    }

    private List<String> getAllowedUriList() {
        List<String> list = new ArrayList<>(); 
        list.add("/api/auth/");
        list.add("/");
        return list;
    }
}