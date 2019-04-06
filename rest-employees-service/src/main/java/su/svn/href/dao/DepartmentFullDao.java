package su.svn.href.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.DepartmentFull;

public interface DepartmentFullDao
{
    Mono<DepartmentFull> findById(long id);

    Flux<DepartmentFull> findAll();
}
