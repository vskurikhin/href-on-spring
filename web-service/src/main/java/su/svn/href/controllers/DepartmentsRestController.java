package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import su.svn.href.models.Department;
import su.svn.href.models.UpdateValue;
import su.svn.href.models.dto.*;
import su.svn.href.repository.DepartmentRepository;
import su.svn.href.services.DepartmentMapUpdater;

import java.util.Objects;

import static su.svn.href.controllers.Constants.*;

@SuppressWarnings("Duplicates")
@RestController()
@RequestMapping(value = REST_API + REST_V1_DEPARTMENTS)
public class DepartmentsRestController
{
    private DepartmentRepository departmentRepository;

    private DepartmentMapUpdater departmentMapUpdater;

    @Autowired
    public DepartmentsRestController(DepartmentRepository departmentRepository, DepartmentMapUpdater departmentMapUpdater)
    {
        this.departmentRepository = departmentRepository;
        this.departmentMapUpdater = departmentMapUpdater;
    }

    @GetMapping
    public Mono<DepartmentDataTables> readFullDepartments(
        @RequestParam("draw")   final Integer draw,
        @RequestParam("start")  final Integer start,
        @RequestParam("length") final Integer length,
        @RequestParam("search[value]") final String searchValue,
        @RequestParam("columns[0][search][value]") final String id,
        @RequestParam("columns[1][search][value]") final String departmentName,
        @RequestParam("columns[2][search][value]") final String managerId,
        @RequestParam("columns[3][search][value]") final String locationId,
        @RequestParam("order[0][column]") final Integer order,
        @RequestParam("order[0][dir]") final String orderDir)
    {
        return departmentRepository
            .findAll(start / length + 1, length)
            .collectList().flatMap(locationDtos ->
                departmentRepository.count().flatMap(count ->
                    Mono.just(new DepartmentDataTables(draw, count, count, locationDtos))
                )
            );
    }

    private Mono<ClientResponse> updateDepartment(UpdateValue<Long> update)
    {
        Department location = departmentMapUpdater.updateDepartment(update);

        if (Objects.isNull(location)) {
            return Mono.empty();
        }
        else {
            return departmentRepository.update(update.getName(), location);
        }
    }

    private Mono<Boolean> checkResponse(UpdateValue<Long> update)
    {
        return updateDepartment(update).map(response -> response.rawStatusCode() == HttpStatus.OK.value());
    }

    @PostMapping(path = REST_UPDATE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<? extends Answer> updateDepartment(UpdateValueDto body)
    {
        System.out.println("body = " + body);
        try {
            UpdateValue<Long> update = body.convertWithLongPk();
            Mono<Answer> error = Mono.error(new RuntimeException()); // TODO

            return departmentRepository
                .findById(update.getPk())
                .flatMap(locationDto -> checkResponse(update))
                .flatMap(result -> result ? Mono.just(new AnswerOk()) : error)
                .switchIfEmpty(error);
        }
        catch (NumberFormatException e) {
            return Mono.error(e);
        }
    }
}

