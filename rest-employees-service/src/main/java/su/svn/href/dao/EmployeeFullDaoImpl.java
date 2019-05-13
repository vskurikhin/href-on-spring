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
    private String SELECT =
        "SELECT e.employee_id, e.first_name, e.last_name, e.email, e.phone_number,"
            + " e.hire_date, e.job_id, e.salary,"
            + " e.commission_pct, e.manager_id, e.department_id,"
            + " m_manager_id, m_first_name, m_last_name, m_email, m_phone_number,"
            + " d_department_id, d_department_name, d_manager_id, d_location_id"
            + " FROM employees e"
            + " LEFT JOIN ("
            + "  SELECT employee_id AS m_manager_id,"
            + "   first_name AS m_first_name,"
            + "   last_name AS m_last_name, email AS m_email,"
            + "   phone_number AS m_phone_number FROM employees"
            + " ) AS m ON m.m_manager_id = e.manager_id"
            + " LEFT JOIN ("
            + "  SELECT department_id AS d_department_id,"
            + "   department_name AS d_department_name,"
            + "   manager_id AS d_manager_id,"
            + "   location_id AS d_location_id FROM departments"
            + " ) AS d ON d.d_department_id = e.department_id";

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

