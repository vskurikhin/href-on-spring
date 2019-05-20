package su.svn.href.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        LOGGER.debug("Initializing the security configuration");
        return http.csrf().disable()
            .authorizeExchange()
            .pathMatchers("/").hasRole("USER")
            .anyExchange()
            .permitAll()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(
                new HttpStatusServerAccessDeniedHandler(HttpStatus.BAD_REQUEST)
            )
            .and()
            .httpBasic()
            .and()
            .build();
    }
}
