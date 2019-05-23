package su.svn.href.services;

import reactor.core.publisher.Flux;
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;

public interface LocationFinder
{
    Flux<Location> findAllLocations(int offset, int limit, String sort);

    Flux<LocationDto> findAllFullLocations(int offset, int limit, String sort);
}
