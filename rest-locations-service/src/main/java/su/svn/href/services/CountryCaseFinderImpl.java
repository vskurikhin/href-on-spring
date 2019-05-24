package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import su.svn.href.dao.CountryDao;
import su.svn.href.models.Country;

@Service("countryCaseFinder")
public class CountryCaseFinderImpl implements CountryFinder
{
    private CountryDao countryDao;

    @Autowired
    public CountryCaseFinderImpl(CountryDao countryDao)
    {
        this.countryDao = countryDao;
    }

    @Override
    public Flux<Country> findAllCountries(int offset, int limit, String sort)
    {
        switch (sort.toUpperCase()) {
            case "ID":   return countryDao.findAllOrderById(offset, limit);
            case "NAME": return countryDao.findAllOrderByCountryName(offset, limit);
            default:     return countryDao.findAll(offset, limit);
        }
    }
}
