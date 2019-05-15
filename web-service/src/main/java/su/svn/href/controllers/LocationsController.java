package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/locations")
    public String locations()
    {
        return "locations";
    }

    @RequestMapping("/location")
    public String location(@RequestParam long id, final Model model)
    {
//        IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
//            locationRepository.findById(id)
//        );
        model.addAttribute("location", locationRepository.findById(id));

        return "location";
    }
}