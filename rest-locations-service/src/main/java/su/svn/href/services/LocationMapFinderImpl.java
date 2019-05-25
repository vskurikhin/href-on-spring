package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static su.svn.href.regulars.Constants.*;

@Service("locationMapFinder")
public class LocationMapFinderImpl implements LocationFinder
{
    private final BiFunction<Integer, Integer, Flux<Location> > defaultLocationFinderCase;

    private final BiFunction<Integer, Integer, Flux<LocationDto> > defaultFullLocationFinderCase;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<String, BiFunction<Integer, Integer, Flux<Location> > > caseMapLocationFinders;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<String, BiFunction<Integer, Integer, Flux<LocationDto> > > caseMapFullLocationFinders;

    @Autowired
    public LocationMapFinderImpl(LocationDao locationDao, LocationFullDao locationFullDao)
    {
        this.defaultLocationFinderCase = locationDao::findAll;
        this.defaultFullLocationFinderCase = locationFullDao::findAll;
        this.caseMapLocationFinders =
            new HashMap<String, BiFunction<Integer, Integer, Flux<Location>>>()
            {{
                put(ID,     locationDao::findAllOrderById);
                put(STREET, locationDao::findAllOrderByStreetAddress);
                put(STATE,  locationDao::findAllOrderByStateProvince);
                put(CITY,   locationDao::findAllOrderByCity);
            }};
        this.caseMapFullLocationFinders =
            new HashMap<String, BiFunction<Integer, Integer, Flux<LocationDto>>>()
            {{
                put(ID,     (offset, limit) -> locationFullDao.findAll(offset, limit, "id"));
                put(STREET, (offset, limit) -> locationFullDao.findAll(offset, limit, "street_address"));
                put(STATE,  (offset, limit) -> locationFullDao.findAll(offset, limit, "state_province"));
                put(CITY,   (offset, limit) -> locationFullDao.findAll(offset, limit, "city"));
            }};
    }

    @Override
    public Flux<Location> findAllLocations(int offset, int limit, String sort)
    {
        return caseMapLocationFinders
            .getOrDefault(sort, defaultLocationFinderCase)
            .apply(offset, limit);
    }

    @Override
    public Flux<LocationDto> findAllFullLocations(int offset, int limit, String sort)
    {
        return caseMapFullLocationFinders
            .getOrDefault(sort, defaultFullLocationFinderCase)
            .apply(offset, limit);
    }
}
