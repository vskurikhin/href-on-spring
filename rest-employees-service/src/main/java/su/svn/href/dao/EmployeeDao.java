package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Employee;

import java.util.Date;

public interface EmployeeDao extends ReactiveCrudRepository<Employee, Long>
{
    String OFFSET_LIMIT = " OFFSET $1 LIMIT $2";
    String SELECT = "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
                  + " job_id, salary, commission_pct, manager_id, department_id"
                  + " FROM employees";

    @Query(SELECT + OFFSET_LIMIT)
    Flux<Employee> findAll(int offset, int limit);

    @Query(SELECT + " ORDER BY employee_id" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderById(int offset, int limit);

    @Query(SELECT + " ORDER BY first_name" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderByFirstName(int offset, int limit);

    @Query(SELECT + " ORDER BY last_name" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderByLastName(int offset, int limit);

    @Query(SELECT + " ORDER BY email" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderByEmail(int offset, int limit);

    @Query(SELECT + " ORDER BY phone_number" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderByPhoneNumber(int offset, int limit);

    @Query(SELECT + " ORDER BY hire_date" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderByHireDate(int offset, int limit);

    @Query(SELECT + " ORDER BY salary" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderBySalary(int offset, int limit);

    @Query(SELECT + " ORDER BY commission_pct" + OFFSET_LIMIT)
    Flux<Employee> findAllOrderByCommissionPct(int offset, int limit);


    @Query(SELECT + " WHERE employee_id = $3")
    Mono<Employee> findById(long id);

    @Query(SELECT + " WHERE job_id = $3")
    Flux<Employee> findByJobId(String jobId);

    @Query(SELECT + " WHERE manager_id = $1")
    Flux<Employee> findByManagerId(long managerId);

    @Query(SELECT + " WHERE department_id = $1")
    Flux<Employee> findByDepartmentId(long departmentId);


    @Query(SELECT + OFFSET_LIMIT + " WHERE first_name LIKE $3")
    Flux<Employee> findAllByFirstName(int offset, int limit, String firstName);

    @Query(SELECT + OFFSET_LIMIT + " WHERE last_name LIKE $3")
    Flux<Employee> findAllByLastName(int offset, int limit, String lastName);

    @Query(SELECT + OFFSET_LIMIT + " WHERE email LIKE $3")
    Flux<Employee> findAllByEmail(int offset, int limit, String email);

    @Query(SELECT + OFFSET_LIMIT + " WHERE phone_number LIKE $3")
    Flux<Employee> findAllByPhoneNumber(int offset, int limit, String phoneNumber);

    @Query(SELECT + OFFSET_LIMIT + " WHERE hire_date = $3")
    Flux<Employee> findAllByHireDate(int offset, int limit, Date hireDate);

    @Query(SELECT + OFFSET_LIMIT + " WHERE salary = $3")
    Flux<Employee> findAllBySalary(int offset, int limit, Double salary);

    @Query(SELECT + OFFSET_LIMIT + " WHERE commission_pct = $3")
    Flux<Employee> findAllByCommissionPct(int offset, int limit, Double commissionPct);
}
