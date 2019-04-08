package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Department;

public interface DepartmentDao extends ReactiveCrudRepository<Department, Long>
{
    String OFFSET_LIMIT = " OFFSET $1 LIMIT $2";
    String SELECT = "SELECT department_id, department_name, manager_id, location_id"
                  + " FROM departments d";

    @Query(SELECT + OFFSET_LIMIT)
    Flux<Department> findAll(int offset, int limit);

    @Query(SELECT + " ORDER BY department_id" + OFFSET_LIMIT)
    Flux<Department> findAllOrderById(int offset, int limit);

    @Query(SELECT + " ORDER BY department_name" + OFFSET_LIMIT)
    Flux<Department> findAllOrderByDepartmentName(int offset, int limit);


    @Query(SELECT + OFFSET_LIMIT + " WHERE d.department_name = $3")
    Flux<Department> findByDepartmentName(int offset, int limit, String departmentName);
}
