package su.svn.href.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.CountryFull;

public interface CountryFullDao
{
    Mono<CountryFull> findById(Long id);

    Flux<CountryFull> findAll();
}
