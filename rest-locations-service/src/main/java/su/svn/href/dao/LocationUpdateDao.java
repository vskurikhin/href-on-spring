package su.svn.href.dao;

import reactor.core.publisher.Mono;

public interface LocationUpdateDao
{
    Mono<Integer> updateStreetAddress(Long id, String streetAddress);

    Mono<Integer> updatePostalCode(Long id, String postalCode);

    Mono<Integer> updateCity(Long id, String city);

    Mono<Integer> updateStateProvince(Long id, String stateProvince);

    Mono<Integer> updateCountryId(Long id, String countryId);
}

