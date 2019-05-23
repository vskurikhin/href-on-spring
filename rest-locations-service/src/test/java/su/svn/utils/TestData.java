package su.svn.utils;

import su.svn.href.models.Country;
import su.svn.href.models.Location;
import su.svn.href.models.Region;
import su.svn.href.models.dto.CountryDto;
import su.svn.href.models.dto.LocationDto;

import java.math.BigInteger;

public class TestData
{
    public static String TEST = "test";

    public static long TEST_ID = 13L;

    public static BigInteger TEST_BID = BigInteger.valueOf(13L);

    public static Long TEST_LID = 13L;

    public static String TEST_SID = "13";

    public static int TEST_NUM = 3;

    public static String TEST_REGION_NAME = "test_region_name";

    public static String TEST_COUNTRY_NAME = "test_country_name";

    public static String TEST_STREET_ADDRESS = "test_street_address";

    public static String TEST_POSTAL_CODE = "postal_code";

    public static String TEST_CITY = "test_city";

    public static String TEST_STATE_PROVINCE = "test_state_province";

    public static Region testRegion = new Region(TEST_LID, TEST_REGION_NAME);

    public static Country testCountry = new Country(TEST_SID, TEST_COUNTRY_NAME, TEST_ID);

    public static Location testLocation = new Location(
        TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );

    public static Region createRegion0()
    {
        Region result = new Region();
        result.setId(null);
        result.setRegionName("test_region_name_0");

        return result;
    }

    public static Region createRegion1()
    {
        Region result = new Region();
        result.setId(1L);
        result.setRegionName("test_region_name_1");

        return result;
    }

    public static Region createRegion2()
    {
        Region result = new Region();
        result.setId(2L);
        result.setRegionName("test_region_name_2");

        return result;
    }

    public static Country createCountry0()
    {
        Country result = new Country();
        result.setCountryName("test_country_name_0");
        result.setRegionId(null);

        return result;
    }

    public static Country createCountry1()
    {
        Country result = new Country();
        result.setId("AA");
        result.setCountryName("test_country_name_1");
        result.setRegionId(1L);

        return result;
    }

    public static Country createCountry2()
    {
        Country result = new Country();
        result.setId("BB");
        result.setCountryName("test_country_name_2");
        result.setRegionId(2L);

        return result;
    }

    public static CountryDto createCountryDto0()
    {
        CountryDto result = new CountryDto();
        result.setCountryName("test_countryDto_name_0");

        return result;
    }

    public static CountryDto createCountryDto1()
    {
        CountryDto result = new CountryDto();
        result.setId("AA");
        result.setCountryName("test_countryDto_name_1");
        result.setRegion(createRegion1());

        return result;
    }

    public static CountryDto createCountryDto2()
    {
        CountryDto result = new CountryDto();
        result.setId("BB");
        result.setCountryName("test_countryDto_name_2");
        result.setRegion(createRegion2());

        return result;
    }

    public static Location createLocation0()
    {
        Location result = new Location();
        result.setStreetAddress("street_address_0");
        result.setPostalCode("postalCode0");
        result.setCity("city_0");
        result.setStateProvince("state_province_0");

        return result;
    }

    public static Location createLocation1()
    {
        Location result = new Location();
        result.setId(1L);
        result.setStreetAddress("street_address_1");
        result.setPostalCode("postalCode1");
        result.setCity("city_1");
        result.setStateProvince("state_province_1");
        result.setCountryId("11");

        return result;
    }

    public static Location createLocation2()
    {
        Location result = new Location();
        result.setId(2L);
        result.setStreetAddress("street_address_2");
        result.setPostalCode("postalCode2");
        result.setCity("city_2");
        result.setStateProvince("state_province_2");
        result.setCountryId("22");

        return result;
    }

    public static LocationDto createLocationDto0()
    {
        LocationDto result = new LocationDto();
        result.setStreetAddress("street_address_0");
        result.setPostalCode("postal_code_0");
        result.setCity("city_0");
        result.setStateProvince("state_province_0");

        return result;
    }

    public static LocationDto createLocationDto1()
    {
        LocationDto result = new LocationDto();
        result.setId(1L);
        result.setStreetAddress("street_address_1");
        result.setPostalCode("postal_code_1");
        result.setCity("city_1");
        result.setStateProvince("state_province_1");
        result.setCountry(createCountryDto1());

        return result;
    }

    public static LocationDto createLocationDto2()
    {
        LocationDto result = new LocationDto();
        result.setId(2L);
        result.setStreetAddress("street_address_2");
        result.setPostalCode("postal_code_2");
        result.setCity("city_2");
        result.setStateProvince("state_province_2");
        result.setCountry(createCountryDto2());

        return result;
    }
}
