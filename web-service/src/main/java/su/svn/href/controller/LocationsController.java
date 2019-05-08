package su.svn.href.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import su.svn.href.repository.LocationRepository;

@Controller
public class LocationsController
{
    private LocationRepository locationRepository;

    @Autowired
    public LocationsController(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    @RequestMapping("/")
    public String regions(final Model model)
    {
        IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
            locationRepository.findAll(1, 10), 1
        );
        model.addAttribute("locations", reactiveDataDrivenMode);

        return "locations";
    }
}