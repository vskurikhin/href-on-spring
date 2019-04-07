package su.svn.href.models;

import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.TEST_DEPARTMENT_NAME;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

public class DepartmentTest
{
    public static Department testDepartment = new Department(TEST_ID, TEST_DEPARTMENT_NAME, TEST_ID, TEST_ID);

    public static void testDepartmentsDb(DatabaseClient client)
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
}