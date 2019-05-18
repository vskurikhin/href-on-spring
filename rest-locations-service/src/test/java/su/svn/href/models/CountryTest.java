package su.svn.href.models;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.TEST_COUNTRY_NAME;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestData.TEST_SID;
import static su.svn.utils.TestUtil.*;

@DisplayName("Class Country")
public class CountryTest
{
    private Country country;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new Country();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            country = new Country();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(country).hasFieldOrPropertyWithValue("id", "");
            assertThat(country).hasFieldOrPropertyWithValue("countryName", null);
            assertThat(country).hasFieldOrPropertyWithValue("regionId", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            country.setId(TEST_SID);
            assertThat(country).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertEquals(TEST_SID, country.getId());
        }

        @Test
        @DisplayName("setter and getter for countryName")
        void testGetSetCountryName()
        {
            country.setCountryName(TEST_COUNTRY_NAME);
            assertThat(country).hasFieldOrPropertyWithValue("countryName", TEST_COUNTRY_NAME);
            assertEquals(TEST_COUNTRY_NAME, country.getCountryName());
        }

        @Test
        @DisplayName("setter and getter for regionId")
        void testGetSetRegionId()
        {
            country.setRegionId(TEST_ID);
            assertThat(country).hasFieldOrPropertyWithValue("regionId", TEST_ID);
            assertEquals(TEST_ID, country.getRegionId().longValue());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            country = new Country(TEST_SID, TEST_COUNTRY_NAME, TEST_ID);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(country).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertThat(country).hasFieldOrPropertyWithValue("countryName", TEST_COUNTRY_NAME);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new Country(), country);
            Country expected = new Country(TEST_SID, TEST_COUNTRY_NAME, TEST_ID);
            assertEquals(expected.hashCode(), country.hashCode());
            assertEquals(expected, country);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(country.toString().length() > 0);
        }
    }

    private static final Log log = LogFactory.getLog(IntegrateWithDB.class);

    @Nested
    @DisplayName("do integrate with DB")
    class IntegrateWithDB
    {
        @Test
        @DisplayName("create table then inserts test record and then drop table")
        void createTableInsertsRecordAndDropTable()
        {
            ConnectionFactory connectionFactory = new H2ConnectionFactory(
                H2ConnectionConfiguration
                    .builder()
                    .url("mem:test;DB_CLOSE_DELAY=10")
                    .build()
            );
            DatabaseClient client = DatabaseClient.create(connectionFactory);
            createTestTableForCountries(client);
            insertTestCountryToTable(client);
            client.select()
                .from(Country.class)
                .fetch()
                .first()
                .doOnNext(it -> log.info(it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
            dropTestTableForCountries(client);
        }
    }
}