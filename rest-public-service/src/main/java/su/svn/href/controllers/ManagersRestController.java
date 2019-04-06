package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import su.svn.href.dao.ManagerDao;
import su.svn.href.dao.RegionDao;
import su.svn.href.models.Manager;
import su.svn.href.models.Region;

@RestController()
@RequestMapping(value = "/managers")
public class ManagersRestController
{
    private ManagerDao managerDao;

    @Autowired
    public ManagersRestController(ManagerDao managerDao)
    {
        this.managerDao = managerDao;
    }

    @GetMapping("/all")
    public Flux<Manager> readRegions()
    {
        return managerDao.findAll();
    }
}
