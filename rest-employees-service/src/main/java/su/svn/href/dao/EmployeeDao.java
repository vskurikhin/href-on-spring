package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Employee;

public interface EmployeeDao extends ReactiveCrudRepository<Employee, Long>
{
    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
            + " job_id, salary, commission_pct, manager_id, department_id"
            + " FROM employees e WHERE e.employee_id = $1"
    )
    Mono<Employee> findById(Long id);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
            + " job_id, salary, commission_pct, manager_id, department_id"
            + " FROM employees e"
    )
    Flux<Employee> findAll();

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
            + " job_id, salary, commission_pct, manager_id, department_id"
            + " FROM employees e WHERE e.first_name = $1"
    )
    Flux<Employee> findByFirstName(String firstName);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
            + " job_id, salary, commission_pct, manager_id, department_id"
            + " FROM employees e WHERE e.last_name = $1"
    )
    Flux<Employee> findByLastName(String lastName);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
            + " job_id, salary, commission_pct, manager_id, department_id"
            + " FROM employees e WHERE e.email = $1"
    )
    Flux<Employee> findByEmail(String email);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number, hire_date,"
            + " job_id, salary, commission_pct, manager_id, department_id"
            + " FROM employees e WHERE e.phone_number = $1"
    )
    Flux<Employee> findByPhoneNumber(String phoneNumber);
}
