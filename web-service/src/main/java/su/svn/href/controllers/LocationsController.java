package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import su.svn.href.repository.LocationRepository;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_UPDATE;
import static su.svn.href.controllers.Constants.REST_V1_LOCATIONS;

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
        model.addAttribute("location", locationRepository.findById(id));
        model.addAttribute("restPath",  REST_API + REST_V1_LOCATIONS + REST_UPDATE);

        return "location";
    }
}