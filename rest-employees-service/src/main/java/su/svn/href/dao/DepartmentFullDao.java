package su.svn.href.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.DepartmentDto;

public interface DepartmentFullDao
{
    Mono<DepartmentDto> findById(long id);

    Flux<DepartmentDto> findAll(int offset, int limit);

    Flux<DepartmentDto> findAll(int offset, int limit, String sortBy);

    Flux<DepartmentDto> findAll(int offset, int limit, String sortBy, boolean descending);
}
