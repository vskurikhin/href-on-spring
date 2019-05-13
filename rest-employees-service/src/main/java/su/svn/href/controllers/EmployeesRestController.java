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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_EMPLOYEES)
public class EmployeesRestController
{
    private EmployeeDao departmentDao;

    private EmployeeFullDao departmentFullDao;

    private PageSettings paging;

    @Autowired
    public EmployeesRestController(EmployeeDao departmentDao,
                                   EmployeeFullDao departmentFullDao,
                                   PageSettings paging)
    {
        this.departmentDao = departmentDao;
        this.departmentFullDao = departmentFullDao;
        this.paging = paging;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createEmployee(@RequestBody Employee department,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
    {
        if (Objects.isNull(department.getId()) || department.getId() < 2) {
            throw new BadValueForEmployeeIdException();
        }

        return departmentDao
            .save(department)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(new EmployeeDontSavedException()));
    }

    @GetMapping(path = REST_RANGE, params = { "page", "size", "sort"})
    public Flux<Employee> readEmployees(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return departmentDao.findAllOrderById(offset, limit);
            case "FIRST_NAME":
                return departmentDao.findAllOrderByFirstName(offset, limit);
            case "LAST_NAME":
                return departmentDao.findAllOrderByLastName(offset, limit);
            case "EMAIL":
                return departmentDao.findAllOrderByEmail(offset, limit);
            case "PHONE_NUMBER":
                return departmentDao.findAllOrderByPhoneNumber(offset, limit);
            case "HIRE_DATE":
                return departmentDao.findAllOrderByHireDate(offset, limit);
            case "SALARY":
                return departmentDao.findAllOrderBySalary(offset, limit);
            case "COMMISSION_PCT":
                return departmentDao.findAllOrderByCommissionPct(offset, limit);
            default:
                return departmentDao.findAll(offset, limit);
        }
    }

    @GetMapping(path = REST_RANGE_FULL, params = { "page", "size", "sort"})
    public Flux<EmployeeDto> readFullEmployees(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam("sort") String sort)
    {
        int limit = paging.getLimit(size);
        int offset = paging.getOffset(page, size);

        switch (sort.toUpperCase()) {
            case "ID":
                return departmentFullDao.findAll(offset, limit, "e.employee_id");
            case "FIRST_NAME":
                return departmentFullDao.findAll(offset, limit, "e.first_name");
            case "LAST_NAME":
                return departmentFullDao.findAll(offset, limit, "e.last_name");
            case "EMAIL":
                return departmentFullDao.findAll(offset, limit, "e.email");
            case "PHONE_NUMBER":
                return departmentFullDao.findAll(offset, limit, "e.phone_number");
            case "HIRE_DATE":
                return departmentFullDao.findAll(offset, limit, "e.hire_date");
            case "SALARY":
                return departmentFullDao.findAll(offset, limit, "e.salary");
            case "COMMISSION_PCT":
                return departmentFullDao.findAll(offset, limit, "e.commission_pct");
            default:
                return departmentFullDao.findAll(offset, limit);
        }
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> readEmployeeById(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForEmployeeIdException();

        return departmentFullDao
            .findById(id)
            .switchIfEmpty(Mono.error(new EmployeeNotFoundException()));
    }

    @PutMapping
    public Mono<? extends Answer> updateEmployee(@RequestBody Employee department)
    {
        if (Objects.isNull(department) || Objects.isNull(department.getId()) || department.getId() < 1) {
            throw new BadValueForEmployeeIdException();
        }

        return departmentDao
            .save(department)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(new EmployeeDontSavedException()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteEmployee(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForEmployeeIdException();
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return departmentDao
            .findById(id)
            .flatMap(department -> departmentDao
                .delete(department)
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

