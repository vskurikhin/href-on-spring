package su.svn.href.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;

import java.util.List;

@Service("departmentCaseFinder")
public class DepartmentCaseFinderImpl implements DepartmentFinder
{
    private final DepartmentDao departmentDao;

    private final DepartmentFullDao departmentFullDao;

    @Autowired
    public DepartmentCaseFinderImpl(DepartmentDao departmentDao, DepartmentFullDao departmentFullDao)
    {
        this.departmentDao = departmentDao;
        this.departmentFullDao = departmentFullDao;
    }

    @Override
    public Flux<Department> findAllDepartments(int offset, int limit, String sort)
    {
        switch (sort.toUpperCase()) {
            case "ID":
                return departmentDao.findAllOrderById(offset, limit);
            case "NAME":
                return departmentDao.findAllOrderByDepartmentName(offset, limit);
            default:
                return departmentDao.findAll(offset, limit);
        }
    }

    @Override
    public Mono<List<DepartmentDto>> findAllFullDepartments(int offset, int limit, String sort)
    {
        switch (sort.toUpperCase()) {
            case "ID":
                return departmentFullDao.findAll(offset, limit, "d.department_id");
            case "NAME":
                return departmentFullDao.findAll(offset, limit, "department_name");
            default:
                return departmentFullDao.findAll(offset, limit);
        }
    }
}
