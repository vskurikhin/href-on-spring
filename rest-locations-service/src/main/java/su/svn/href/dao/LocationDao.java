package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Location;

public interface LocationDao extends ReactiveCrudRepository<Location, Long>
{
    String OFFSET_LIMIT = " OFFSET $1 LIMIT $2";
    String SELECT = "SELECT location_id, street_address, postal_code, city, state_province, country_id"
                  + " FROM locations l";

    @Query(SELECT + OFFSET_LIMIT)
    Flux<Location> findAll(int offset, int limit);

    @Query(SELECT + " ORDER BY l.location_id" + OFFSET_LIMIT)
    Flux<Location> findAllOrderById(int offset, int limit);

    @Query(SELECT + " ORDER BY l.street_address" + OFFSET_LIMIT)
    Flux<Location> findAllOrderByStreetAddress(int offset, int limit);

    @Query(SELECT + " ORDER BY l.city" + OFFSET_LIMIT)
    Flux<Location> findAllOrderByCity(int offset, int limit);

    @Query(SELECT + " ORDER BY l.state_province" + OFFSET_LIMIT)
    Flux<Location> findAllOrderByStateProvince(int offset, int limit);


    @Query(SELECT + OFFSET_LIMIT + " WHERE l.street_address = $3")
    Flux<Location> findByStreetAddress(int offset, int limit, String streetAddress);

    @Query(SELECT + OFFSET_LIMIT + " WHERE l.postal_code = $3")
    Flux<Location> findByPostalCode(int offset, int limit, String postalCode);

    @Query(SELECT + OFFSET_LIMIT + " WHERE l.city = $3")
    Flux<Location> findByCity(int offset, int limit, String city);

    @Query(SELECT + OFFSET_LIMIT + " WHERE l.state_province = $3")
    Flux<Location> findByStateProvince(int offset, int limit, String stateProvince);
}
