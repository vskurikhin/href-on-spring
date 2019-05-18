package su.svn.href.test;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;
import su.svn.href.models.Department;
import su.svn.href.models.Employee;
import su.svn.href.models.Location;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class H2Helper
{
    public static Long TEST_LID = 13L;

    public static String TEST_SID = "13";


    public static String TEST_DEPARTMENT_NAME = "test_department_name";


    public static String TEST_STREET_ADDRESS = "test_street_address";

    public static String TEST_POSTAL_CODE = "postal_code";

    public static String TEST_CITY = "test_city";

    public static String TEST_STATE_PROVINCE = "test_state_province";

    public static String TEST_FIRST_NAME = "test_first_name";

    public static String TEST_LAST_NAME = "test_last_name";

    public static String TEST_EMAIL = "test_email";

    public static String TEST_PHONE_NUMBER = "test_phone_number";

    public static Date TEST_HIRE_DATE =  Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

    public static Double TEST_SALARY = 13_000_000.0;

    public static Double TEST_COMMISSION_PCT = 0.013;

    public static Location testLocation = new Location(
        TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );

    public static Department testDepartment = new Department(TEST_LID, TEST_DEPARTMENT_NAME, TEST_LID, TEST_LID);

    public static Employee testEmployee = new Employee(
        TEST_LID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
        TEST_HIRE_DATE, TEST_SID, TEST_SALARY, TEST_COMMISSION_PCT, TEST_LID, TEST_LID
    );

    public static ConnectionFactory createH2ConnectionFactory()
    {
        return new H2ConnectionFactory(H2ConnectionConfiguration
            .builder()
            .url("mem:test;DB_CLOSE_DELAY=10")
            .build()
        );
    }

    public static void databaseClientExecuteSql(DatabaseClient client, String sql)
    {
        client.execute()
            .sql(sql)
            .fetch()
            .rowsUpdated()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }

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
    }

    public static void insertTestLocationToTable(DatabaseClient client)
    {
        client.insert()
            .into(Location.class)
            .using(testLocation)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    public static void deleteTestTableForLocations(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DELETE locations CASCADE");
    }

    public static void dropTestTableForLocations(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS locations CASCADE");
    }

    public static void createTestTableForDepartments(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE departments (\n"
                + "  department_id    BIGINT UNIQUE NOT NULL\n"
                + ", department_name  VARCHAR(30) CONSTRAINT dept_name_nn  NOT NULL\n"
                + ", manager_id       BIGINT\n"
                + ", location_id      BIGINT\n"
                + ", CONSTRAINT       dept_id_pk PRIMARY KEY (department_id)\n"
                + ")"
        );
    }

    public static void insertTestDepartmentToTable(DatabaseClient client)
    {
        client.insert()
            .into(Department.class)
            .using(testDepartment)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    public static void deleteTestTableForDepartments(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DELETE departments CASCADE");
    }

    public static void dropTestTableForDepartments(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS departments CASCADE");
    }

    public static void createTestTableForEmployees(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS employees (\n"
                + "  employee_id    BIGINT UNIQUE NOT NULL\n"
                + ", first_name     VARCHAR(20)\n"
                + ", last_name      VARCHAR(25) CONSTRAINT emp_last_name_nn NOT NULL\n"
                + ", email          VARCHAR(25) CONSTRAINT emp_email_nn NOT NULL\n"
                + ", phone_number   VARCHAR(20)\n"
                + ", hire_date      DATE        CONSTRAINT emp_hire_date_nn NOT NULL\n"
                + ", job_id         VARCHAR(10) CONSTRAINT emp_job_nn NOT NULL\n"
                + ", salary         REAL\n"
                + ", commission_pct REAL\n"
                + ", manager_id     BIGINT\n"
                + ", department_id  BIGINT\n"
                + ", CONSTRAINT     emp_salary_min CHECK (salary > 0) \n"
                + ", CONSTRAINT     emp_email_uk   UNIQUE (email)\n"
                + ", CONSTRAINT     emp_emp_id_pk  PRIMARY KEY (employee_id)\n"
                + ")"
        );
    }


    public static void insertTestEmployeeToTable(DatabaseClient client)
    {
        client.insert()
            .into(Employee.class)
            .using(testEmployee)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    public static void deleteTestTableForEmployees(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DELETE employees CASCADE");
    }

    public static void dropTestTableForEmployees(DatabaseClient client)
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS employees CASCADE");
    }
}
