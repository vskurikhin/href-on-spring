package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class DepartmentDaoImpl implements DepartmentUpdateDao
{
    private final DatabaseClient databaseClient;

    @Autowired
    public DepartmentDaoImpl(DatabaseClient databaseClient)
    {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Integer> updateDepartmentName(Long id, String departmentName)
    {
        return databaseClient.execute()
            .sql("UPDATE departments SET department_name = $2 WHERE department_id = $1")
            .bind("$1", id)
            .bind("$2", departmentName)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateManagerId(Long id, Long managerId)
    {
        return databaseClient.execute()
            .sql("UPDATE departments SET department_id = $2 WHERE department_id = $1")
            .bind("$1", id)
            .bind("$2", managerId)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateLocationId(Long id, Long locationId)
    {
        return databaseClient.execute()
            .sql("UPDATE departments SET location_id = $2 WHERE department_id = $1")
            .bind("$1", id)
            .bind("$2", locationId)
            .fetch()
            .rowsUpdated();
    }
}
