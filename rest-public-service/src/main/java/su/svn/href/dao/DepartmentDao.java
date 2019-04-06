package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Department;

public interface DepartmentDao extends ReactiveCrudRepository<Department, Long>
{
    @Query(
        "SELECT department_id, department_name, manager_id, location_id"
            + " FROM departments d WHERE d.department_name = $1"
    )
    Flux<Department> findByDepartmentName(String departmentName);
}
