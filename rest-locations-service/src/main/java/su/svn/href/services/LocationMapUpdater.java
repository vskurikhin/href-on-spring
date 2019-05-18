package su.svn.href.services;

import reactor.core.publisher.Mono;
import su.svn.href.models.Location;
import su.svn.href.models.UpdateValue;

public interface LocationMapUpdater
{
    Mono<Integer> updateLocation(String field, Location location);
}
