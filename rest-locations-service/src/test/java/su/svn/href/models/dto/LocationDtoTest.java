package su.svn.href.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.href.models.Region;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.href.models.dto.CountryDtoTest.testCountryDto;
import static su.svn.utils.TestData.*;

public class LocationDtoTest
{
    public static LocationDto testLocationDto = new LocationDto(
        TEST_ID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, testCountryDto
    );

    private LocationDto locationDto;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new LocationDto();
    }

    @Nested
    @DisplayName("constructing from map")
    class WhenConstructingFromMap
    {
        @Test
        void collectFromNull()
        {
            assertThrows(NullPointerException.class, () -> LocationDto.collectFromMap(null));
        }

        @Test
        void collectFromEmpty()
        {
            assertThrows(NullPointerException.class, () -> LocationDto.collectFromMap(new HashMap<>()));
        }

        @Test
        void collectFromMinimalValidMap()
        {
            Map<String, Object> mapTest = new HashMap<String, Object>() {{
                put("LOCATION_ID", TEST_ID);
            }};
            LocationDto expected = new LocationDto(TEST_ID, "NULL", "NULL", "NULL", "NULL", null);
            assertEquals(expected, LocationDto.collectFromMap(mapTest));
        }
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            locationDto = new LocationDto();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(locationDto).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(locationDto).hasFieldOrPropertyWithValue("streetAddress", null);
            assertThat(locationDto).hasFieldOrPropertyWithValue("postalCode", null);
            assertThat(locationDto).hasFieldOrPropertyWithValue("city", null);
            assertThat(locationDto).hasFieldOrPropertyWithValue("stateProvince", null);
            assertThat(locationDto).hasFieldOrPropertyWithValue("country", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            locationDto.setId(TEST_ID);
            assertThat(locationDto).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertEquals(TEST_ID, locationDto.getId());
        }

        @Test
        @DisplayName("setter and getter for streetAddress")
        void testGetSetCountryName()
        {
            locationDto.setStreetAddress(TEST_STREET_ADDRESS);
            assertThat(locationDto).hasFieldOrPropertyWithValue("streetAddress", TEST_STREET_ADDRESS);
            assertEquals(TEST_STREET_ADDRESS, locationDto.getStreetAddress());
        }

        @Test
        @DisplayName("setter and getter for postalCode")
        void testGetSetPostalCode()
        {
            locationDto.setPostalCode(TEST_POSTAL_CODE);
            assertThat(locationDto).hasFieldOrPropertyWithValue("postalCode", TEST_POSTAL_CODE);
            assertEquals(TEST_POSTAL_CODE, locationDto.getPostalCode());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            locationDto = new LocationDto(
                TEST_ID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, testCountryDto
            );
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(locationDto).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(locationDto).hasFieldOrPropertyWithValue("streetAddress", TEST_STREET_ADDRESS);
            assertThat(locationDto).hasFieldOrPropertyWithValue("postalCode", TEST_POSTAL_CODE);
            assertThat(locationDto).hasFieldOrPropertyWithValue("city", TEST_CITY);
            assertThat(locationDto).hasFieldOrPropertyWithValue("stateProvince", TEST_STATE_PROVINCE);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new LocationDto(), locationDto);
            LocationDto expected = new LocationDto(
                TEST_ID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, testCountryDto
            );
            assertEquals(expected.hashCode(), locationDto.hashCode());
            assertEquals(expected, locationDto);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(locationDto.toString().length() > 0);
        }
    }
}