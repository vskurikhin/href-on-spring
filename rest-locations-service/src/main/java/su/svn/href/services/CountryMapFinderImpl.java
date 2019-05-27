package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.CountryDao;
import su.svn.href.models.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static su.svn.href.regulars.Constants.ID;
import static su.svn.href.regulars.Constants.NAMEID;

@Service("countryMapFinder")
public class CountryMapFinderImpl implements CountryFinder
{
    private final BiFunction<Integer, Integer, Flux<Country> > defaultCase;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<String, BiFunction<Integer, Integer, Flux<Country> > > caseMap;

    @Autowired
    public CountryMapFinderImpl(CountryDao countryDao)
    {
        this.defaultCase = countryDao::findAll;
        this.caseMap =
            new HashMap<String, BiFunction<Integer, Integer, Flux<Country> > >()
            {{
                put(ID,     countryDao::findAllOrderById);
                put(NAMEID, countryDao::findAllOrderByCountryName);
            }};
    }

    @Override
    public Flux<Country> findAllCountries(int offset, int limit, String sort)
    {
        return caseMap.getOrDefault(sort, defaultCase).apply(offset, limit);
    }
}
