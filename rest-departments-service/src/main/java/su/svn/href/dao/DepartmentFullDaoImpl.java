package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.DepartmentDto;

import java.util.Comparator;
import java.util.List;

@Repository
public class DepartmentFullDaoImpl implements DepartmentFullDao
{
    private final DatabaseClient databaseClient;

    private final EmployeeDao employeeDao;

    public static String SELECT =
        "SELECT d.department_id, department_name, d.manager_id,"
            + " m.first_name, m.last_name, m.email, m.phone_number,"
            + " d.location_id, l.street_address, l.postal_code, l.city, l.state_province, l.country_id"
            + " FROM departments d"
            + " LEFT JOIN employees m ON m.employee_id = d.manager_id"
            + " LEFT JOIN locations l ON l.location_id = d.location_id";

    @Autowired
    public DepartmentFullDaoImpl(DatabaseClient databaseClient, EmployeeDao employeeDao)
    {
        this.databaseClient = databaseClient;
        this.employeeDao = employeeDao;
    }

    @Override
    public Mono<DepartmentDto> findById(long id)
    {
        return databaseClient.execute()
            .sql(SELECT + " WHERE d.department_id = $1")
            .bind("$1", id)
            .fetch().first().map(DepartmentDto::collectFromMap);
    }

    @Override
    public Mono<List<DepartmentDto>> findAll(int offset, int limit, String sortBy)
    {
        return findAll(offset, limit, sortBy, false);
    }

    @Override
    public Mono<List<DepartmentDto>> findAll(int offset, int limit)
    {
        return findAll(offset, limit, "department_id");
    }

    private Mono<DepartmentDto> departmentDtoMono(DepartmentDto departmentDto)
    {
        return employeeDao
            .findByDepartmentId(departmentDto.getId())
            .collectList()
            .map(e -> {
                departmentDto.setEmployees(e);
                return departmentDto;
            });
    }

    @Override
    public Mono<List<DepartmentDto>> findAll(int offset, int limit, String sortBy, boolean descending)
    {
        String direction = descending ? " DESC" : " ASC";
        String orderBy = sortBy != null ? " ORDER BY " + sortBy : "";

        Comparator<DepartmentDto> comparator = Comparator.comparingLong(DepartmentDto::getId);

        return databaseClient.execute()
            .sql(SELECT + orderBy + direction + " OFFSET $1 LIMIT $2")
            .bind("$1", offset)
            .bind("$2", limit)
            .fetch()
            .all()
            .map(DepartmentDto::collectFromMap)
            .flatMap(this::departmentDtoMono)
            .collectSortedList(comparator);
    }
}
