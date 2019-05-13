package su.svn.href.dao;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.function.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;
import su.svn.href.models.Department;
import su.svn.href.models.Employee;
import su.svn.href.models.Location;
import su.svn.href.models.dto.DepartmentDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.test.H2Helper.*;
import static su.svn.utils.TestData.*;

@SuppressWarnings("ALL")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
class DepartmentFullDaoImplTest
{
    static DatabaseClient client;

    static void insertTestLocationToTable(DatabaseClient client)
    {
        client.insert()
            .into(Location.class)
            .using(testLocation)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    static void insertTestDepartmentToTable(DatabaseClient client)
    {
        client.insert()
            .into(Department.class)
            .using(testDepartment)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
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

    @Configuration
    @EnableR2dbcRepositories(basePackages = "su.svn.href.dao")
    static class Config
    {
        @Bean
        public DatabaseClient databaseClient()
        {
            ConnectionFactory connectionFactory = createH2ConnectionFactory();
            client = DatabaseClient.create(connectionFactory);

            createTestTableForLocations(client);
            insertTestLocationToTable(client);

            createTestTableForDepartments(client);
            insertTestDepartmentToTable(client);

            createTestTableForEmployees(client);
            insertTestEmployeeToTable(client);

            return client;
        }

        @Bean
        public ReactiveDataAccessStrategy reactiveDataAccessStrategy()
        {
            return new DefaultReactiveDataAccessStrategy(new PostgresDialect());
        }

        @Bean
        public DepartmentFullDao locationFullDao(EmployeeDao employeeDao)
        {
            return new DepartmentFullDaoImpl(client, employeeDao);
        }
    }

    @Autowired
    DepartmentFullDao departmentFullDao;

    @AfterAll
    static void clean()
    {
        dropTestTableForLocations(client);
        dropTestTableForEmployees(client);
        dropTestTableForDepartments(client);
    }

    @Test
    void findById()
    {
        assertEquals(testDepartmentDto, departmentFullDao.findById(TEST_LID).block());
    }

    @Test
    void findAll()
    {
        DepartmentDto expectedDepartmentDto = new DepartmentDto(
            TEST_ID, TEST_DEPARTMENT_NAME, testManager, testLocation, null
        );
        expectedDepartmentDto.setEmployees(Collections.singletonList(testEmployee));
        List<DepartmentDto> expected = Collections.singletonList(expectedDepartmentDto);
        List<DepartmentDto> list = departmentFullDao.findAll(0, 1).block();
        // TODO assertEquals(expected, list);
    }
}