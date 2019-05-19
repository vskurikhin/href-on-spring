package su.svn.href.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        LOGGER.debug("Initializing the security configuration");
        return http.authorizeExchange()
            .pathMatchers("/").hasRole("USER")
            .anyExchange().permitAll()
            .and().httpBasic()
            .and().build();
    }

    /**
     * Sample in-memory user details service.
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        LOGGER.debug("Initializing the user details service");
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("pass")
            .roles("USER")
            .build();
        return new MapReactiveUserDetailsService(user);
    }
}
