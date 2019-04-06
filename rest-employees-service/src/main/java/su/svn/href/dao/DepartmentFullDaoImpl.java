package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.*;

import java.util.List;
import java.util.function.Function;

@Repository
public class DepartmentFullDaoImpl implements DepartmentFullDao
{
    private final DepartmentDao departmentDao;

    private final EmployeeDao employeeDao;

    private final LocationDao locationDao;

    private final ManagerDao managerDao;

    @Autowired
    public DepartmentFullDaoImpl(DepartmentDao deptDao, EmployeeDao employeeDao, LocationDao locDao, ManagerDao mngrDao)
    {
        this.departmentDao = deptDao;
        this.employeeDao = employeeDao;
        this.locationDao = locDao;
        this.managerDao = mngrDao;
    }

    private Function<List<Employee>, DepartmentFull> toDepartmentFull(Department dept, Location location, Manager mngr)
    {
        return employees -> new DepartmentFull(
            dept.getId(), dept.getDepartmentName(), mngr, location, employees
        );
    }

    private Function<Manager, Mono<DepartmentFull>> monoDepartmentFull(Department department, Location location)
    {
        return manager -> employeeDao
            .findByDepartmentId(department.getId())
            .collectList()
            .map(toDepartmentFull(department, location, manager));
    }

    @Override
    public Mono<DepartmentFull> findById(long id)
    {
        return departmentDao.findById(id).flatMap(department -> locationDao
            .findById(department.getLocationId())
            .flatMap(location -> managerDao
                .findById(department.getManagerId())
                .flatMap(monoDepartmentFull(department, location))));
    }

    @Override
    public Flux<DepartmentFull> findAll()
    {
        return departmentDao.findAll().flatMap(department -> locationDao
            .findById(department.getLocationId())
            .flatMap(location -> managerDao
                .findById(department.getManagerId())
                .flatMap(monoDepartmentFull(department, location))));
    }
}
