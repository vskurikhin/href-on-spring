package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.EmployeeDto;

@Repository
public class EmployeeFullDaoImpl implements EmployeeFullDao
{
    public static String SELECT =
        "SELECT e.employee_id, e.first_name, e.last_name, e.email, e.phone_number, e.hire_date, e.job_id, e.salary,"
            + " e.commission_pct, e.manager_id, e.department_id,"
            + " m.employee_id AS manager_id, m.first_name AS manager_first_name, m.last_name AS manager_last_name,"
            + " m.email AS manager_email, m.phone_number AS manager_phone_number,"
            + " d.department_id, d.department_name, d.manager_id AS dept_manager_id, d.location_id"
            + " FROM employees e"
            + " LEFT JOIN employees m ON m.employee_id = e.manager_id"
            + " LEFT JOIN departments d ON d.department_id = e.department_id";

    private final DatabaseClient databaseClient;

    @Autowired
    public EmployeeFullDaoImpl(DatabaseClient databaseClient)
    {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<EmployeeDto> findById(long id)
    {
        return databaseClient.execute()
            .sql(SELECT + " WHERE e.employee_id = $1")
            .bind("$1", id)
            .fetch().first().map(EmployeeDto::collectFromMap);
    }

    @Override
    public Flux<EmployeeDto> findAll(int offset, int limit, String sortBy, boolean descending)
    {
        String direction = descending ? " DESC" : " ASC";
        String orderBy = sortBy != null ? " ORDER BY " + sortBy : "";

        return databaseClient.execute()
            .sql(SELECT + orderBy + direction + " OFFSET $1 LIMIT $2")
            .bind("$1", offset)
            .bind("$2", limit)
            .fetch()
            .all()
            .map(EmployeeDto::collectFromMap);
    }

    @Override
    public Flux<EmployeeDto> findAll(int offset, int limit, String sortBy)
    {
        return findAll(offset, limit, sortBy, false);
    }

    @Override
    public Flux<EmployeeDto> findAll(int offset, int limit)
    {
        return findAll(offset, limit, "e.employee_id" );
    }
}

