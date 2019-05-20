package su.svn.href.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import su.svn.href.dao.AccountDao;

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
        model.addAttribute("account", accountDao.findByUsername("user"));

        return "welcome";
    }

    @RequestMapping("/form-login")
    public String formLogin(final Model model)
    {
        return "form-login";
    }
}