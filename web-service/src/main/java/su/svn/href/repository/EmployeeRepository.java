package su.svn.href.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Employee;

public interface EmployeeRepository
{
    Mono<Long> count();

    Flux<Employee> findAll(int page, int size);
}
