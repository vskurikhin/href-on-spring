package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public class EmployeeDaoImpl implements EmployeeUpdateDao
{
    private final DatabaseClient databaseClient;

    @Autowired
    public EmployeeDaoImpl(DatabaseClient databaseClient)
    {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Integer> updateFirstName(Long id, String firstName)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET first_name = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", firstName)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateLastName(Long id, String lastName)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET last_name = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", lastName)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateEmail(Long id, String email)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET email = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", email)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updatePhoneNumber(Long id, String phoneNumber)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET phone_number = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", phoneNumber)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateHireDate(Long id, Date hireDate)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET hire_date = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", hireDate)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateJobId(Long id, String jobId)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET job_id = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", jobId)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateSalary(Long id, Double salary)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET salary = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", salary)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateCommissionPct(Long id, Double commissionPct)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET commission_pct = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", commissionPct)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateManagerId(Long id, Long managerId)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET manager_id = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", managerId)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateDepartmentId(Long id, Long departmentId)
    {
        return databaseClient.execute()
            .sql("UPDATE employees SET department_id = $2 WHERE employee_id = $1")
            .bind("$1", id)
            .bind("$2", departmentId)
            .fetch()
            .rowsUpdated();
    }
}
