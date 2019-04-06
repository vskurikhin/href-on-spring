package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Country;
import su.svn.href.models.CountryFull;
import su.svn.href.models.Region;

import java.util.function.Function;

@Repository
public class CountryFullDaoImpl implements CountryFullDao
{
    private final CountryDao countryDao;

    private final RegionDao regionDao;

    @Autowired
    public CountryFullDaoImpl(CountryDao countryDao, RegionDao regionDao)
    {
        this.countryDao = countryDao;
        this.regionDao = regionDao;
    }

    private Function<Region, CountryFull> toCountryFull(Country country)
    {
        return region -> new CountryFull(country.getId(), country.getCountryName(), region);
    }

    private Mono<? extends CountryFull> join(Country country)
    {
        return regionDao
            .findById(country.getRegionId())
            .map(toCountryFull(country));
    }

    @Override
    public Mono<CountryFull> findById(String id)
    {
        return countryDao.findById(id).flatMap(this::join);
    }

    @Override
    public Flux<CountryFull> findAll()
    {
        return countryDao.findAll().flatMap(this::join);
    }
}
