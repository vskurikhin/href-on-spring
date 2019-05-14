package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.exceptions.BadValueForLocationIdException;
import su.svn.href.exceptions.LocationDontSavedException;
import su.svn.href.exceptions.LocationNotFoundException;
import su.svn.href.models.Location;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_LOCATIONS)
public class LocationsRestController
{
    private LocationDao locationDao;

    private LocationFullDao locationFullDao;

    private PageSettings paging;

    @Autowired
    public LocationsRestController(LocationDao locationDao,
                                   LocationFullDao locationFullDao,
                                   PageSettings paging)
    {
        this.locationDao = locationDao;
        this.locationFullDao = locationFullDao;
        this.paging = paging;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createLocation(@RequestBody Location location,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
    {
        if (Objects.isNull(location.getId()) || location.getId() < 1) {
            throw new BadValueForLocationIdException();
        }

        return locationDao
            .save(location)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(new LocationDontSavedException()));
    }

    @GetMapping(path = REST_COUNT)
    public Mono<Long> countLocations()
    {
        return locationDao.count();

    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Location> readLocations(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return locationDao.findAllOrderById(offset, limit);
            case "STREET":
                return locationDao.findAllOrderByStreetAddress(offset, limit);
            case "STATE":
                return locationDao.findAllOrderByStateProvince(offset, limit);
            case "CITY":
                return locationDao.findAllOrderByCity(offset, limit);
            default:
                return locationDao.findAll(offset, limit);
        }
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Flux<LocationDto> readFullLocations(@RequestParam("page") int page,
                                               @RequestParam("size") int size,
                                               @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "STREET":
                return locationFullDao.findAll(offset, limit, "street_address");
            case "STATE":
                return locationFullDao.findAll(offset, limit, "state_province");
            case "CITY":
                return locationFullDao.findAll(offset, limit, "city");
            default:
                return locationFullDao.findAll(offset, limit);
        }
    }

    @PutMapping
    public Mono<? extends Answer> updateLocation(@RequestBody Location location)
    {
        if (Objects.isNull(location) || Objects.isNull(location.getId()) || location.getId() < 1) {
            throw new BadValueForLocationIdException();
        }

        return locationDao
            .save(location)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(new LocationDontSavedException()));
    }

    @PutMapping(path = REST_UPDATE, params = {"field"})
    public Mono<? extends Answer> updateLocation(@RequestParam("field") String field, @RequestBody Location location)
    {
        if (Objects.isNull(location) || Objects.isNull(location.getId()) || location.getId() < 1) {
            throw new BadValueForLocationIdException();
        }

        switch (field.toUpperCase()) {
            case "STREET_ADDRESS":
                return locationDao
                    .updateStreetAddress(location.getId(), location.getStreetAddress())
                    .map(r -> new AnswerOk())
                    .switchIfEmpty(Mono.error(new LocationDontSavedException()));
            case "POSTAL_CODE":
                return locationDao
                    .updatePostalCode(location.getId(), location.getPostalCode())
                    .map(r -> new AnswerOk())
                    .switchIfEmpty(Mono.error(new LocationDontSavedException()));
            case "CITY":
                return locationDao
                    .updateCity(location.getId(), location.getCity())
                    .map(r -> new AnswerOk())
                    .switchIfEmpty(Mono.error(new LocationDontSavedException()));
            case "STATE_PROVINCE":
                return locationDao
                    .updateStateProvince(location.getId(), location.getStateProvince())
                    .map(r -> new AnswerOk())
                    .switchIfEmpty(Mono.error(new LocationDontSavedException()));
            case "COUNTRY_ID":
                return locationDao
                    .updateCountryId(location.getId(), location.getCountryId())
                    .map(r -> new AnswerOk())
                    .switchIfEmpty(Mono.error(new LocationDontSavedException()));
            default:
                return Mono.error(new LocationDontSavedException());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteLocation(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForLocationIdException();
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return locationDao
            .findById(id)
            .flatMap(location -> locationDao
                .delete(location)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(new LocationNotFoundException())))
            .switchIfEmpty(Mono.error(new LocationNotFoundException()));
    }

    @ExceptionHandler(BadValueForLocationIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForLocationIdException e)
    {
        return new AnswerBadRequest("Bad value for Location Id");
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(LocationNotFoundException e)
    {
        return new AnswerBadRequest("Location not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        return new AnswerBadRequest("Bad value for Location");
    }

    @ExceptionHandler(LocationDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(LocationDontSavedException e)
    {
        return new AnswerBadRequest("Location don't saved");
    }
}
