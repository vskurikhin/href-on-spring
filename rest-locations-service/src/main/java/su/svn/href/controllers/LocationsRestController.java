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
import su.svn.href.models.helpers.PageSettings;

@RestController()
@RequestMapping(value = "/locations")
public class LocationsRestController
{
    private LocationDao locationDao;

    private LocationFullDao locationFullDao;

    private PageSettings paging;

    @Autowired
    public LocationsRestController(LocationDao locationDao,
                                   LocationFullDao locationFullDao,
                                   PageSettings paging)
    {
        this.locationDao = locationDao;
        this.locationFullDao = locationFullDao;
        this.paging = paging;
    }

    @GetMapping(path = "/range", params = { "page", "size", "sort"})
    public Flux<Location> readLocations(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

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
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

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
