package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;

@Service("locationFinder")
public class LocationCaseFinderImpl implements LocationFinder
{
    private LocationDao locationDao;

    private LocationFullDao locationFullDao;

    @Autowired
    public LocationCaseFinderImpl(LocationDao locationDao, LocationFullDao locationFullDao)
    {
        this.locationDao = locationDao;
        this.locationFullDao = locationFullDao;
    }

    @Override
    public Flux<Location> findAllLocations(int offset, int limit, String sort)
    {
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

    @Override
    public Flux<LocationDto> findAllFullLocations(int offset, int limit, String sort)
    {
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
