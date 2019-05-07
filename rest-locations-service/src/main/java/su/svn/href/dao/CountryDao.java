package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Country;

public interface CountryDao extends ReactiveCrudRepository<Country, String>
{
    String OFFSET_LIMIT = " OFFSET $1 LIMIT $2";
    String SELECT = "SELECT country_id, country_name, region_id"
                  + " FROM countries c";

    @Query(SELECT + OFFSET_LIMIT)
    Flux<Country> findAll(int offset, int limit);

    @Query(SELECT + " ORDER BY c.country_id" + OFFSET_LIMIT)
    Flux<Country> findAllOrderById(int offset, int limit);

    @Query(SELECT + " ORDER BY c.country_name" + OFFSET_LIMIT)
    Flux<Country> findAllOrderByCountryName(int offset, int limit);

    @Query(SELECT + " WHERE c.country_name = $1")
    Flux<Country> findByCountryName(String countryName);
}

