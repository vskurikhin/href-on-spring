package su.svn.href.models;

import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static su.svn.href.test.H2Helper.createH2ConnectionFactory;
import static su.svn.href.test.H2Helper.createTestTableForEmployees;
import static su.svn.href.test.H2Helper.dropTestTableForEmployees;
import static su.svn.utils.TestData.*;

public class EmployeeTest
{
    public static Employee testEmployee = new Employee(
        TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
        TEST_HIRE_DATE, TEST_SID, TEST_SALARY, TEST_COMMISSION_PCT, TEST_ID, TEST_ID
    );

    private Employee employee;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new Employee();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            employee = new Employee();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(employee).hasFieldOrPropertyWithValue("id", null);
            assertThat(employee).hasFieldOrPropertyWithValue("firstName", null);
            assertThat(employee).hasFieldOrPropertyWithValue("lastName", null);
            assertThat(employee).hasFieldOrPropertyWithValue("email", null);
            assertThat(employee).hasFieldOrPropertyWithValue("phoneNumber", null);
            assertThat(employee).hasFieldOrPropertyWithValue("hireDate", null);
            assertThat(employee).hasFieldOrPropertyWithValue("jobId", null);
            assertThat(employee).hasFieldOrPropertyWithValue("salary", null);
            assertThat(employee).hasFieldOrPropertyWithValue("commissionPct", null);
            assertThat(employee).hasFieldOrPropertyWithValue("managerId", null);
            assertThat(employee).hasFieldOrPropertyWithValue("departmentId", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            employee.setId(TEST_LID);
            assertThat(employee).hasFieldOrPropertyWithValue("id", TEST_LID);
            assertEquals(TEST_LID, employee.getId());
        }

        @Test
        @DisplayName("setter and getter for firstName")
        void testGetSetFirstName()
        {
            employee.setFirstName(TEST_FIRST_NAME);
            assertThat(employee).hasFieldOrPropertyWithValue("firstName", TEST_FIRST_NAME);
            assertEquals(TEST_FIRST_NAME, employee.getFirstName());
        }

        @Test
        @DisplayName("setter and getter for lastName")
        void testGetSetLastName()
        {
            employee.setLastName(TEST_LAST_NAME);
            assertThat(employee).hasFieldOrPropertyWithValue("lastName", TEST_LAST_NAME);
            assertEquals(TEST_LAST_NAME, employee.getLastName());
        }

        @Test
        @DisplayName("setter and getter for email")
        void testGetSetEmail()
        {
            employee.setEmail(TEST_EMAIL);
            assertThat(employee).hasFieldOrPropertyWithValue("email", TEST_EMAIL);
            assertEquals(TEST_EMAIL, employee.getEmail());
        }

        @Test
        @DisplayName("setter and getter for phoneNumber")
        void testGetSetPhoneNumber()
        {
            employee.setPhoneNumber(TEST_PHONE_NUMBER);
            assertThat(employee).hasFieldOrPropertyWithValue("phoneNumber", TEST_PHONE_NUMBER);
            assertEquals(TEST_PHONE_NUMBER, employee.getPhoneNumber());
        }

        @Test
        @DisplayName("setter and getter for hireDate")
        void testGetSetHireDate()
        {
            employee.setHireDate(TEST_HIRE_DATE);
            assertThat(employee).hasFieldOrPropertyWithValue("hireDate", TEST_HIRE_DATE);
            assertEquals(TEST_HIRE_DATE, employee.getHireDate());
        }

        @Test
        @DisplayName("setter and getter for jobId")
        void testGetSetJobId()
        {
            employee.setJobId(TEST_JOBID);
            assertThat(employee).hasFieldOrPropertyWithValue("jobId", TEST_JOBID);
            assertEquals(TEST_JOBID, employee.getJobId());
        }

        @Test
        @DisplayName("setter and getter for salary")
        void testGetSetSalary()
        {
            employee.setSalary(TEST_SALARY);
            assertThat(employee).hasFieldOrPropertyWithValue("salary", TEST_SALARY);
            assertEquals(TEST_SALARY, employee.getSalary());
        }

        @Test
        @DisplayName("setter and getter for commissionPct")
        void testGetSetCommissionPct()
        {
            employee.setCommissionPct(TEST_COMMISSION_PCT);
            assertThat(employee).hasFieldOrPropertyWithValue("commissionPct", TEST_COMMISSION_PCT);
            assertEquals(TEST_COMMISSION_PCT, employee.getCommissionPct());
        }

        @Test
        @DisplayName("setter and getter for managerId")
        void testGetSetManagerId()
        {
            employee.setManagerId(TEST_MANAGER_ID);
            assertThat(employee).hasFieldOrPropertyWithValue("managerId", TEST_MANAGER_ID);
            assertEquals(TEST_MANAGER_ID, employee.getManagerId());
        }

        @Test
        @DisplayName("setter and getter for departmentId")
        void testGetSetDepartmentId()
        {
            employee.setDepartmentId(TEST_DEPARTMENT_ID);
            assertThat(employee).hasFieldOrPropertyWithValue("departmentId", TEST_DEPARTMENT_ID);
            assertEquals(TEST_DEPARTMENT_ID, employee.getDepartmentId());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            employee = new Employee(
                TEST_LID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
                TEST_HIRE_DATE, TEST_SID, TEST_SALARY, TEST_COMMISSION_PCT, TEST_LID, TEST_LID
            );
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(employee).hasFieldOrPropertyWithValue("id", TEST_LID);
            assertThat(employee).hasFieldOrPropertyWithValue("firstName", TEST_FIRST_NAME);
            assertThat(employee).hasFieldOrPropertyWithValue("lastName", TEST_LAST_NAME);
            assertThat(employee).hasFieldOrPropertyWithValue("email", TEST_EMAIL);
            assertThat(employee).hasFieldOrPropertyWithValue("phoneNumber", TEST_PHONE_NUMBER);
            assertThat(employee).hasFieldOrPropertyWithValue("hireDate", TEST_HIRE_DATE);
            assertThat(employee).hasFieldOrPropertyWithValue("jobId", TEST_SID);
            assertThat(employee).hasFieldOrPropertyWithValue("salary", TEST_SALARY);
            assertThat(employee).hasFieldOrPropertyWithValue("commissionPct", TEST_COMMISSION_PCT);
            assertThat(employee).hasFieldOrPropertyWithValue("managerId", TEST_LID);
            assertThat(employee).hasFieldOrPropertyWithValue("departmentId", TEST_LID);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new Employee(), employee);
            Employee expected = new Employee(
                TEST_LID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
                TEST_HIRE_DATE, TEST_SID, TEST_SALARY, TEST_COMMISSION_PCT, TEST_LID, TEST_LID
            );
            assertEquals(expected.hashCode(), employee.hashCode());
            assertEquals(expected, employee);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(employee.toString().length() > 0);
        }
    }

    private static final Log log = LogFactory.getLog(IntegrateWithDB.class);

    @Nested
    @DisplayName("do integrate with DB")
    class IntegrateWithDB
    {
        @Test
        @DisplayName("create table then inserts test record and then drop table")
        void createTableInsertsRecordAndDropTable()
        {
            ConnectionFactory connectionFactory = createH2ConnectionFactory();
            DatabaseClient client = DatabaseClient.create(connectionFactory);
            createTestTableForEmployees(client);
            client.insert()
                .into(Employee.class)
                .using(testEmployee)
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
            client.select()
                .from(Employee.class)
                .fetch()
                .first()
                .doOnNext(it -> log.info(it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
            dropTestTableForEmployees(client);
        }
    }
}