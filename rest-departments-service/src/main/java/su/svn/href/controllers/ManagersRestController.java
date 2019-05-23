package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.ManagerDao;
import su.svn.href.exceptions.BadValueForIdException;
import su.svn.href.exceptions.EntryNotFoundException;
import su.svn.href.models.Manager;
import su.svn.href.models.dto.AnswerBadRequest;

import static su.svn.href.controllers.Constants.REST_ALL;
import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_V1_MANAGERS;

@RestController()
@RequestMapping(value = REST_API + REST_V1_MANAGERS)
public class ManagersRestController
{
    private static final Log LOG = LogFactory.getLog(ManagersRestController.class);

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
        if ( ! Manager.isValidId(id)) {
            throw new BadValueForIdException(Manager.class, "id is: " + id);
        }

        return managerDao
            .findById(id)
            .switchIfEmpty(Mono.error(
                new EntryNotFoundException(Manager.class, "for id: " + id)
            ));
    }

    @ExceptionHandler(BadValueForIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForIdException e)
    {
        return new AnswerBadRequest("Bad value for Department Id");
    }

    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(EntryNotFoundException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Department not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        LOG.error(e.getMessage());
        return new AnswerBadRequest("Bad value for Department");
    }
}
