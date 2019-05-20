package su.svn.href.controllers;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import su.svn.href.dao.AccountDao;
import su.svn.href.models.UserAccount;

@Controller
public class WelcomeController
{
    private AccountDao accountDao;

    public WelcomeController(AccountDao accountDao)
    {
        this.accountDao = accountDao;
    }

    @RequestMapping("/")
    public String welcome(final Model model)
    {
        return "login";
    }

    @PostMapping("/login")
    public Mono<UserDetails> login(ServerWebExchange exchange) {

        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .cast(UserDetails.class)
            .doOnNext(userDetails -> {
                addTokenHeader(exchange.getResponse(), userDetails); // your job to code it the way you want
            });
    }

    private UserDetails addTokenHeader(ServerHttpResponse response, UserDetails userDetails)
    {
        System.err.println("userDetails = " + userDetails);

        return userDetails;
    }

//    private UserDetails addTokenHeader(ServerHttpResponse response, UserAccount userDetails)
//    {
//        return userDetails;
//    }
}