package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.CountryDao;
import su.svn.href.models.Country;
import su.svn.href.models.helpers.PageSettings;

@RestController()
@RequestMapping(value = "/countries")
public class CountriesRestController
{
    private final CountryDao countryDao;

    private final PageSettings paging;

    @Autowired
    public CountriesRestController(CountryDao countryDao, PageSettings paging)
    {
        this.countryDao = countryDao;
        this.paging = paging;
    }

    @GetMapping(path = "/range", params = { "page", "size", "sort"})
    public Flux<Country> readCountries(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":   return countryDao.findAllOrderById(offset, limit);
            case "NAME": return countryDao.findAllOrderByCountryName(offset, limit);
            default:     return countryDao.findAll(offset, limit);
        }
    }
}
