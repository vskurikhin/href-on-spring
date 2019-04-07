package su.svn.href.dao;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.href.models.Country;

public interface CountryDao extends ReactiveCrudRepository<Country, String>
{
    @Query("SELECT country_id, country_name, region_id FROM countries OFFSET $1 LIMIT $2")
    Flux<Country> findAll(int offset, int limit);

    @Query("SELECT country_id, country_name, region_id FROM countries OFFSET $1 LIMIT $2")
    Flux<Country> findAllOrderById(int offset, int limit);

    @Query("SELECT country_id, country_name, region_id FROM countries OFFSET $1 LIMIT $2")
    Flux<Country> findAllOrderByCountryName(int offset, int limit);

    @Query("SELECT country_id, country_name, region_id FROM countries c WHERE c.country_name = $1")
    Flux<Country> findByCountryName(String countryName);
}

