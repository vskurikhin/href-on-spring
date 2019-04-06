package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.LocationDao;
import su.svn.href.models.Location;

@RestController()
@RequestMapping(value = "/locations")
public class LocationsRestController
{
    private LocationDao locationDao;

    @Autowired
    public LocationsRestController(LocationDao locationDao)
    {
        this.locationDao = locationDao;
    }

    @GetMapping("/all")
    public Flux<Location> readLocations()
    {
        return locationDao.findAll();
    }
}
