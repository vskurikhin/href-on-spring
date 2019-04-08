package su.svn.href.models;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

public class EmployeeTest
{
    public static Employee testEmployee = new Employee(
        TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, TEST_PHONE_NUMBER,
        TEST_HIRE_DATE, TEST_SID, TEST_SALARY, TEST_COMMISSION_PCT, TEST_ID, TEST_ID
    );

    public static void testEmployeesDb(DatabaseClient client)
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

        client.insert()
            .into(Employee.class)
            .using(testEmployee)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    private static final Log log = LogFactory.getLog(WithDataBaseTable.class);

    @Nested
    @DisplayName("when integrate DB regions")
    class WithDataBaseTable
    {

        @Test
        void table()
        {
            ConnectionFactory connectionFactory = new H2ConnectionFactory(
                H2ConnectionConfiguration
                    .builder()
                    .url("mem:test;DB_CLOSE_DELAY=10")
                    .build()
            );
            DatabaseClient client = DatabaseClient.create(connectionFactory);
            testEmployeesDb(client);
            client.select()
                .from(Employee.class)
                .fetch()
                .first()
                .doOnNext(it -> log.info(it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
            databaseClientExecuteSql(client, "DROP TABLE IF EXISTS employees CASCADE");
        }
    }
}