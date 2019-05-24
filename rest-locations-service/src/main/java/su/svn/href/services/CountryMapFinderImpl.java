package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.CountryDao;
import su.svn.href.models.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Service("countryMapFinder")
public class CountryMapFinderImpl implements CountryFinder
{
    private CountryDao countryDao;

    private BiFunction<Integer, Integer, Flux<Country> > defaultCase;

    @Autowired
    public CountryMapFinderImpl(CountryDao countryDao)
    {
        this.countryDao = countryDao;
        this.defaultCase = countryDao::findAll;
    }

    private Map<String, BiFunction<Integer, Integer, Flux<Country> > > caseMap()
    {
        return new HashMap<String, BiFunction<Integer, Integer, Flux<Country> > >()
        {{
            put("ID",      (offset, limit) -> countryDao.findAllOrderById(offset, limit));
            put("NAME-ID", (offset, limit) -> countryDao.findAllOrderByCountryName(offset, limit));
        }};
    }

    @Override
    public Flux<Country> findAllCountries(int offset, int limit, String sort)
    {
        return caseMap().getOrDefault(sort, defaultCase).apply(offset, limit);
    }
}
