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
import su.svn.href.dao.EmployeeDao;
import su.svn.href.dao.EmployeeFullDao;
import su.svn.href.exceptions.*;
import su.svn.href.models.Department;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.EmployeeFinder;
import su.svn.href.services.EmployeeUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_EMPLOYEES)
public class EmployeesRestController
{
    private static final Log LOG = LogFactory.getLog(EmployeesRestController.class);

    private final EmployeeDao employeeDao;

    private final EmployeeFullDao employeeFullDao;

     private final EmployeeFinder employeeFinder;

    private final EmployeeUpdater employeeUpdater;

    private final PageSettings paging;

    @Autowired
    public EmployeesRestController(
        EmployeeDao employeeDao,
        EmployeeFullDao employeeFullDao,
        @Qualifier("employeeCaseFinder") EmployeeFinder employeeFinder,
        EmployeeUpdater employeeUpdater,
        PageSettings paging)
    {
        this.employeeDao = employeeDao;
        this.employeeFullDao = employeeFullDao;
        this.employeeFinder = employeeFinder;
        this.employeeUpdater = employeeUpdater;
        this.paging = paging;
    }

    @GetMapping(path = REST_COUNT)
    public Mono<Long> countEmployees()
    {
        return employeeDao.count();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createEmployee(
        @RequestBody Employee employee,
        HttpServletRequest request,
        HttpServletResponse response)
    {
        if (Objects.isNull(employee) || ! Employee.isValidId(employee.getId())) {
            throw new BadValueForIdException(Employee.class, "employee is: " + employee);
        }

        return employeeDao
            .save(employee)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Employee.class, "when creating employee: " + employee)
            ));
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Employee> readEmployees(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        return employeeFinder.findAllEmployees(offset, limit, sort);
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Flux<EmployeeDto> readFullEmployees(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        return employeeFinder.findAllFullEmployees(offset, limit, sort);
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> readEmployeeById(@PathVariable Long id)
    {
        if ( ! Employee.isValidId(id)) {
            throw new BadValueForIdException(Employee.class, "id is: " + id);
        }

        return employeeFullDao
            .findById(id)
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Employee.class, "for id: " + id)
            ));
    }

    @PutMapping
    public Mono<? extends Answer> updateEmployee(@RequestBody Employee employee)
    {
        if (Objects.isNull(employee) || ! Employee.isValidId(employee.getId())) {
            throw new BadValueForIdException(Employee.class, "employee is: " + employee);
        }

        return employeeDao
            .save(employee)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Employee.class, "when updating employee: " + employee)
            ));
    }

    @PutMapping(path = REST_UPDATE, params = {"field"})
    public Mono<? extends Answer> updateEmployeeField(
        @RequestParam("field") String field,
        @RequestBody Employee employee)
    {
        if ( ! Department.isValidFieldName(field.toUpperCase())) {
            throw new BadValueForFieldException(Department.class, "filed name is: " + field);
        }

        return employeeUpdater.updateEmployee(field, employee)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(
                new EntryDontSavedException(Employee.class, "when updating employee: " + employee)
            ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteEmployee(@PathVariable Long id)
    {
        if ( ! Employee.isValidId(id)) {
            throw new BadValueForIdException(Employee.class, "id is: " + id);
        }
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return employeeDao
            .findById(id)
            .flatMap(employee -> employeeDao
                .delete(employee)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(
                    new EntryNotFoundException(Employee.class, "for id: " + id)
                )))
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Employee.class, "for id: " + id)
            ));
    }

    @ExceptionHandler(BadValueForIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForIdException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Employee Id");
    }

    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(EntryNotFoundException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Employee not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Employee");
    }

    @ExceptionHandler(EntryDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(EntryDontSavedException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Employee don't saved");
    }
}

