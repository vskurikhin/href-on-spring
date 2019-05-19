package su.svn.href.services;

import reactor.core.publisher.Mono;
import su.svn.href.models.Employee;

public interface EmployeeMapUpdater
{
    Mono<Integer> updateEmployee(String field, Employee employee);
}
