package su.svn.href.services;

import su.svn.href.models.Location;
import su.svn.href.models.UpdateValue;

public interface LocationMapUpdater
{
    Location updateLocation(UpdateValue<Long> update);
}
