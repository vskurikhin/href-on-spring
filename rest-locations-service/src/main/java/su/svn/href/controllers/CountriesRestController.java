package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.CountryDao;
import su.svn.href.models.Country;

@RestController()
@RequestMapping(value = "/countries")
public class CountriesRestController
{
    private final CountryDao countryDao;

    @Autowired
    public CountriesRestController(CountryDao countryDao)
    {
        this.countryDao = countryDao;
    }

    @GetMapping(path = "/range", params = { "page", "size" , "sort"})
    public Flux<Country> readCountries(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam("sort") String sort)
    {
        int limit = size < 10 ? 10 : (size > 100 ? 100 : size);
        int offset = (page < 1 ? 0 : page - 1) * size;
        switch (sort.toUpperCase()) {
            case "ID": return countryDao.findAllOrderById(offset, limit);
            case "NAME": return countryDao.findAllOrderByCountryName(offset, limit);
            default: return countryDao.findAll(offset, limit);
        }
    }
}
