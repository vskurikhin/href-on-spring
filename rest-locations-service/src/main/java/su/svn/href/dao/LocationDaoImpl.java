package su.svn.href.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LocationDaoImpl implements LocationUpdateDao
{
    private final DatabaseClient databaseClient;

    @Autowired
    public LocationDaoImpl(DatabaseClient databaseClient)
    {
        this.databaseClient = databaseClient;

    }

    public Mono<Integer> updateStreetAddress(Long id, String streetAddress)
    {
        return databaseClient.execute()
            .sql("UPDATE locations SET street_address = $2 WHERE location_id = $1")
            .bind("$1", id)
            .bind("$2", streetAddress)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updatePostalCode(Long id, String postalCode)
    {
        return databaseClient.execute()
            .sql("UPDATE locations SET postal_code = $2 WHERE location_id = $1")
            .bind("$1", id)
            .bind("$2", postalCode)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateCity(Long id, String city)
    {
        return databaseClient.execute()
            .sql("UPDATE locations SET city = $2 WHERE location_id = $1")
            .bind("$1", id)
            .bind("$2", city)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateStateProvince(Long id, String stateProvince)
    {
        return databaseClient.execute()
            .sql("UPDATE locations SET state_province = $2 WHERE location_id = $1")
            .bind("$1", id)
            .bind("$2", stateProvince)
            .fetch()
            .rowsUpdated();
    }

    @Override
    public Mono<Integer> updateCountryId(Long id, String countryId)
    {
        return databaseClient.execute()
            .sql("UPDATE locations SET country_id = $2 WHERE location_id = $1")
            .bind("$1", id)
            .bind("$2", countryId)
            .fetch()
            .rowsUpdated();
    }
}

