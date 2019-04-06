package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.models.Location;
import su.svn.href.models.LocationFull;

@RestController()
@RequestMapping(value = "/locations")
public class LocationsRestController
{
    private LocationDao locationDao;

    private LocationFullDao locationFullDao;

    @Autowired
    public LocationsRestController(LocationDao locationDao, LocationFullDao locationFullDao)
    {
        this.locationDao = locationDao;
        this.locationFullDao = locationFullDao;
    }

    @GetMapping("/all")
    public Flux<Location> readLocations()
    {
        return locationDao.findAll();
    }

    @GetMapping("/all-full")
    public Flux<LocationFull> readFullLocations()
    {
        return locationFullDao.findAll();
    }
}
