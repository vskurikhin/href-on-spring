package su.svn.href.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.LocationFull;

public interface LocationFullDao
{
    Mono<LocationFull> findById(long id);

    Flux<LocationFull> findAll();
}
