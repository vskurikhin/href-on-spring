package su.svn.href.dao;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.*;
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
import su.svn.href.models.Department;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.test.H2Helper.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("Class DepartmentDaoImpl")
class DepartmentDaoImplTest
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

            return client;
        }

        @Bean
        public ReactiveDataAccessStrategy reactiveDataAccessStrategy()
        {
            return new DefaultReactiveDataAccessStrategy(new PostgresDialect());
        }
    }


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    DepartmentDao departmentDao;

    @AfterAll
    static void drop()
    {
        dropTestTableForDepartments(client);
        dropTestTableForLocations(client);
    }

    @BeforeEach
    void setUp()
    {
        insertTestLocationToTable(client);
        insertTestDepartmentToTable(client);
    }

    @AfterEach
    void clean()
    {
        deleteTestTableForDepartments(client);
        deleteTestTableForLocations(client);
    }

    @Test
    void findByDepartmentName_found()
    {
        List<Department> expected = Collections.singletonList(testDepartment);
        List<Department> list = departmentDao.findByDepartmentName(0, 1, TEST_DEPARTMENT_NAME).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByDepartmentName_notFound()
    {
        List<Department> expected = Collections.emptyList();
        List<Department> list = departmentDao.findByDepartmentName("").collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByByLocationId_found()
    {
        List<Department> expected = Collections.singletonList(testDepartment);
        List<Department> list = departmentDao.findByLocationId(0, 1, TEST_LID).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByLocationId_notFound()
    {
        List<Department> expected = Collections.emptyList();
        List<Department> list = departmentDao.findByLocationId(Long.MAX_VALUE).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByByManagerId_found()
    {
        List<Department> expected = Collections.singletonList(testDepartment);
        List<Department> list = departmentDao.findByManagerId(0, 1, TEST_LID).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByManagerId_notFound()
    {
        List<Department> expected = Collections.emptyList();
        List<Department> list = departmentDao.findByManagerId(Long.MAX_VALUE).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void updateDepartmentName()
    {
        Department expectedDepartment = new Department(
            testDepartment.getId(), testDepartment.getDepartmentName(), testDepartment.getManagerId(), testDepartment.getLocationId()
        );
        departmentDao.updateDepartmentName(testDepartment.getId(), expectedDepartment.getDepartmentName()).block();
        List<Department> expected = Collections.singletonList(expectedDepartment);
        List<Department> list = departmentDao.findByDepartmentName(expectedDepartment.getDepartmentName()).collectList().block();
        assertEquals(expected, list);
    }
}