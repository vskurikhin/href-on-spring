package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import su.svn.href.dao.LocationDao;
import su.svn.href.models.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static su.svn.href.regulars.Constants.*;

@Service("locationUpdater")
public class LocationMapUpdaterImpl implements LocationUpdater
{
    private final Map<String, Function<Location, Mono<Integer> > > caseMap;

    @Autowired
    public LocationMapUpdaterImpl(LocationDao locationDao)
    {
        this.caseMap =
            new HashMap<String, Function<Location, Mono<Integer>>>()
            {{
                put(STREETADDRESS, l -> locationDao.updateStreetAddress(l.getId(), l.getStreetAddress()));
                put(POSTALCODE,    l -> locationDao.updatePostalCode(l.getId(), l.getPostalCode()));
                put(CITY,          l -> locationDao.updateCity(l.getId(), l.getCity()));
                put(STATEPROVINCE, l -> locationDao.updateStateProvince(l.getId(), l.getStateProvince()));
                put(COUNTRYID,     l -> locationDao.updateCountryId(l.getId(), l.getCountryId()));
            }};
    }

    @Override
    public Mono<Integer> updateLocation(String field, Location location)
    {
        return caseMap.getOrDefault(field.toUpperCase(), l -> Mono.empty()).apply(location);
    }
}
