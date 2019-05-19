package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.EmployeeDao;
import su.svn.href.dao.EmployeeFullDao;
import su.svn.href.exceptions.BadValueForEmployeeIdException;
import su.svn.href.exceptions.EmployeeDontSavedException;
import su.svn.href.exceptions.EmployeeNotFoundException;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.*;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.EmployeeMapUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_EMPLOYEES)
public class EmployeesRestController
{
    private EmployeeDao employeeDao;

    private EmployeeFullDao employeeFullDao;

    private EmployeeMapUpdater employeeMapUpdater;

    private PageSettings paging;

    @Autowired
    public EmployeesRestController(
        EmployeeDao employeeDao,
        EmployeeFullDao employeeFullDao,
        EmployeeMapUpdater employeeMapUpdater,
        PageSettings paging)
    {
        this.employeeDao = employeeDao;
        this.employeeFullDao = employeeFullDao;
        this.employeeMapUpdater = employeeMapUpdater;
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
        if (Objects.isNull(employee.getId()) || employee.getId() < 2) {
            throw new BadValueForEmployeeIdException();
        }

        return employeeDao
            .save(employee)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(new EmployeeDontSavedException()));
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Employee> readEmployees(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return employeeDao.findAllOrderById(offset, limit);
            case "FIRST_NAME":
                return employeeDao.findAllOrderByFirstName(offset, limit);
            case "LAST_NAME":
                return employeeDao.findAllOrderByLastName(offset, limit);
            case "EMAIL":
                return employeeDao.findAllOrderByEmail(offset, limit);
            case "PHONE_NUMBER":
                return employeeDao.findAllOrderByPhoneNumber(offset, limit);
            case "HIRE_DATE":
                return employeeDao.findAllOrderByHireDate(offset, limit);
            case "SALARY":
                return employeeDao.findAllOrderBySalary(offset, limit);
            case "COMMISSION_PCT":
                return employeeDao.findAllOrderByCommissionPct(offset, limit);
            default:
                return employeeDao.findAll(offset, limit);
        }
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Flux<EmployeeDto> readFullEmployees(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return employeeFullDao.findAll(offset, limit, "e.employee_id");
            case "FIRST_NAME":
                return employeeFullDao.findAll(offset, limit, "e.first_name");
            case "LAST_NAME":
                return employeeFullDao.findAll(offset, limit, "e.last_name");
            case "EMAIL":
                return employeeFullDao.findAll(offset, limit, "e.email");
            case "PHONE_NUMBER":
                return employeeFullDao.findAll(offset, limit, "e.phone_number");
            case "HIRE_DATE":
                return employeeFullDao.findAll(offset, limit, "e.hire_date");
            case "SALARY":
                return employeeFullDao.findAll(offset, limit, "e.salary");
            case "COMMISSION_PCT":
                return employeeFullDao.findAll(offset, limit, "e.commission_pct");
            default:
                return employeeFullDao.findAll(offset, limit);
        }
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> readEmployeeById(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForEmployeeIdException();

        return employeeFullDao
            .findById(id)
            .switchIfEmpty(Mono.error(new EmployeeNotFoundException()));
    }

    @PutMapping
    public Mono<? extends Answer> updateEmployee(@RequestBody Employee employee)
    {
        if (Objects.isNull(employee) || Objects.isNull(employee.getId()) || employee.getId() < 1) {
            throw new BadValueForEmployeeIdException();
        }

        return employeeDao
            .save(employee)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(new EmployeeDontSavedException()));
    }

    @PutMapping(path = REST_UPDATE, params = {"field"})
    public Mono<? extends Answer> updateEmployeeField(
        @RequestParam("field") String field,
        @RequestBody Employee employee)
    {
        return employeeMapUpdater.updateEmployee(field, employee)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(new EmployeeDontSavedException()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteEmployee(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForEmployeeIdException();
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return employeeDao
            .findById(id)
            .flatMap(employee -> employeeDao
                .delete(employee)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException())))
            .switchIfEmpty(Mono.error(new EmployeeNotFoundException()));
    }

    @ExceptionHandler(BadValueForEmployeeIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForEmployeeIdException e)
    {
        return new AnswerBadRequest("Bad value for Employee Id");
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(EmployeeNotFoundException e)
    {
        return new AnswerBadRequest("Employee not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        return new AnswerBadRequest("Bad value for Employee");
    }

    @ExceptionHandler(EmployeeDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(EmployeeDontSavedException e)
    {
        return new AnswerBadRequest("Employee don't saved");
    }
}

