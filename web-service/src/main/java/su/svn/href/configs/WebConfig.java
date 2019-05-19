package su.svn.href.configs;

import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.VersionResourceResolver;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

// @Configuration
// @EnableWebSecurity
// @EnableWebFluxSecurity
public class WebConfig
{
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception
    {
        return http
            .authorizeExchange()
            .pathMatchers(HttpMethod.GET, "/posts/**").permitAll()
            .pathMatchers(HttpMethod.DELETE, "/posts/**").hasRole("ADMIN")
            //.pathMatchers("/users/{user}/**").access(this::currentUserMatchesPath)
            .anyExchange().authenticated()
            .and()
            .build();
    }

    private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication, AuthorizationContext context)
    {
        return authentication
            .map(a -> context.getVariables().get("user").equals(a.getName()))
            .map(granted -> new AuthorizationDecision(granted));
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsRepository()
    {
        UserDetails rob = User.withDefaultPasswordEncoder().username("test").password("password").roles("USER").build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("password").roles("USER", "ADMIN").build();
        return new MapReactiveUserDetailsService(rob, admin);
    }
}

/*
Caused by: org.springframework.beans.factory.support.BeanDefinitionOverrideException:
 Invalid bean definition with name 'requestDataValueProcessor' defined in class path resource [org/springframework/security/config/annotation/web/configuration/WebMvcSecurityConfiguration.class]: Cannot register bean definition [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration; factoryMethodName=requestDataValueProcessor; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/security/config/annotation/web/configuration/WebMvcSecurityConfiguration.class]] for bean 'requestDataValueProcessor': There is already [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.security.config.annotation.web.reactive.WebFluxSecurityConfiguration; factoryMethodName=requestDataValueProcessor; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/security/config/annotation/web/reactive/WebFluxSecurityConfiguration.class]] bound.
 */
