package su.svn.href.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import su.svn.href.exceptions.BadServiceStatusException;
import su.svn.href.models.Location;
import su.svn.href.models.UpdateValue;
import su.svn.href.models.dto.*;
import su.svn.href.repository.LocationRepository;
import su.svn.href.services.LocationMapUpdater;

import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_LOCATIONS)
public class LocationsRestController
{
    private static final Log LOG = LogFactory.getLog(LocationsRestController.class);

    private final LocationRepository locationRepository;

    private final LocationMapUpdater locationMapUpdater;

    @Autowired
    public LocationsRestController(LocationRepository locationRepository, LocationMapUpdater locationMapUpdater)
    {
        this.locationRepository = locationRepository;
        this.locationMapUpdater = locationMapUpdater;
    }

    @GetMapping
    public Mono<LocationDataTables> readFullLocations(
        @RequestParam("draw")   final Integer draw,
        @RequestParam("start")  final Integer start,
        @RequestParam("length") final Integer length,
        @RequestParam("search[value]") final String searchValue,
        @RequestParam("columns[0][search][value]") final String id,
        @RequestParam("columns[1][search][value]") final String streetAddress,
        @RequestParam("columns[2][search][value]") final String postalCode,
        @RequestParam("columns[3][search][value]") final String city,
        @RequestParam("columns[4][search][value]") final String stateProvince,
        @RequestParam("order[0][column]") final Integer order,
        @RequestParam("order[0][dir]") final String orderDir)
    {
        return locationRepository
            .findAll(start / length + 1, length)
            .collectList().flatMap(locationDtos ->
                locationRepository.count().flatMap(count ->
                    Mono.just(new LocationDataTables(draw, count, count, locationDtos))
                )
            );
    }

    private Mono<ClientResponse> updateLocation(UpdateValue<Long> update)
    {
        Location location = locationMapUpdater.updateLocation(update);

        if (Objects.isNull(location)) {
            return Mono.empty();
        }
        else {
            return locationRepository.update(update.getName(), location);
        }
    }

    private Mono<Boolean> checkResponse(UpdateValue<Long> update)
    {
        return updateLocation(update).map(response -> response.rawStatusCode() == HttpStatus.OK.value());
    }

    @PostMapping(path = REST_UPDATE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<? extends Answer> updateLocation(UpdateValueDto body)
    {
        try {
            UpdateValue<Long> update = body.convertWithLongPk();
            Mono<Answer> error = Mono.error(
                new BadServiceStatusException(LocationRepository.class, " error when updating location: " +  body)
            );

            return locationRepository
                .findById(update.getPk())
                .flatMap(locationDto -> checkResponse(update))
                .flatMap(result -> result ? Mono.just(new AnswerOk()) : error)
                .switchIfEmpty(error);
        }
        catch (NumberFormatException e) {
            return Mono.error(e);
        }
    }

    @ExceptionHandler(BadServiceStatusException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadServiceStatusException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("can't update");
    }
}

