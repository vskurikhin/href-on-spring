package su.svn.href.services;

import reactor.core.publisher.Flux;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.EmployeeDto;

public interface EmployeeFinder
{
    Flux<Employee> findAllEmployees(int offset, int limit, String sort);

    Flux<EmployeeDto> findAllFullEmployees(int offset, int limit, String sort);
}
