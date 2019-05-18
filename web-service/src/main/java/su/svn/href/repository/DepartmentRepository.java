package su.svn.href.repository;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;

public interface DepartmentRepository
{
    Mono<Long> count();

    Flux<Department> findAll(int page, int size);

    Mono<DepartmentDto> findById(long id);

    Mono<ClientResponse> update(String field, Department department);
}
