package su.svn.href.models.dto;

import static su.svn.href.models.dto.CountryDtoTest.testCountryDto;
import static su.svn.utils.TestData.*;

public class LocationDtoTest
{
    public static LocationDto testLocationDto = new LocationDto(
        TEST_ID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, testCountryDto
    );
}