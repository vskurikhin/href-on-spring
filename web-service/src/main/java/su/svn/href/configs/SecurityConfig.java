package su.svn.href.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import su.svn.href.dao.AccountDao;
import su.svn.href.models.Account;
import su.svn.href.models.UserAccount;

import java.util.function.Function;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private Function<ServerWebExchange, Mono<Authentication>> tokenAuthenticationConverter()
    {

        return serverWebExchange -> {

            String authorization = serverWebExchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authorization == null || !authorization.startsWith("Bearer "))
                return Mono.empty();

            return Mono.just(new JwtAuthenticationToken(authorization.substring(7)));
        };
    }

    private static Mono<UserDetails> user(Account a, String... roles)
    {
        return a.isActive() ? Mono.just(new UserAccount(a, roles)) : Mono.empty();
    }

    private ReactiveAuthenticationManager tokenAuthenticationManager(AccountDao accountDao)
    {
        return authentication -> {
            String token = (String) authentication.getCredentials();

            System.err.println("token = " + token);

            return accountDao
                .findByUsername(token)
                .flatMap(account -> user(account, "ROLE_USER"))
                .map(userDetails ->
                    new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities())
                );
            /*
            return accountService
                .findByUsername(token)
                .map(userDetails ->
                    new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities())
                );
            */
            /*
            Account userDetails = getUserDetails(token); // your job to code it

            return Mono.just(new JwtAuthenticationToken(
                userDetails, token, userDetails.getAuthorities()));
            */
        };
    }

    private AuthenticationWebFilter tokenAuthenticationFilter(AccountDao accountDao)
    {
        AuthenticationWebFilter filter =
            new AuthenticationWebFilter(tokenAuthenticationManager(accountDao));

        filter.setAuthenticationConverter(tokenAuthenticationConverter());

        filter.setAuthenticationFailureHandler(
            (exchange, exception) -> Mono.error(exception));

        return filter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http,
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") AccountDao accountDao)
    {
        LOGGER.debug("Initializing the security configuration");
        System.err.println("accountDao = " + accountDao);

        return http
            .authorizeExchange()
            .anyExchange().permitAll()
//            .pathMatchers("/login", "/logout").permitAll()
//            .pathMatchers(HttpMethod.GET, "/css/**", "/and-this-page").permitAll()
//            .pathMatchers("/").hasRole("USER")
            .and()
            .formLogin()
            .loginPage("/login")
            .authenticationFailureHandler((exchange, exception) -> Mono.error(exception))
            .authenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler())
            .and()
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .exceptionHandling()
            .authenticationEntryPoint((exchange, exception) -> Mono.error(exception))
            .accessDeniedHandler((exchange, exception) -> Mono.error(exception))
            .and()
            .csrf().disable()
            .addFilterAt(tokenAuthenticationFilter(accountDao), SecurityWebFiltersOrder.AUTHENTICATION)
            .logout().disable()
            .build();

//        return http.csrf().disable()
//            .authorizeExchange()
//            .pathMatchers("/").hasRole("USER")
//            .anyExchange()
//            .permitAll()
//            .and()
//            .exceptionHandling()
//            .accessDeniedHandler(
//                new HttpStatusServerAccessDeniedHandler(HttpStatus.BAD_REQUEST)
//            )
//            .and()
//            .httpBasic()
//            .and()
//            .build();
    }
}
