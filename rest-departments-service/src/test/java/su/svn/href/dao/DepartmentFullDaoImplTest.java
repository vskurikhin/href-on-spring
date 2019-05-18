package su.svn.href.dao;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import su.svn.href.models.dto.DepartmentDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.test.H2Helper.*;
import static su.svn.href.test.H2Helper.TEST_DEPARTMENT_NAME;
import static su.svn.href.test.H2Helper.TEST_LID;
import static su.svn.href.test.H2Helper.testEmployee;
import static su.svn.href.test.H2Helper.testLocation;
import static su.svn.utils.TestData.*;

@SuppressWarnings("ALL")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
class DepartmentFullDaoImplTest
{
    static DatabaseClient client;

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
            createTestTableForDepartments(client);
            createTestTableForEmployees(client);

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
    static void drop()
    {
        dropTestTableForEmployees(client);
        dropTestTableForDepartments(client);
        dropTestTableForLocations(client);
    }

    @BeforeEach
    void setUp()
    {
        insertTestLocationToTable(client);
        insertTestDepartmentToTable(client);
        insertTestEmployeeToTable(client);
    }

    @AfterEach
    void clean()
    {
        deleteTestTableForEmployees(client);
        deleteTestTableForDepartments(client);
        deleteTestTableForLocations(client);
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