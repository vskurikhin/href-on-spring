package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Manager;

public interface ManagerDao extends ReactiveCrudRepository<Manager, Long>
{
    String SELECT_MNGR_SET = "SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL";

    @Query("SELECT * FROM employees WHERE employee_id = $1 AND employee_id IN (" + SELECT_MNGR_SET + ')')
    Mono<Manager> findById(Long id);

    @Query("SELECT * FROM employees WHERE employee_id IN (" + SELECT_MNGR_SET + ')')
    Flux<Manager> findAll();

    @Query("SELECT * FROM employees WHERE first_name LIKE $1 AND employee_id IN (" + SELECT_MNGR_SET + ')')
    Flux<Manager> findByFirstName(String firstName);

    @Query("SELECT * FROM employees WHERE last_name LIKE $1 AND employee_id IN (" + SELECT_MNGR_SET + ')')
    Flux<Manager> findByLastName(String lastName);

    @Query("SELECT * FROM employees WHERE email LIKE $1 AND employee_id IN (" + SELECT_MNGR_SET + ')')
    Flux<Manager> findByEmail(String email);

    @Query("SELECT * FROM employees WHERE phone_number LIKE $1 AND employee_id IN (" + SELECT_MNGR_SET + ')')
    Flux<Manager> findByPhoneNumber(String phoneNumber);
}

