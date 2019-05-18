package su.svn.href.dao;

import reactor.core.publisher.Mono;

public interface DepartmentUpdateDao
{
    Mono<Integer> updateDepartmentName(Long id, String departmentName);

    Mono<Integer> updateManagerId(Long id, Long managerId);

    Mono<Integer> updateLocationId(Long id, Long locationId);
}

