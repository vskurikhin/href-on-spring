package su.svn.utils;

import su.svn.href.models.Country;
import su.svn.href.models.Region;

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
        result.setId("1");
        result.setCountryName("test_country_name_1");
        result.setRegionId(1L);

        return result;
    }

    public static Country createCountry2()
    {
        Country result = new Country();
        result.setId("2");
        result.setCountryName("test_country_name_2");
        result.setRegionId(2L);

        return result;
    }
}
