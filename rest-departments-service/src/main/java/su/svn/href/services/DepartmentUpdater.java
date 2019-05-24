package su.svn.href.services;

import reactor.core.publisher.Mono;
import su.svn.href.models.Department;

public interface DepartmentUpdater
{
    Mono<Integer> updateDepartment(String field, Department department);
}
