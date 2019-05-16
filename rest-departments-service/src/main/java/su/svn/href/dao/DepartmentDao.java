package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Department;

@Repository
public interface DepartmentDao extends ReactiveCrudRepository<Department, Long>, DepartmentUpdateDao
{
    @Query("SELECT * FROM departments OFFSET $1 LIMIT $2")
    Flux<Department> findAll(int offset, int limit);

    @Query("SELECT * FROM departments ORDER BY department_id OFFSET $1 LIMIT $2")
    Flux<Department> findAllOrderById(int offset, int limit);

    @Query("SELECT * FROM departments ORDER BY department_name OFFSET $1 LIMIT $2")
    Flux<Department> findAllOrderByDepartmentName(int offset, int limit);


    @Query("SELECT * FROM departments WHERE department_name LIKE $1")
    Flux<Department> findByDepartmentName(String departmentName);

    @Query("SELECT * FROM departments OFFSET $1 LIMIT $2 WHERE department_name LIKE $3")
    Flux<Department> findByDepartmentName(int offset, int limit, String departmentName);


    @Query("SELECT * FROM departments WHERE manager_id = $1")
    Flux<Department> findByManagerId(Long managerId);

    @Query("SELECT * FROM departments OFFSET $1 LIMIT $2 WHERE manager_id = $3")
    Flux<Department> findByManagerId(int offset, int limit, Long managerId);


    @Query("SELECT * FROM departments WHERE location_id = $3")
    Flux<Department> findByLocationId(Long locationId);

    @Query("SELECT * FROM departments OFFSET $1 LIMIT $2 WHERE location_id = $3")
    Flux<Department> findByLocationId(int offset, int limit, Long locationId);
}

