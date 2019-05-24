package su.svn.href.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;

import java.util.List;

public interface DepartmentFinder
{
    Flux<Department> findAllDepartments(int offset, int limit, String sort);

    Mono<List<DepartmentDto>> findAllFullDepartments(int offset, int limit, String sort);
}
