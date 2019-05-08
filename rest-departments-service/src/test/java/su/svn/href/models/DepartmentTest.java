package su.svn.href.models;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
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
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.TEST_DEPARTMENT_NAME;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestData.TEST_LID;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@DisplayName("Class Region")
public class DepartmentTest
{
    public static Department testDepartment = new Department(TEST_ID, TEST_DEPARTMENT_NAME, TEST_ID, TEST_ID);

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

        client.insert()
            .into(Department.class)
            .using(testDepartment)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    private Department department;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new Department();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            department = new Department();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(department).hasFieldOrPropertyWithValue("id", null);
            assertThat(department).hasFieldOrPropertyWithValue("departmentName", "");
            assertThat(department).hasFieldOrPropertyWithValue("managerId", null);
            assertThat(department).hasFieldOrPropertyWithValue("locationId", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            department.setId(TEST_ID);
            assertThat(department).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertEquals(TEST_LID, department.getId());
        }

        @Test
        @DisplayName("setter and getter for departmentName")
        void testGetSetDepartmentName()
        {
            department.setDepartmentName(TEST_DEPARTMENT_NAME);
            assertThat(department).hasFieldOrPropertyWithValue("departmentName", TEST_DEPARTMENT_NAME);
            assertEquals(TEST_DEPARTMENT_NAME, department.getDepartmentName());
        }

        @Test
        @DisplayName("setter and getter for managerId")
        void testGetSetManagerId()
        {
            department.setManagerId(TEST_ID);
            assertThat(department).hasFieldOrPropertyWithValue("managerId", TEST_ID);
            assertEquals(TEST_ID, department.getManagerId().longValue());
        }

        @Test
        @DisplayName("setter and getter for locationId")
        void testGetSetLocationId()
        {
            department.setLocationId(TEST_ID);
            assertThat(department).hasFieldOrPropertyWithValue("locationId", TEST_ID);
            assertEquals(TEST_ID, department.getLocationId().longValue());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {

            department = new Department(TEST_ID, TEST_DEPARTMENT_NAME, TEST_ID, TEST_ID);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(department).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(department).hasFieldOrPropertyWithValue("departmentName", TEST_DEPARTMENT_NAME);
            assertThat(department).hasFieldOrPropertyWithValue("managerId", TEST_ID);
            assertThat(department).hasFieldOrPropertyWithValue("locationId", TEST_ID);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new Department(), department);
            Department expected = new Department(TEST_ID, TEST_DEPARTMENT_NAME, TEST_ID, TEST_ID);
            assertEquals(expected.hashCode(), department.hashCode());
            assertEquals(expected, department);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(department.toString().length() > 0);
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
            ConnectionFactory connectionFactory = new H2ConnectionFactory(
                H2ConnectionConfiguration
                    .builder()
                    .url("mem:test;DB_CLOSE_DELAY=10")
                    .build()
            );
            DatabaseClient client = DatabaseClient.create(connectionFactory);
            createTestTableForDepartments(client);
            client.select()
                .from(Department.class)
                .fetch()
                .first()
                .doOnNext(it -> log.info(it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
            databaseClientExecuteSql(client, "DROP TABLE IF EXISTS departments CASCADE");
        }
    }
}