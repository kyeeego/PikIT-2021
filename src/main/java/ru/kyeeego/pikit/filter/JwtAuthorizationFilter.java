package ru.kyeeego.pikit.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kyeeego.pikit.modules.auth.entity.MyUserDetailsService;
import ru.kyeeego.pikit.modules.auth.usecase.AccessTokenService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Order(1)
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {

    private final MyUserDetailsService myUserDetailsService;
    private final AccessTokenService jwtService;

    @Override
    public void doFilter(ServletRequest request,
                            ServletResponse response,
                            FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;

        final String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String accessToken = authHeader.split(" ")[1];

        final String email = jwtService.extractEmail(accessToken);
        if (email == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);
        if (jwtService.validateToken(accessToken, userDetails)) {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(req));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}