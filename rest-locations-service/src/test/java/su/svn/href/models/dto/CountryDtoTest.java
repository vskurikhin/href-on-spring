package su.svn.href.models.dto;

import static su.svn.href.models.RegionTest.testRegion;
import static su.svn.utils.TestData.*;

class CountryDtoTest
{
    public static CountryDto testCountryDto = new CountryDto(
        TEST_SID, TEST_COUNTRY_NAME, testRegion
    );
}