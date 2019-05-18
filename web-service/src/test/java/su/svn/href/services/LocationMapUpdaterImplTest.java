package su.svn.href.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import su.svn.href.models.Location;
import su.svn.href.models.UpdateValue;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapUpdaterImplTest
{
    public static String POSTALCODE = "postalCode";

    LocationMapUpdater locationMapUpdater;

    @BeforeEach
    void setUp()
    {
        locationMapUpdater = new LocationMapUpdaterImpl();
    }

    @Test
    void test()
    {
        UpdateValue<Long> updateValuePostalCode = new UpdateValue<>(POSTALCODE, 1L, POSTALCODE);

        Location location = locationMapUpdater.updateLocation(updateValuePostalCode);
        assertEquals(POSTALCODE, location.getPostalCode());
    }
}