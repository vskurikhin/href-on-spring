package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.CountryDao;
import su.svn.href.exceptions.BadValueForCountryIdException;
import su.svn.href.exceptions.CountryDontSavedException;
import su.svn.href.exceptions.CountryNotFoundException;
import su.svn.href.models.Country;
import su.svn.href.models.dto.AnswerBadRequest;
import su.svn.href.models.dto.AnswerCreated;
import su.svn.href.models.helpers.PageSettings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_V1_COUNTRIES;

@RestController()
@RequestMapping(value = REST_API + REST_V1_COUNTRIES)
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

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<AnswerCreated> createCountry(@RequestBody Country country,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
    {
        if (Objects.isNull(country.getId())) throw new BadValueForCountryIdException();

        return countryDao
            .save(country)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(new CountryDontSavedException()));
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

    @ExceptionHandler(BadValueForCountryIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForCountryIdException e)
    {
        return new AnswerBadRequest("Bad value for Region Id");
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(CountryNotFoundException e)
    {
        return new AnswerBadRequest("Region not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        return new AnswerBadRequest("Bad value for Region");
    }

    @ExceptionHandler(CountryDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(CountryDontSavedException e)
    {
        System.out.println("e = " + e);
        return new AnswerBadRequest("Region don't saved");
    }
}
