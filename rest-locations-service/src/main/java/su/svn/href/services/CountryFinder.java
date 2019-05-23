package su.svn.href.services;

import reactor.core.publisher.Flux;
import su.svn.href.models.Country;

public interface CountryFinder
{
    Flux<Country> findAllCountries(int offset, int limit, String sort);
}
