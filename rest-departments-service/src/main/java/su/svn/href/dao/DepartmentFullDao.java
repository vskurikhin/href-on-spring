package su.svn.href.dao;

import reactor.core.publisher.Mono;
import su.svn.href.models.dto.DepartmentDto;

import java.util.List;

public interface DepartmentFullDao
{
    Mono<DepartmentDto> findById(long id);

    Mono<List<DepartmentDto>> findAll(int offset, int limit, String sortBy, boolean descending);

    Mono<List<DepartmentDto>> findAll(int offset, int limit, String sortBy);

    Mono<List<DepartmentDto>> findAll(int offset, int limit);
}
