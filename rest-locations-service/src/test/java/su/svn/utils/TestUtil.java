package su.svn.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;
import su.svn.href.models.Country;
import su.svn.href.models.Location;
import su.svn.href.models.Region;

import java.io.IOException;

import static su.svn.utils.TestData.testCountry;
import static su.svn.utils.TestData.testLocation;
import static su.svn.utils.TestData.testRegion;

public class TestUtil
{
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append("a");
        }

        return builder.toString();
    }

    public static void databaseClientExecuteSql(DatabaseClient client, String sql)
    {
        client.execute()
            .sql(sql)
            .fetch()
            .rowsUpdated()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }

    public static void createTestTableForRegions(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS regions (\n"
                + "  region_id SERIAL PRIMARY KEY\n"
                + ", region_name VARCHAR(25)\n"
                + ")"
        );
    }

    public static void insertTestRegionToTable(DatabaseClient client)
    {
        client.insert()
            .into(Region.class)
            .using(testRegion)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    public static void deleteTestTableForRegions(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DELETE regions CASCADE");
    }

    public static void dropTestTableForRegions(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS regions CASCADE");
    }



    public static void createTestTableForCountries(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS countries (\n"
                + "  country_id      CHAR(2) UNIQUE NOT NULL\n"
                + ", country_name    VARCHAR(40)\n"
                + ", region_id       BIGINT\n"
                + ", CONSTRAINT      country_c_id_pk PRIMARY KEY (country_id)\n"
                + ")"
        );
    }

    public static void insertTestCountryToTable(DatabaseClient client)
    {
        client.insert()
            .into(Country.class)
            .using(testCountry)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    public static void deleteTestTableForCountries(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DELETE countries CASCADE");
    }

    public static void dropTestTableForCountries(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS countries CASCADE");
    }

    public static void insertTestLocationToTable(DatabaseClient client)
    {
        client.insert()
            .into(Location.class)
            .using(testLocation)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    public static void deleteTestTableForLocations(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DELETE locations CASCADE");
    }
}
