package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;

import java.util.List;

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

    @GetMapping(path = "/range", params = { "page", "size", "sort"})
    public Flux<Department> readDepartments(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sort") String sort)
    {
        int limit = size < 10 ? 10 : (size > 100 ? 100 : size);
        int offset = (page < 1 ? 0 : page - 1) * size;
        switch (sort.toUpperCase()) {
            case "ID":
                return departmentDao.findAllOrderById(offset, limit);
            case "NAME":
                return departmentDao.findAllOrderByDepartmentName(offset, limit);
            default:
                return departmentDao.findAll(offset, limit);
        }
    }

    @GetMapping(path = "/range-full", params = { "page", "size", "sort"})
    public Mono<List<DepartmentDto>> readFullDepartments(@RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam("sort") String sort)
    {
        int limit = size < 10 ? 10 : (size > 100 ? 100 : size);
        int offset = (page < 1 ? 0 : page - 1) * size;
        /*
        switch (sort.toUpperCase()) {
            case "ID1":
                return departmentFullDao.findAll(offset, limit, "d.department_id");
            case "NAME1":
                return departmentFullDao.findAll(offset, limit, "department_name");
            default:
                return departmentFullDao.findAllTry(offset, limit);
        } */
        return departmentFullDao.findAllTry(offset, limit);
    }

    @GetMapping("/{id}")
    public Mono<DepartmentDto> readDepartment(@PathVariable Long id)
    {
        return departmentFullDao.findById(id);
    }
}
