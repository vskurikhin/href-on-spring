package su.svn.href.services;

import reactor.core.publisher.Mono;
import su.svn.href.models.Employee;

public interface EmployeeUpdater
{
    Mono<Integer> updateEmployee(String field, Employee employee);
}
