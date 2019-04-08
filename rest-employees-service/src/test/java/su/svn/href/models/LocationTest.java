package su.svn.href.models;

import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

public class LocationTest
{
    public static Location testLocation = new Location(
        TEST_ID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );

    private Location location;

    public static void testLocationsDb(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS locations (\n"
                + "  location_id    BIGINT UNIQUE NOT NULL\n"
                + ", street_address VARCHAR(40)\n"
                + ", postal_code    VARCHAR(12)\n"
                + ", city           VARCHAR(30) CONSTRAINT loc_city_nn  NOT NULL\n"
                + ", state_province VARCHAR(25)\n"
                + ", country_id     CHAR(2)\n"
                + ")"
        );

        client.insert()
            .into(Location.class)
            .using(testLocation)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }
}