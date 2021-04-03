package ru.kyeeego.pikit.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.kyeeego.pikit.filter.InFilterExceptionFilter;
import ru.kyeeego.pikit.filter.JwtAuthorizationFilter;
import ru.kyeeego.pikit.modules.auth.entity.MyUserDetailsService;
import ru.kyeeego.pikit.modules.user.entity.UserRole;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final InFilterExceptionFilter inFilterExceptionFilter;

    @Autowired
    public SecurityConfig(MyUserDetailsService userDetailsService,
                          JwtAuthorizationFilter jwtAuthorizationFilter,
                          InFilterExceptionFilter inFilterExceptionFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.inFilterExceptionFilter = inFilterExceptionFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable();

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((httpServletRequest, httpServletResponse, e) -> {
                            httpServletResponse.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    e.getMessage()
                            );
                        })
                );

        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/create").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers("/api/test/super").hasAnyAuthority(UserRole.Access.SUPER)
                .antMatchers("/api/v1/req/new").hasAnyAuthority(UserRole.Access.DEFAULT)
                .antMatchers("/static/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/req/new/update").hasAnyAuthority(UserRole.Access.DEFAULT)
                .antMatchers(HttpMethod.POST, "/api/v1/req/new/file").hasAnyAuthority(UserRole.Access.DEFAULT)
                .antMatchers(HttpMethod.DELETE, "/api/v1/req/new/delete").hasAnyAuthority(UserRole.Access.DEFAULT)
                .antMatchers("/api/v1/req/new/**").hasAnyAuthority(UserRole.Access.MOD)
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(inFilterExceptionFilter, ChannelProcessingFilter.class);
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
