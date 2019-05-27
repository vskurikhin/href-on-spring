package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.exceptions.*;
import su.svn.href.models.Department;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.DepartmentFinder;
import su.svn.href.services.DepartmentUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_DEPARTMENTS)
public class DepartmentsRestController
{
    private static final Log LOG = LogFactory.getLog(DepartmentsRestController.class);

    private final DepartmentDao departmentDao;

    private final DepartmentFullDao departmentFullDao;

    private final DepartmentFinder departmentFinder;

    private final DepartmentUpdater departmentMapUpdater;

    private final PageSettings paging;

    @Autowired
    public DepartmentsRestController(
        DepartmentDao departmentDao,
        DepartmentFullDao departmentFullDao,
        @Qualifier("departmentCaseFinder") DepartmentFinder departmentFinder,
        DepartmentUpdater departmentMapUpdater,
        PageSettings paging)
    {
        this.departmentDao = departmentDao;
        this.departmentFullDao = departmentFullDao;
        this.departmentFinder = departmentFinder;
        this.departmentMapUpdater = departmentMapUpdater;
        this.paging = paging;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createDepartment(
        @RequestBody Department department,
        HttpServletRequest request,
        HttpServletResponse response)
    {
        if (Objects.isNull(department) || ! Department.isValidId(department.getId())) {
            throw new BadValueForIdException(Department.class, "department is: " + department);
        }

        return departmentDao
            .save(department)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Department.class, "when creating department: " + department)
            ));
    }

    @GetMapping(path = REST_COUNT)
    public Mono<Long> countDepartments()
    {
        return departmentDao.count();
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Department> readDepartments(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        return departmentFinder.findAllDepartments(offset, limit, sort); // TODO check sort
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Mono<List<DepartmentDto>> readFullDepartments(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        return departmentFinder.findAllFullDepartments(offset, limit, sort); // TODO check sort
    }

    @GetMapping("/{id}")
    public Mono<DepartmentDto> readDepartmentById(@PathVariable Long id)
    {
        if ( ! Department.isValidId(id)) {
            throw new BadValueForIdException(Department.class, "id is: " + id);
        }

        return departmentFullDao
            .findById(id)
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Department.class, "for id: " + id)
            ));
    }

    @PutMapping
    public Mono<? extends Answer> updateDepartment(@RequestBody Department department)
    {
        if (Objects.isNull(department) || ! Department.isValidId(department.getId())) {
            throw new BadValueForIdException(Department.class, "department is: " + department);
        }

        return departmentDao
            .save(department)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Department.class, "when updating department: " + department)
            ));
    }

    @PutMapping(path = REST_UPDATE, params = {"field"})
    public Mono<? extends Answer> updateDepartmentField(
        @RequestParam("field") String field,
        @RequestBody Department department)
    {
        if ( ! Department.isValidFieldName(field.toUpperCase())) {
            throw new BadValueForFieldException(Department.class, "filed name is: " + field);
        }

        return departmentMapUpdater.updateDepartment(field, department)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Department.class, "when updating department: " + department)
            ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteDepartment(@PathVariable Long id)
    {
        if ( ! Department.isValidId(id)) {
            throw new BadValueForIdException(Department.class, "id is: " + id);
        }
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return departmentDao
            .findById(id)
            .flatMap(department -> departmentDao
                .delete(department)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(
                    new EntryNotFoundException(Department.class, "for id: " + id)
                )))
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Department.class, "for id: " + id)
            ));
    }

    @ExceptionHandler(BadValueForIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForIdException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Department id");
    }

    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(EntryNotFoundException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Department not found for id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Department");
    }

    @ExceptionHandler(EntryDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(EntryDontSavedException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Department don't saved");
    }
}
