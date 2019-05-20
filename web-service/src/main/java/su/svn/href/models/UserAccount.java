package su.svn.href.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Slf4j
public class UserAccount implements UserDetails
{
    private final Account account;

    Collection<GrantedAuthority> authorities;

    public UserAccount(Account account, String[] roles)
    {
        this.authorities = Arrays.asList(roles)
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.authorities;
    }

    @Override
    public String getPassword()
    {
        return account.getPassword();
    }

    @Override
    public String getUsername()
    {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return account.isActive();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return account.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return account.isActive();
    }

    @Override
    public boolean isEnabled()
    {
        return account.isActive();
    }
}
