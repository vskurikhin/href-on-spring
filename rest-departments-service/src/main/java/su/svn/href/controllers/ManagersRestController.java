package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.ManagerDao;
import su.svn.href.exceptions.BadValueForManagerIdException;
import su.svn.href.exceptions.ManagerNotFoundException;
import su.svn.href.models.Manager;
import su.svn.href.models.dto.AnswerBadRequest;

import java.util.Objects;

import static su.svn.href.controllers.Constants.REST_ALL;
import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_V1_MANAGERS;

@RestController()
@RequestMapping(value = REST_API + REST_V1_MANAGERS)
public class ManagersRestController
{
    private ManagerDao managerDao;

    @Autowired
    public ManagersRestController(ManagerDao managerDao)
    {
        this.managerDao = managerDao;
    }

    @GetMapping(REST_ALL)
    public Flux<Manager> readManagers()
    {
        return managerDao.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Manager> readManagerById(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForManagerIdException();

        return managerDao
            .findById(id)
            .switchIfEmpty(Mono.error(new ManagerNotFoundException()));
    }

    @ExceptionHandler(BadValueForManagerIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForManagerIdException e)
    {
        return new AnswerBadRequest("Bad value for Department Id");
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(ManagerNotFoundException e)
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
}
