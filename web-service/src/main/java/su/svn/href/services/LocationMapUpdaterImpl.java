package su.svn.href.services;

import org.springframework.stereotype.Service;
import su.svn.href.models.Location;
import su.svn.href.models.UpdateValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class LocationMapUpdaterImpl implements LocationMapUpdater
{
    private Location.Builder getBuilder(UpdateValue<Long> v)
    {
        return Location.builder().setId(v.getPk());
    }

    private Map<String, Function<UpdateValue<Long>, Location>> caseMap()
    {
        return new HashMap<String, Function<UpdateValue<Long>, Location>>()
        {{
            put("STREETADDRESS", v -> getBuilder(v).setStreetAddress(v.getValue()).build());
            put("POSTALCODE",    v -> getBuilder(v).setPostalCode(v.getValue()).build());
            put("CITY",          v -> getBuilder(v).setCity(v.getValue()).build());
            put("STATEPROVINCE", v -> getBuilder(v).setStateProvince(v.getValue()).build());
            put("COUNTRYID",     v -> getBuilder(v).setCountryId(v.getValue()).build());
        }};
    }

    @Override
    public Location updateLocation(UpdateValue<Long> update)
    {
        return caseMap().getOrDefault(update.getName().toUpperCase(), (v) -> null).apply(update);
    }
}
