package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.*;

import java.util.function.Function;

@Repository
public class LocationFullDaoImpl implements LocationFullDao
{
    private final CountryFullDao countryDao;

    private final LocationDao locationDao;

    @Autowired
    public LocationFullDaoImpl(CountryFullDao countryFullDao, LocationDao locationDao)
    {
        this.countryDao = countryFullDao;
        this.locationDao = locationDao;
    }

    private Function<CountryFull, LocationFull> toLocationFull(Location location)
    {
        return country -> new LocationFull(
            location.getId(),
            location.getStreetAddress(),
            location.getPostalCode(),
            location.getCity(),
            location.getStateProvince(),
            country
        );
    }

    private Mono<? extends LocationFull> join(Location location)
    {
        return countryDao
            .findById(location.getCountryId())
            .map(toLocationFull(location));
    }

    @Override
    public Mono<LocationFull> findById(long id)
    {
        return locationDao.findById(id).flatMap(this::join);
    }

    @Override
    public Flux<LocationFull> findAll()
    {
        return locationDao.findAll().flatMap(
            location -> countryDao
                .findById(location.getCountryId())
                .map(toLocationFull(location))
        );
    }
}
