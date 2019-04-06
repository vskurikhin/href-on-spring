package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.Manager;

public interface ManagerDao extends ReactiveCrudRepository<Manager, Long>
{
    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number"
            + " FROM employees e WHERE e.employee_id = $1 AND employee_id IN"
            + " (SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL)"
    )
    Mono<Manager> findById(Long id);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number"
            + " FROM employees e WHERE employee_id IN"
            + " (SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL)"
    )
    Flux<Manager> findAll();

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number"
            + " FROM employees e WHERE e.first_name = $1 AND employee_id IN"
            + " (SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL)"
    )
    Flux<Manager> findByFirstName(String firstName);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number"
            + " FROM employees e WHERE e.last_name = $1 AND employee_id IN"
            + " (SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL)"
    )
    Flux<Manager> findByLastName(String lastName);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number"
            + " FROM employees e WHERE e.email = $1 AND employee_id IN"
            + " (SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL)"
    )
    Flux<Manager> findByEmail(String email);

    @Query(
        "SELECT employee_id, first_name, last_name, email, phone_number"
            + " FROM employees e WHERE e.phone_number = $1 AND employee_id IN"
            + " (SELECT DISTINCT manager_id FROM departments WHERE manager_id IS NOT NULL)"
    )
    Flux<Manager> findByPhoneNumber(String phoneNumber);
}
