package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.models.dto.LocationDto;

@Repository
public class LocationFullDaoImpl implements LocationFullDao
{
    private final DatabaseClient databaseClient;

    public static String SELECT =
        "SELECT location_id, street_address, postal_code, city, state_province,"
            + " l.country_id, country_name, c.region_id, region_name"
            + " FROM locations l"
            + " JOIN countries c ON c.country_id = l.country_id"
            + " JOIN regions r ON r.region_id = c.region_id";

    @Autowired
    public LocationFullDaoImpl(DatabaseClient databaseClient)
    {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<LocationDto> findById(long id)
    {
        return databaseClient.execute()
            .sql(SELECT + " WHERE location_id = $1")
            .bind("$1", id)
            .fetch().first().map(LocationDto::collectFromMap);
    }

    @Override
    public Flux<LocationDto> findAll(int offset, int limit)
    {
        return findAll(offset, limit, "location_id" );
    }

    @Override
    public Flux<LocationDto> findAll(int offset, int limit, String sortBy)
    {
        return findAll(offset, limit, sortBy, false);
    }

    @Override
    public Flux<LocationDto> findAll(int offset, int limit, String sortBy, boolean descending)
    {
        String direction = descending ? " DESC" : " ASC";
        String orderBy = sortBy != null ? " ORDER BY " + sortBy : "";

        return databaseClient.execute()
            .sql(SELECT + orderBy + direction + " OFFSET $1 LIMIT $2")
            .bind("$1", offset)
            .bind("$2", limit)
            .fetch()
            .all()
            .map(LocationDto::collectFromMap);
    }
}
