package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import su.svn.href.dao.RegionDao;
import su.svn.href.models.Region;

@RestController()
@RequestMapping(value = "/regions")
public class RegionsRestController
{
    private RegionDao regionDao;

    @Autowired
    public RegionsRestController(RegionDao regionDao)
    {
        this.regionDao = regionDao;
    }

    @GetMapping("/all")
    public Flux<Region> readRegions()
    {
        return regionDao.findAll();
    }
}
