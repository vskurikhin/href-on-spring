package su.svn.href.configs;

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
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
            .authorizeExchange()
                .pathMatchers("/login", "/logout", "/css/**").permitAll()
                .pathMatchers("/").hasRole("USER")
                .anyExchange().authenticated()
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
