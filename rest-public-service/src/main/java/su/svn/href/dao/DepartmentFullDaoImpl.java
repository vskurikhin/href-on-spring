package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.*;

import java.util.function.Function;

@Repository
public class DepartmentFullDaoImpl implements DepartmentFullDao
{
    private final DepartmentDao departmentDao;

    private final LocationDao locationDao;

    private final ManagerDao managerDao;

    @Autowired
    public DepartmentFullDaoImpl(DepartmentDao departmentDao, LocationDao locationDao, ManagerDao managerDao)
    {
        this.departmentDao = departmentDao;
        this.locationDao = locationDao;
        this.managerDao = managerDao;
    }

    private Function<Manager, DepartmentFull> toDepartmentFull(Department department, Location location)
    {
        return manager -> new DepartmentFull(
            department.getId(), department.getDepartmentName(), manager, location
        );
    }

    private Function<Location, Mono<DepartmentFull>> monoDepartmentFull(Department department)
    {
        return location -> managerDao
            .findById(department.getManagerId())
            .map(toDepartmentFull(department, location));
    }

    private Mono<? extends DepartmentFull> joinLocation(Department department)
    {
        return locationDao
            .findById(department.getLocationId())
            .flatMap(monoDepartmentFull(department));
    }

    @Override
    public Mono<DepartmentFull> findById(long id)
    {
        return null;
    }

    @Override
    public Flux<DepartmentFull> findAll()
    {
        return departmentDao.findAll().flatMap(this::joinLocation);
    }
}
