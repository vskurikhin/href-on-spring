package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Employee;

import java.util.Date;

public interface EmployeeDao extends ReactiveCrudRepository<Employee, Long>
{
    @Query("SELECT * FROM employees")
    Flux<Employee> findAll(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY employee_id OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderById(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY first_name OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderByFirstName(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY last_name OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderByLastName(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY email OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderByEmail(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY phone_number OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderByPhoneNumber(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY hire_date OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderByHireDate(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY salary OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderBySalary(int offset, int limit);

    @Query("SELECT * FROM employees ORDER BY commission_pct OFFSET $1 LIMIT $2")
    Flux<Employee> findAllOrderByCommissionPct(int offset, int limit);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE first_name LIKE $3")
    Flux<Employee> findAllByFirstName(int offset, int limit, String firstName);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE last_name LIKE $3")
    Flux<Employee> findAllByLastName(int offset, int limit, String lastName);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE email LIKE $3")
    Flux<Employee> findAllByEmail(int offset, int limit, String email);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE phone_number LIKE $3")
    Flux<Employee> findAllByPhoneNumber(int offset, int limit, String phoneNumber);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE hire_date = $3") // TODO issue#
    Flux<Employee> findAllByHireDate(int offset, int limit, Date hireDate);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE job_id LIKE $3")
    Flux<Employee> findAllByJobId(int offset, int limit, String jobId);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE salary = $3") // TODO issue#
    Flux<Employee> findAllBySalary(int offset, int limit, Double salary);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE commission_pct = $3") // TODO issue#
    Flux<Employee> findAllByCommissionPct(int offset, int limit, Double commissionPct);


    @Query("SELECT * FROM employees WHERE manager_id = $3")
    Flux<Employee> findByManagerId(Long managerId);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE manager_id = $3")
    Flux<Employee> findByManagerId(int offset, int limit, Long managerId);


    @Query("SELECT * FROM employees WHERE department_id = $1")
    Flux<Employee> findByDepartmentId(Long departmentId);

    @Query("SELECT * FROM employees OFFSET $1 LIMIT $2 WHERE department_id = $3")
    Flux<Employee> findByDepartmentId(int offset, int limit, Long departmentId);
}

