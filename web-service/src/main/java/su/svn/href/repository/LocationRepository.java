package su.svn.href.repository;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;

public interface LocationRepository
{
    Mono<Long> count();

    Flux<Location> findAll(int page, int size);

    Mono<LocationDto> findById(long id);

    Mono<ClientResponse> update(String field, Location location);
}
