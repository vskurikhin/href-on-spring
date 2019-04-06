package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.CountryDao;
import su.svn.href.dao.CountryFullDao;
import su.svn.href.models.Country;
import su.svn.href.models.CountryFull;

@RestController()
@RequestMapping(value = "/countries")
public class CountriesRestController
{
    private final CountryDao countryDao;

    private final CountryFullDao countryFullDao;

    @Autowired
    public CountriesRestController(CountryDao countryDao, CountryFullDao countryFullDao)
    {
        this.countryDao = countryDao;
        this.countryFullDao = countryFullDao;
    }

    @GetMapping("/all")
    public Flux<Country> readCountries()
    {
        return countryDao.findAll();
    }

    @GetMapping("/all-full")
    public Flux<CountryFull> readFullCountries()
    {
        return countryFullDao.findAll();
    }
}
