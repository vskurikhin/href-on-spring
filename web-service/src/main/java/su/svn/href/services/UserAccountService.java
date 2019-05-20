package su.svn.href.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import su.svn.href.dao.AccountDao;
import su.svn.href.models.Account;
import su.svn.href.models.UserAccount;

@Component
public class UserAccountService implements ReactiveUserDetailsService
{
    private static final PasswordEncoder pw = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private static UserDetails user(String u, String... roles)
    {
        return new UserAccount(
            new Account(u, pw.encode("password"), true), roles
        );
    }

    private static Mono<UserDetails> user(Account a, String... roles)
    {
        return a.isActive() ? Mono.just(new UserAccount(a, roles)) : Mono.empty();
    }


    private final AccountDao accountDao;

    public UserAccountService(AccountDao accountDao)
    {
        this.accountDao = accountDao;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username)
    {
        String encryptedPassword = pw.encode("password");
        System.err.println("encryptedPassword = " + encryptedPassword);

        return accountDao.findByUsername(username).flatMap(a -> user(a, "ROLE_ADMIN", "ROLE_USER"));
    }
}
