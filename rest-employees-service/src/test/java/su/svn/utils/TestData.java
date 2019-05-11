package su.svn.utils;

import su.svn.href.models.Department;
import su.svn.href.models.Employee;
import su.svn.href.models.Location;
import su.svn.href.models.Manager;
import su.svn.href.models.dto.EmployeeDto;

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

    public static String TEST_DEPARTMENT_NAME = "test_department_name";

    public static String TEST_FIRST_NAME = "first_name";

    public static String TEST_LAST_NAME = "last_name";

    public static String TEST_EMAIL = "email";

    public static String TEST_PHONE_NUMBER = "phone_number";

    public static Date TEST_HIRE_DATE =  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    public static String TEST_JOBID = "test_jobId";

    public static Double TEST_SALARY = 13_000_000.0;

    public static Double TEST_COMMISSION_PCT = 0.013;

    public static Long TEST_MANAGER_ID = TEST_ID;

    public static Long TEST_DEPARTMENT_ID = TEST_ID;

    public static String TEST_REGION_NAME = "test_region_name";

    public static String TEST_COUNTRY_NAME = "test_country_name";

    public static String TEST_STREET_ADDRESS = "test_street_address";

    public static String TEST_POSTAL_CODE = "postal_code";

    public static String TEST_CITY = "test_city";

    public static String TEST_STATE_PROVINCE = "test_state_province";


    public static Location testLocation = new Location(
        TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );

    public static Department testDepartment = new Department(TEST_ID, TEST_DEPARTMENT_NAME, TEST_LID, TEST_LID);

    public static Employee testEmployee = new Employee(
        TEST_LID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
        TEST_HIRE_DATE, TEST_JOBID, TEST_SALARY, TEST_COMMISSION_PCT, TEST_LID, TEST_LID
    );

    public static Manager testManager = new Manager(
        TEST_LID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER
    );

    public static EmployeeDto testEmployeeDto = new EmployeeDto(
        TEST_LID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER, TEST_HIRE_DATE, TEST_JOBID,
        TEST_SALARY, TEST_COMMISSION_PCT, testManager, testDepartment
    );

}
