package su.svn.utils;

import su.svn.href.models.Department;
import su.svn.href.models.Employee;
import su.svn.href.models.Location;
import su.svn.href.models.Manager;
import su.svn.href.models.dto.EmployeeDto;

import java.time.LocalDate;
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

    public static Date TEST_HIRE_DATE =  Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    public static String TEST_JOBID = "test_jobId";

    public static Double TEST_SALARY = 2.0;

    public static Double TEST_COMMISSION_PCT = 2.0;

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

    public static Employee createEmployee0()
    {
        Employee result = new Employee();
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");
        result.setHireDate(TEST_HIRE_DATE);
        result.setJobId("test_jobId_0");
        result.setSalary(0.0);
        result.setCommissionPct(0.0);

        return result;
    }

    public static Employee createEmployee1()
    {
        Employee result = new Employee();
        result.setId(1L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");
        result.setHireDate(TEST_HIRE_DATE);
        result.setJobId("test_jobId_1");
        result.setSalary(1.0);
        result.setCommissionPct(1.0);
        result.setManagerId(TEST_LID);
        result.setDepartmentId(TEST_LID);

        return result;
    }

    public static Employee createEmployee2()
    {
        Employee result = new Employee();
        result.setId(2L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");
        result.setHireDate(TEST_HIRE_DATE);
        result.setJobId("test_jobId_2");
        result.setSalary(2.0);
        result.setCommissionPct(2.0);
        result.setManagerId(TEST_LID);
        result.setDepartmentId(TEST_LID);

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

    public static EmployeeDto createEmployeeDto0()
    {
        EmployeeDto result = new EmployeeDto();
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");
        result.setHireDate(TEST_HIRE_DATE);
        result.setJobId("test_jobId_0");
        result.setSalary(0.0);
        result.setCommissionPct(0.0);

        return result;
    }

    public static EmployeeDto createEmployeeDto1()
    {
        EmployeeDto result = new EmployeeDto();
        result.setId(1L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");
        result.setHireDate(TEST_HIRE_DATE);
        result.setJobId("test_jobId_1");
        result.setSalary(1.0);
        result.setCommissionPct(1.0);
        result.setManager(createManager1());
        result.setDepartment(createDepartment1());

        return result;
    }

    public static EmployeeDto createEmployeeDto2()
    {
        EmployeeDto result = new EmployeeDto();
        result.setId(2L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");
        result.setEmail("test_email_0");
        result.setPhoneNumber("test_phone_number_0");
        result.setHireDate(TEST_HIRE_DATE);
        result.setJobId("test_jobId_2");
        result.setSalary(2.0);
        result.setCommissionPct(2.0);
        result.setManager(createManager2());
        result.setDepartment(createDepartment2());

        return result;
    }
}
