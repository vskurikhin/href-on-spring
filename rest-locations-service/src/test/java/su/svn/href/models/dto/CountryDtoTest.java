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
import static su.svn.href.models.RegionTest.testRegion;
import static su.svn.utils.TestData.*;

@DisplayName("Class CountryDto")
class CountryDtoTest
{
    public static CountryDto testCountryDto = new CountryDto(
        TEST_SID, TEST_COUNTRY_NAME, testRegion
    );

    private CountryDto countryDto;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new CountryDto();
    }

    @Nested
    @DisplayName("constructing from map")
    class WhenConstructingFromMap
    {
        @Test
        void collectFromNull()
        {
            assertThrows(NullPointerException.class, () -> CountryDto.collectFromMap(null));
        }

        @Test
        void collectFromEmpty()
        {
            assertThrows(NullPointerException.class, () -> CountryDto.collectFromMap(new HashMap<>()));
        }

        @Test
        void collectFromMinimalValidMap()
        {
            Map<String, Object> mapTest = new HashMap<String, Object>() {{
                put("COUNTRY_ID", TEST_SID);
            }};
            CountryDto expected = new CountryDto(TEST_SID, "NULL", null);
            assertEquals(expected, CountryDto.collectFromMap(mapTest));
        }
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            countryDto = new CountryDto();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(countryDto).hasFieldOrPropertyWithValue("id", "");
            assertThat(countryDto).hasFieldOrPropertyWithValue("countryName", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            countryDto.setId(TEST_SID);
            assertThat(countryDto).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertEquals(TEST_SID, countryDto.getId());
        }

        @Test
        @DisplayName("setter and getter for countryName")
        void testGetSetCountryName()
        {
            countryDto.setCountryName(TEST);
            assertThat(countryDto).hasFieldOrPropertyWithValue("countryName", TEST);
            assertEquals(TEST, countryDto.getCountryName());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            countryDto = new CountryDto(TEST_SID, TEST_COUNTRY_NAME, testRegion);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(countryDto).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertThat(countryDto).hasFieldOrPropertyWithValue("countryName", TEST_COUNTRY_NAME);

        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new CountryDto(), countryDto);
            CountryDto expected = new CountryDto(TEST_SID, TEST_COUNTRY_NAME, testRegion);
            assertEquals(expected.hashCode(), countryDto.hashCode());
            assertEquals(expected, countryDto);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(countryDto.toString().length() > 0);
        }
    }
}