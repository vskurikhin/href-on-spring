package su.svn.href.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import su.svn.href.models.Account;
import su.svn.href.models.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Configuration
public class UserDetailServiceBean
{
    private static final PasswordEncoder pw = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private static UserDetails user(String u, String... roles)
    {
        return new UserAccount(
            new Account(u, pw.encode("password"), true), roles
        );
    }

    private static final Collection<UserDetails> users = new ArrayList<>(
        Arrays.asList(
            user("thor", "ROLE_ADMIN"),
            user("loki", "ROLE_USER"),
            user("zeus", "ROLE_ADMIN", "ROLE_USER")
        ));

    /*
    @Bean
    // @Profile("map-reactive")
    public MapReactiveUserDetailsService userDetailsService()
    {
        return new MapReactiveUserDetailsService(users);
    } */
}
