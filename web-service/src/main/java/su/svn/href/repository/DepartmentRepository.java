package su.svn.href.repository;

import reactor.core.publisher.Flux;
import su.svn.href.models.dto.DepartmentDto;

public interface DepartmentRepository
{
    Flux<DepartmentDto> findAll();
}
