package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Region;

public interface RegionDao extends ReactiveCrudRepository<Region, Long>
{
    @Query("SELECT region_id, region_name FROM regions r WHERE r.region_name = $1")
    Flux<Region> findByRegionName(String regionName);
}
