package su.svn.href.models;

import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.TEST_COUNTRY_NAME;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestData.TEST_SID;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

public class CountryTest
{
    public static Country testCountry = new Country(TEST_SID, TEST_COUNTRY_NAME, TEST_ID);

    public static void testCountriesDb(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS countries (\n"
                + "  country_id      CHAR(2) UNIQUE NOT NULL\n"
                + ", country_name    VARCHAR(40)\n"
                + ", region_id       BIGINT\n"
                + ", CONSTRAINT      country_c_id_pk PRIMARY KEY (country_id)\n"
                + ")"
        );
        client.insert()
            .into(Country.class)
            .using(testCountry)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }
}