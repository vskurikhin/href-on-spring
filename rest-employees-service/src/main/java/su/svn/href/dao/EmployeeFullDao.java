package su.svn.href.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.EmployeeDto;

public interface EmployeeFullDao
{
    Mono<EmployeeDto> findById(long id);

    Flux<EmployeeDto> findAll(int offset, int limit, String sortBy, boolean descending);

    Flux<EmployeeDto> findAll(int offset, int limit, String sortBy);

    Flux<EmployeeDto> findAll(int offset, int limit);
}
