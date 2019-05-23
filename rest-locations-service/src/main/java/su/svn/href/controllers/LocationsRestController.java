package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.exceptions.*;
import su.svn.href.models.Location;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.LocationMapUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_LOCATIONS)
public class LocationsRestController
{
    private static final Log LOG = LogFactory.getLog(CountriesRestController.class);

    private LocationDao locationDao;

    private LocationFullDao locationFullDao;

    private LocationMapUpdater locationMapUpdater;

    private PageSettings paging;

    @Autowired
    public LocationsRestController(
        LocationDao locationDao,
        LocationFullDao locationFullDao,
        LocationMapUpdater locationMapUpdater,
        PageSettings paging)
    {
        this.locationDao = locationDao;
        this.locationFullDao = locationFullDao;
        this.locationMapUpdater = locationMapUpdater;
        this.paging = paging;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createLocation(
        @RequestBody Location location,
        HttpServletRequest request,
        HttpServletResponse response)
    {
        if (Objects.isNull(location.getId()) || location.getId() < 1) {
            throw new BadValueForIdException(Location.class, "location is: " + location);
        }

        return locationDao
            .save(location)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Location.class, "when creating country: " + location)
            ));
    }

    @GetMapping(path = REST_COUNT)
    public Mono<Long> countLocations()
    {
        return locationDao.count();
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Location> readLocations(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) { // TODO
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

    @GetMapping("/{id}")
    public Mono<LocationDto> readFullLocation(@PathVariable Long id)
    {
        if ( ! Location.isValidId(id)) {
            throw new BadValueForIdException(Location.class, "id is: " + id);
        }

        return locationFullDao
            .findById(id)
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Location.class, "for id: " + id)
            ));
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Flux<LocationDto> readFullLocations(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) { // TODO
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
        if (Objects.isNull(location) || ! Location.isValidId(location.getId())) {
            throw new BadValueForIdException(Location.class, "location is: " + location);
        }

        return locationDao
            .save(location)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Location.class, "when updating country: " + location)
            ));
    }

    @PutMapping(path = REST_UPDATE, params = {"field"})
    public Mono<? extends Answer> updateLocationField(
        @RequestParam("field") String field,
        @RequestBody Location location)
    {
        if ( ! Location.isValidFieldName(field.toUpperCase())) {
            throw new BadValueForFieldException(Location.class, "filed name is: " + field);
        }

        return locationMapUpdater.updateLocation(field, location)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Location.class, "when updating location: " + location)
            ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteLocation(@PathVariable Long id)
    {
        if ( ! Location.isValidId(id)) {
            throw new BadValueForIdException(Location.class, "id is: " + id);
        }
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return locationDao
            .findById(id)
            .flatMap(location -> locationDao
                .delete(location)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(
                    new EntryNotFoundException(Location.class, "for id: " + id)
                ))
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Location.class, "for id: " + id)
            )));
    }

    @ExceptionHandler(BadValueForIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForIdException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Location Id");
    }

    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(EntryNotFoundException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Location not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Location");
    }

    @ExceptionHandler(EntryDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(EntryDontSavedException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Location don't saved");
    }
}
