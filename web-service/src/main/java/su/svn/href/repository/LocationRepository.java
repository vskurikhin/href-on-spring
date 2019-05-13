package su.svn.href.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Location;

public interface LocationRepository
{
    Mono<Long> count();

    Flux<Location> findAll(int page, int size);
}
