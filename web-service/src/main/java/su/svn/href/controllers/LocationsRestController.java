package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.*;
import su.svn.href.repository.LocationRepository;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_LOCATIONS)
public class LocationsRestController
{
    private LocationRepository locationRepository;

    @Autowired
    public LocationsRestController(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public Mono<LocationDataTables> readFullLocations(@RequestParam("draw")   final Integer draw,
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
}
