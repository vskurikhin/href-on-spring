package su.svn.href.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@DisplayName("Class Location")
public class LocationTest
{
    public static Location testLocation = new Location(
        TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );

    public static void createTestTableForLocations(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS locations (\n"
                + "  location_id    BIGINT UNIQUE NOT NULL\n"
                + ", street_address VARCHAR(40)\n"
                + ", postal_code    VARCHAR(12)\n"
                + ", city           VARCHAR(30) CONSTRAINT loc_city_nn NOT NULL\n"
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

    private Location location;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new Location();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            location = new Location();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(location).hasFieldOrPropertyWithValue("id", null);
            assertThat(location).hasFieldOrPropertyWithValue("streetAddress", null);
            assertThat(location).hasFieldOrPropertyWithValue("postalCode", null);
            assertThat(location).hasFieldOrPropertyWithValue("city", "");
            assertThat(location).hasFieldOrPropertyWithValue("stateProvince", null);
            assertThat(location).hasFieldOrPropertyWithValue("countryId", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            location.setId(TEST_LID);
            assertThat(location).hasFieldOrPropertyWithValue("id", TEST_LID);
            assertEquals(TEST_LID, location.getId());
        }

        @Test
        @DisplayName("setter and getter for streetAddress")
        void testGetSetStreetAddress()
        {
            location.setStreetAddress(TEST_STREET_ADDRESS);
            assertThat(location).hasFieldOrPropertyWithValue("streetAddress", TEST_STREET_ADDRESS);
            assertEquals(TEST_STREET_ADDRESS, location.getStreetAddress());
        }

        @Test
        @DisplayName("setter and getter for postalCode")
        void testGetSetPostalCode()
        {
            location.setPostalCode(TEST_POSTAL_CODE);
            assertThat(location).hasFieldOrPropertyWithValue("postalCode", TEST_POSTAL_CODE);
            assertEquals(TEST_POSTAL_CODE, location.getPostalCode());
        }

        @Test
        @DisplayName("setter and getter for city")
        void testGetSetCity()
        {
            location.setCity(TEST_CITY);
            assertThat(location).hasFieldOrPropertyWithValue("city", TEST_CITY);
            assertEquals(TEST_CITY, location.getCity());
        }

        @Test
        @DisplayName("setter and getter for stateProvince")
        void testGetSetStateProvince()
        {
            location.setStateProvince(TEST_STATE_PROVINCE);
            assertThat(location).hasFieldOrPropertyWithValue("stateProvince", TEST_STATE_PROVINCE);
            assertEquals(TEST_STATE_PROVINCE, location.getStateProvince());
        }

        @Test
        @DisplayName("setter and getter for countryId")
        void testGetSetCountryId()
        {
            location.setCountryId(TEST_SID);
            assertThat(location).hasFieldOrPropertyWithValue("countryId", TEST_SID);
            assertEquals(TEST_SID, location.getCountryId());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            location = new Location(
                TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
            );
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(location).hasFieldOrPropertyWithValue("id", TEST_LID);
            assertThat(location).hasFieldOrPropertyWithValue("streetAddress", TEST_STREET_ADDRESS);
            assertThat(location).hasFieldOrPropertyWithValue("postalCode", TEST_POSTAL_CODE);
            assertThat(location).hasFieldOrPropertyWithValue("city", TEST_CITY);
            assertThat(location).hasFieldOrPropertyWithValue("stateProvince", TEST_STATE_PROVINCE);
            assertThat(location).hasFieldOrPropertyWithValue("countryId", TEST_SID);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new Location(), location);
            Location expected = new Location(
                TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
            );
            assertEquals(expected.hashCode(), location.hashCode());
            assertEquals(expected, location);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(location.toString().length() > 0);
        }
    }
}