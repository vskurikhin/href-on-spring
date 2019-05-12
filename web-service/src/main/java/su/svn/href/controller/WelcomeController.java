package su.svn.href.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController
{
    @RequestMapping("/")
    public String welcome(final Model model)
    {
        return "welcome";
    }
}