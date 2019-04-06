package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.models.Department;
import su.svn.href.models.DepartmentFull;

@RestController()
@RequestMapping(value = "/departments")
public class DepartmentsRestController
{
    private DepartmentDao departmentDao;

    private DepartmentFullDao departmentFullDao;

    @Autowired
    public DepartmentsRestController(DepartmentDao departmentDao, DepartmentFullDao departmentFullDao)
    {
        this.departmentDao = departmentDao;
        this.departmentFullDao = departmentFullDao;
    }

    @GetMapping("/all")
    public Flux<Department> readDepartments()
    {
        return departmentDao.findAll();
    }

    @GetMapping("/all-full")
    public Flux<DepartmentFull> readFullDepartments()
    {
        return departmentFullDao.findAll();
    }


    @GetMapping("/{id}")
    public Mono<DepartmentFull> readDepartment(@PathVariable Long id)
    {
        return departmentFullDao.findById(id);
    }
}
