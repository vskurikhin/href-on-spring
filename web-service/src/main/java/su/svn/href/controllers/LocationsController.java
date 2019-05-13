package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String locations(final Model model)
    {
        return "locations";
    }
}