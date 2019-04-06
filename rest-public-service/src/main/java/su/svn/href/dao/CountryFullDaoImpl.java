package su.svn.href.dao;

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

    public CountryFullDaoImpl(CountryDao countryDao, RegionDao regionDao)
    {
        this.countryDao = countryDao;
        this.regionDao = regionDao;
    }

    private Function<Region, CountryFull> toCountryFull(Country country)
    {
        return region -> new CountryFull(country.getId(), country.getCountryName(), region);
    }

    private Mono<? extends CountryFull> apply(Country country)
    {
        return regionDao
            .findById(country.getRegionId())
            .map(toCountryFull(country));
    }

    @Override
    public Mono<CountryFull> findById(Long id)
    {
        return countryDao.findById(id).flatMap(this::apply);
    }

    @Override
    public Flux<CountryFull> findAll()
    {
        return countryDao.findAll().flatMap(this::apply);
    }
}
