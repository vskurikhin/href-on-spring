package su.svn.href.repository;

import reactor.core.publisher.Flux;
import su.svn.href.models.dto.LocationDto;

public interface LocationRepository
{
    Flux<LocationDto> findAll();
}
