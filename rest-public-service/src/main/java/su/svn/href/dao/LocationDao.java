package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Location;

public interface LocationDao extends ReactiveCrudRepository<Location, Long>
{
    @Query(
        "SELECT location_id, street_address, postal_code, city, state_province, country_id"
            + " FROM locations c WHERE c.street_address = $1"
    )
    Flux<Location> findByStreetAddress(String streetAddress);

    @Query(
        "SELECT location_id, street_address, postal_code, city, state_province, country_id"
            + " FROM locations c WHERE c.postal_code = $1"
    )
    Flux<Location> findByPostalCode(String postalCode);

    @Query(
        "SELECT location_id, street_address, postal_code, city, state_province, country_id"
            + " FROM locations c WHERE c.city = $1"
    )
    Flux<Location> findByCity(String city);

    @Query(
        "SELECT location_id, street_address, postal_code, city, state_province, country_id"
            + " FROM locations c WHERE c.state_province = $1"
    )
    Flux<Location> findByStateProvince(String stateProvince);
}
