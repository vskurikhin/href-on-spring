package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import su.svn.href.models.Account;

public interface AccountDao extends ReactiveCrudRepository<Account, String>
{
    @Query("SELECT * FROM accounts WHERE username = $1")
    Mono<Account> findByUsername(String username);
}
