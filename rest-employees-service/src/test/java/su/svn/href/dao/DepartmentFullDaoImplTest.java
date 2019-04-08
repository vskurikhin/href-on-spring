package su.svn.href.dao;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
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
import su.svn.href.models.dto.DepartmentDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.href.models.DepartmentTest.createTestTableForDepartments;
import static su.svn.href.models.EmployeeTest.testEmployee;
import static su.svn.href.models.EmployeeTest.testEmployeesDb;
import static su.svn.href.models.LocationTest.testLocation;
import static su.svn.href.models.LocationTest.testLocationsDb;
import static su.svn.href.models.ManagerTest.testManager;
import static su.svn.href.models.dto.DepartmentDtoTest.testDepartmentDto;
import static su.svn.utils.TestData.TEST_DEPARTMENT_NAME;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

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
            ConnectionFactory connectionFactory = new H2ConnectionFactory(
                H2ConnectionConfiguration
                    .builder()
                    .url("mem:test;DB_CLOSE_DELAY=10")
                    .build()
            );
            client = DatabaseClient.create(connectionFactory);
            createTestTableForDepartments(client);
            testEmployeesDb(client);
            testLocationsDb(client);

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
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS locations CASCADE");
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS employees CASCADE");
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS departments CASCADE");
    }

    @Test
    void findById()
    {
        assertEquals(testDepartmentDto, departmentFullDao.findById(TEST_ID).block());
    }

    @Test
    void findAll()
    {
        DepartmentDto expectedDepartmentDto = new DepartmentDto(
            TEST_ID, TEST_DEPARTMENT_NAME, testManager, testLocation, null
        );
        expectedDepartmentDto.setEmployees(Collections.singletonList(testEmployee));
        List<DepartmentDto> expected = Collections.singletonList(expectedDepartmentDto);
        List<DepartmentDto> list = departmentFullDao.findAll(0, 1).collectList().block();
        // TODO assertEquals(expected, list);
    }

}