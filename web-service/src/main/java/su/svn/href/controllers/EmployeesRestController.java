package su.svn.href.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import su.svn.href.exceptions.BadServiceStatusException;
import su.svn.href.models.Employee;
import su.svn.href.models.UpdateValue;
import su.svn.href.models.dto.*;
import su.svn.href.repository.EmployeeRepository;
import su.svn.href.services.EmployeeMapUpdater;

import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@SuppressWarnings("Duplicates")
@RestController()
@RequestMapping(value = REST_API + REST_V1_EMPLOYEES)
public class EmployeesRestController
{
    private static final Log LOG = LogFactory.getLog(EmployeesRestController.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapUpdater employeeMapUpdater;

    @Autowired
    public EmployeesRestController(EmployeeRepository employeeRepository, EmployeeMapUpdater employeeMapUpdater)
    {
        this.employeeRepository = employeeRepository;
        this.employeeMapUpdater = employeeMapUpdater;
    }

    @GetMapping
    public Mono<EmployeeDataTables> readFullEmployees(
        @RequestParam("draw")   final Integer draw,
        @RequestParam("start")  final Integer start,
        @RequestParam("length") final Integer length,
        @RequestParam("search[value]") final String searchValue,
        @RequestParam("columns[0][search][value]") final String id,
        @RequestParam("columns[1][search][value]") final String firstName,
        @RequestParam("columns[2][search][value]") final String lastName,
        @RequestParam("columns[3][search][value]") final String email,
        @RequestParam("columns[4][search][value]") final String phoneNumber,
        @RequestParam("columns[5][search][value]") final String hireDate,
        @RequestParam("columns[6][search][value]") final String jobId,
        @RequestParam("order[0][column]") final Integer order,
        @RequestParam("order[0][dir]") final String orderDir)
    {
        return employeeRepository
            .findAll(start / length + 1, length)
            .collectList()
            .flatMap(employees ->
                employeeRepository.count().flatMap(count ->
                    Mono.just(new EmployeeDataTables(draw, count, count, employees))
                )
            );
    }

    private Mono<ClientResponse> updateEmployee(UpdateValue<Long> update)
    {
        Employee employee = employeeMapUpdater.updateEmployee(update);

        if (Objects.isNull(employee)) {
            return Mono.empty();
        }
        else {
            return employeeRepository.update(update.getName(), employee);
        }
    }

    private Mono<Boolean> checkResponse(UpdateValue<Long> update)
    {
        return updateEmployee(update).map(response -> response.rawStatusCode() == HttpStatus.OK.value());
    }

    @PostMapping(path = REST_UPDATE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<? extends Answer> updateEmployee(UpdateValueDto body)
    {
        try {
            UpdateValue<Long> update = body.convertWithLongPk();
            Mono<Answer> error = Mono.error(
                new BadServiceStatusException(EmployeeRepository.class, " error when updating employee: " +  body)
            );

            return employeeRepository
                .findById(update.getPk())
                .flatMap(employeeDto -> checkResponse(update))
                .flatMap(result -> result ? Mono.just(new AnswerOk()) : error)
                .switchIfEmpty(error);
        }
        catch (NumberFormatException e) {
            return Mono.error(e);
        }
    }

    @ExceptionHandler(BadServiceStatusException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadServiceStatusException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("can't update");
    }
}

