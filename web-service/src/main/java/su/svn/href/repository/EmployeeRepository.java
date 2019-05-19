package su.svn.href.repository;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.EmployeeDto;

public interface EmployeeRepository
{
    Mono<Long> count();

    Flux<Employee> findAll(int page, int size);

    Mono<EmployeeDto> findById(long id);

    Mono<ClientResponse> update(String field, Employee employee);
}
