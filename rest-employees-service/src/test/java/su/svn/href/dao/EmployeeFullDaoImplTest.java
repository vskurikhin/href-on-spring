package su.svn.href.dao;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
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
import su.svn.href.models.dto.EmployeeDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.test.H2Helper.*;
import static su.svn.utils.TestData.*;

@SuppressWarnings("ALL")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("Class EmployeeFullDaoImpl")
class EmployeeFullDaoImplTest
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
        public EmployeeFullDao employeeFullDao()
        {
            return new EmployeeFullDaoImpl(client);
        }
    }

    @Autowired
    EmployeeFullDao employeeFullDao;

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
        // TODO
        // assertEquals(testEmployeeDto, employeeFullDao.findById(TEST_LID).block());
    }

    @Test
    void findAll()
    {
        // TODO
        // List<EmployeeDto> expected = Collections.singletonList(testEmployeeDto);
        // List<EmployeeDto> list = employeeFullDao.findAll(0, 1).collectList().block();
        // assertEquals(expected, list);
    }

    @Test
    void findAllSortByFirstName()
    {
        // TODO
        // List<EmployeeDto> expected = Collections.singletonList(testEmployeeDto);
        // List<EmployeeDto> list = employeeFullDao.findAll(0, 1, "e.first_name").collectList().block();
        // assertEquals(expected, list);
    }

    @Test
    void findAllSortByCityLastName()
    {
        // TODO
        // List<EmployeeDto> expected = Collections.singletonList(testEmployeeDto);
        // List<EmployeeDto> list = employeeFullDao.findAll(0, 1, "last_name").collectList().block();
        // assertEquals(expected, list);
    }
}