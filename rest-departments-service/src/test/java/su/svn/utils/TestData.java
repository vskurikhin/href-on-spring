package su.svn.utils;

import su.svn.href.models.Department;
import su.svn.href.models.Location;
import su.svn.href.models.Manager;
import su.svn.href.models.dto.DepartmentDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TestData
{
    public static String TEST = "test";

    public static long TEST_ID = 13L;

    public static Long TEST_LID = 13L;

    public static String TEST_SID = "13";

    public static int TEST_NUM = 3;

    public static Long TEST_DEPARTMENT_ID = TEST_ID;

    public static String TEST_DEPARTMENT_NAME = "test_department_name";

    public static Long TEST_MANAGER_ID = TEST_LID;

    public static Long TEST_LOCATION_ID = TEST_LID;

    public static String TEST_REGION_NAME = "test_region_name";

    public static String TEST_COUNTRY_NAME = "test_country_name";

    public static String TEST_STREET_ADDRESS = "test_street_address";

    public static String TEST_POSTAL_CODE = "postal_code";

    public static String TEST_CITY = "test_city";

    public static String TEST_STATE_PROVINCE = "test_state_province";

    public static String TEST_FIRST_NAME = "test_first_name";

    public static String TEST_LAST_NAME = "test_last_name";

    public static String TEST_EMAIL = "test_email";

    public static String TEST_PHONE_NUMBER = "test_phone_number";

    public static Date TEST_HIRE_DATE =  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    public static String TEST_JOBID = "test_jobId";

    public static Double TEST_SALARY = 13_000_000.0;

    public static Double TEST_COMMISSION_PCT = 0.013;

    public static Location createLocation0()
    {
        Location result = new Location();
        result.setStreetAddress("street_address_0");
        result.setPostalCode("postal_code_0");
        result.setCity("city_0");
        result.setStateProvince("state_province_0");

        return result;
    }

    public static Location createLocation1()
    {
        Location result = new Location();
        result.setId(1L);
        result.setStreetAddress("street_address_1");
        result.setPostalCode("postal_code_1");
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
        result.setPostalCode("postal_code_2");
        result.setCity("city_2");
        result.setStateProvince("state_province_2");
        result.setCountryId("22");

        return result;
    }



    public static Department createDepartment0()
    {
        Department result = new Department();
        result.setDepartmentName("test_department_name_0");

        return result;
    }

    public static Department createDepartment1()
    {
        Department result = new Department();
        result.setId(1L);
        result.setDepartmentName("test_department_name_1");
        result.setLocationId(1L);
        result.setManagerId(1L);

        return result;
    }

    public static Department createDepartment2()
    {
        Department result = new Department();
        result.setId(2L);
        result.setDepartmentName("test_department_name_2");
        result.setLocationId(2L);
        result.setManagerId(2L);

        return result;
    }


    public static DepartmentDto createDepartmentDto0()
    {
        DepartmentDto result = new DepartmentDto();
        result.setDepartmentName("test_department_name_0");

        return result;
    }

    public static DepartmentDto createDepartmentDto1()
    {
        DepartmentDto result = new DepartmentDto();
        result.setId(1L);
        result.setDepartmentName("test_department_name_1");
        result.setLocation(createLocation1());
        result.setManager(createManager1());

        return result;
    }

    public static DepartmentDto createDepartmentDto2()
    {
        DepartmentDto result = new DepartmentDto();
        result.setId(2L);
        result.setDepartmentName("test_department_name_2");
        result.setLocation(createLocation2());
        result.setManager(createManager2());

        return result;
    }


    public static Manager createManager0()
    {
        Manager result = new Manager();
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");

        return result;
    }

    public static Manager createManager1()
    {
        Manager result = new Manager();
        result.setId(1L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");

        return result;
    }

    public static Manager createManager2()
    {
        Manager result = new Manager();
        result.setId(2L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");

        return result;
    }
}
