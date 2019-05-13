package su.svn.href.repository;

import reactor.core.publisher.Flux;
import su.svn.href.models.dto.EmployeeDto;

public interface EmployeeRepository
{
    Flux<EmployeeDto> findAll(int page, int size);
}
