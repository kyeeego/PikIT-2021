package ru.kyeeego.pikit.modules.requisition.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kyeeego.pikit.exception.BadRequestException;
import ru.kyeeego.pikit.exception.ForbiddenException;
import ru.kyeeego.pikit.modules.requisition.entity.Requisition;
import ru.kyeeego.pikit.modules.requisition.entity.RequisitionStatus;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.requisition.port.RequisitionRepository;
import ru.kyeeego.pikit.modules.user.entity.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Order(2)
public class ModifyReqAccessFilter implements Filter {

    private final RequisitionRepository repository;

    @Autowired
    public ModifyReqAccessFilter(RequisitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("INITIALIZE ModifyReqAccessFilter");
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid ID");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        var authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();

        Requisition requisition = repository
                .findByIdAndStatus(id, RequisitionStatus.MODERATING)
                .orElseThrow(RequisitionNotFoundException::new);

        if (!requisition.getAuthorEmail().equals(userEmail)
                && !authorities.containsAll(Arrays.stream(UserRole.Access.MOD)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()))) {
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
