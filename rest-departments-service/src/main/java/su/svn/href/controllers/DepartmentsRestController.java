package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.exceptions.BadValueForDepartmentIdException;
import su.svn.href.exceptions.DepartmentDontSavedException;
import su.svn.href.exceptions.DepartmentNotFoundException;
import su.svn.href.models.Department;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_DEPARTMENTS)
public class DepartmentsRestController
{
    private DepartmentDao departmentDao;

    private DepartmentFullDao departmentFullDao;

    private PageSettings paging;

    @Autowired
    public DepartmentsRestController(DepartmentDao departmentDao,
                                     DepartmentFullDao departmentFullDao,
                                     PageSettings paging)
    {
        this.departmentDao = departmentDao;
        this.departmentFullDao = departmentFullDao;
        this.paging = paging;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createDepartment(@RequestBody Department department,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response)
    {
        if (Objects.isNull(department.getId()) || department.getId() < 2) {
            throw new BadValueForDepartmentIdException();
        }

        return departmentDao
            .save(department)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(new DepartmentDontSavedException()));
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Department> readDepartments(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return departmentDao.findAllOrderById(offset, limit);
            case "NAME":
                return departmentDao.findAllOrderByDepartmentName(offset, limit);
            default:
                return departmentDao.findAll(offset, limit);
        }
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Mono<List<DepartmentDto>> readFullDepartments(@RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return departmentFullDao.findAll(offset, limit, "d.department_id");
            case "NAME":
                return departmentFullDao.findAll(offset, limit, "department_name");
            default:
                return departmentFullDao.findAll(offset, limit);
        }
    }

    @GetMapping("/{id}")
    public Mono<DepartmentDto> readDepartmentById(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForDepartmentIdException();

        return departmentFullDao
            .findById(id)
            .switchIfEmpty(Mono.error(new DepartmentNotFoundException()));
    }

    @PutMapping
    public Mono<? extends Answer> updateDepartment(@RequestBody Department department)
    {
        if (Objects.isNull(department) || Objects.isNull(department.getId()) || department.getId() < 1) {
            throw new BadValueForDepartmentIdException();
        }

        return departmentDao
            .save(department)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(new DepartmentDontSavedException()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteDepartment(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForDepartmentIdException();
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return departmentDao
            .findById(id)
            .flatMap(department -> departmentDao
                .delete(department)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(new DepartmentNotFoundException())))
            .switchIfEmpty(Mono.error(new DepartmentNotFoundException()));
    }

    @ExceptionHandler(BadValueForDepartmentIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForDepartmentIdException e)
    {
        return new AnswerBadRequest("Bad value for Department Id");
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(DepartmentNotFoundException e)
    {
        return new AnswerBadRequest("Department not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        return new AnswerBadRequest("Bad value for Department");
    }

    @ExceptionHandler(DepartmentDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(DepartmentDontSavedException e)
    {
        return new AnswerBadRequest("Department don't saved");
    }
}
