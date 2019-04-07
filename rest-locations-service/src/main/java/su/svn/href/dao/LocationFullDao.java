package su.svn.href.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.LocationDto;

public interface LocationFullDao
{
    Mono<LocationDto> findById(long id);

    Flux<LocationDto> findAll(int offset, int limit);

    Flux<LocationDto> findAll(int offset, int limit, String sortBy);

    Flux<LocationDto> findAll(int offset, int limit, String sortBy, boolean descending);
}
