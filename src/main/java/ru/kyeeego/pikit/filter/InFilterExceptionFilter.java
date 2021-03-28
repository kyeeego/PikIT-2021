package ru.kyeeego.pikit.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kyeeego.pikit.exception.ApiException;
import ru.kyeeego.pikit.filter.exception.ErrorResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class InFilterExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ApiException e) {

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(e.getStatus());
            errorResponse.setMessage(e.getMessage());

            httpServletResponse.setStatus(e.getStatus().value());
            httpServletResponse.getWriter().write(JSON(errorResponse));

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorResponse.setMessage(e.getMessage());

            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(JSON(errorResponse));

        }
    }

    private String JSON(Object o) throws JsonProcessingException {
        if (o == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

}
