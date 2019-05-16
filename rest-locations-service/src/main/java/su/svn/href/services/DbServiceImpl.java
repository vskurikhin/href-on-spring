package su.svn.href.services;

import org.springframework.stereotype.Service;
import su.svn.href.dao.CountryDao;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.dao.RegionDao;

@Service
public class DbServiceImpl
{
    private final RegionDao regionDao;

    private final CountryDao countryDao;

    private final LocationDao locationDao;

    private final LocationFullDao locationFullDao;

    public DbServiceImpl(RegionDao regionDao,
                         CountryDao countryDao,
                         LocationDao locationDao,
                         LocationFullDao locationFullDao)
    {
        this.regionDao = regionDao;
        this.countryDao = countryDao;
        this.locationDao = locationDao;
        this.locationFullDao = locationFullDao;
    }
}
