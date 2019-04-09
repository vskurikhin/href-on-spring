package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;

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

    @GetMapping(path = "/range", params = { "page", "size", "sort"})
    public Flux<Location> readLocations(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam("sort") String sort)
    {
        int limit = size < 10 ? 10 : (size > 100 ? 100 : size);
        int offset = (page < 1 ? 0 : page - 1) * size;
        switch (sort.toUpperCase()) {
            case "ID":
                return locationDao.findAllOrderById(offset, limit);
            case "STREET":
                return locationDao.findAllOrderByStreetAddress(offset, limit);
            case "STATE":
                return locationDao.findAllOrderByStateProvince(offset, limit);
            case "CITY":
                return locationDao.findAllOrderByCity(offset, limit);
            default:
                return locationDao.findAll(offset, limit);
        }
    }

    @GetMapping(path = "/range-full", params = { "page", "size", "sort"})
    public Flux<LocationDto> readFullLocations(@RequestParam("page") int page,
                                               @RequestParam("size") int size,
                                               @RequestParam("sort") String sort)
    {
        int limit = size < 10 ? 10 : (size > 100 ? 100 : size);
        int offset = (page < 1 ? 0 : page - 1) * size;
        switch (sort.toUpperCase()) {
            case "STREET":
                return locationFullDao.findAll(offset, limit, "street_address");
            case "STATE":
                return locationFullDao.findAll(offset, limit, "state_province");
            case "CITY":
                return locationFullDao.findAll(offset, limit, "city");
            default:
                return locationFullDao.findAll(offset, limit);
        }
    }
}
