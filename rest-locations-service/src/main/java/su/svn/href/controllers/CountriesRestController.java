package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.CountryDao;
import su.svn.href.exceptions.BadValueForIdException;
import su.svn.href.exceptions.EntryDontSavedException;
import su.svn.href.exceptions.EntryNotFoundException;
import su.svn.href.models.Country;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.CountryFinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_RANGE;
import static su.svn.href.controllers.Constants.REST_V1_COUNTRIES;

@RestController()
@RequestMapping(value = REST_API + REST_V1_COUNTRIES)
public class CountriesRestController
{
    private static final Log LOG = LogFactory.getLog(CountriesRestController.class);

    private final CountryDao countryDao;

    private final CountryFinder countryFinder;

    private final PageSettings paging;

    @Autowired
    public CountriesRestController(
        CountryDao countryDao,
        @Qualifier("countryCaseFinder") CountryFinder countryFinder,
        PageSettings paging)
    {
        this.countryDao = countryDao;
        this.countryFinder = countryFinder;
        this.paging = paging;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createCountry(
        @RequestBody Country country,
        HttpServletRequest request,
        HttpServletResponse response)
    {
        if (Objects.isNull(country) || ! Country.isValidId(country.getId())) {
            throw new BadValueForIdException(Country.class, "country is: " + country);
        }

        return countryDao
            .save(country)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Country.class, "when creating country: " + country)
            ));
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Country> readCountries(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        return countryFinder.findAllCountries(offset, limit, sort); // TODO check sort
    }

    @GetMapping("/{id}")
    public Mono<Country> readCountryById(@PathVariable String id)
    {
        if ( ! Country.isValidId(id)) {
            throw new BadValueForIdException(Country.class, "id is: " + id);
        }

        return countryDao
            .findById(id)
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Country.class, "for id: " + id)
            ));
    }

    @PutMapping
    public Mono<? extends Answer> updateCountry(@RequestBody Country country)
    {
        if (Objects.isNull(country) || ! Country.isValidId(country.getId())) {
            throw new BadValueForIdException(Country.class, "country is: " + country);
        }

        return countryDao
            .save(country)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Country.class, "when updating country: " + country)
            ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteCountry(@PathVariable String id)
    {
        if (Objects.isNull(id) || ! Country.isValidId(id)) {
            throw new BadValueForIdException(Country.class, "id is: " + id);
        }
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return countryDao
            .findById(id)
            .flatMap(country -> countryDao
                .delete(country)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(
                    new EntryNotFoundException(Country.class, "for id: " + id)
                ))
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Country.class, "for id: " + id)
            )));
    }

    @ExceptionHandler(BadValueForIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForIdException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Country id");
    }

    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(EntryNotFoundException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Country not found for id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Country");
    }

    @ExceptionHandler(EntryDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(EntryDontSavedException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Country don't saved");
    }
}
