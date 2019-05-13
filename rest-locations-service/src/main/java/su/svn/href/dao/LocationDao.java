package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Location;

public interface LocationDao extends ReactiveCrudRepository<Location, Long>
{
    @Query("SELECT * FROM locations  OFFSET $1 LIMIT $2")
    Flux<Location> findAll(int offset, int limit);

    @Query("SELECT * FROM locations ORDER BY location_id OFFSET $1 LIMIT $2")
    Flux<Location> findAllOrderById(int offset, int limit);

    @Query("SELECT * FROM locations ORDER BY street_address OFFSET $1 LIMIT $2")
    Flux<Location> findAllOrderByStreetAddress(int offset, int limit);

    @Query("SELECT * FROM locations ORDER BY city OFFSET $1 LIMIT $2")
    Flux<Location> findAllOrderByCity(int offset, int limit);

    @Query("SELECT * FROM locations ORDER BY state_province OFFSET $1 LIMIT $2")
    Flux<Location> findAllOrderByStateProvince(int offset, int limit);


    @Query("SELECT * FROM locations OFFSET $1 LIMIT $2 WHERE street_address LIKE $3")
    Flux<Location> findByStreetAddress(int offset, int limit, String streetAddress);

    @Query("SELECT * FROM locations OFFSET $1 LIMIT $2 WHERE postal_code LIKE $3")
    Flux<Location> findByPostalCode(int offset, int limit, String postalCode);

    @Query("SELECT * FROM locations OFFSET $1 LIMIT $2 WHERE city LIKE $3")
    Flux<Location> findByCity(int offset, int limit, String city);

    @Query("SELECT * FROM locations OFFSET $1 LIMIT $2 WHERE state_province LIKE $3")
    Flux<Location> findByStateProvince(int offset, int limit, String stateProvince);
}

