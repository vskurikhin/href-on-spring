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
import reactor.core.publisher.Flux;
import su.svn.href.models.Region;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.test.H2Helper.createH2ConnectionFactory;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("Class RegionDao")
class RegionDaoTest
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
            createTestTableForRegions(client);

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
    RegionDao regionDao;

    @AfterAll
    static void drop()
    {
        dropTestTableForRegions(client);
    }

    @BeforeEach
    void setUp()
    {
        insertTestRegionToTable(client);
    }

    @AfterEach
    void clean()
    {
        deleteTestTableForRegions(client);
    }

    @Test
    void findById()
    {
        assertEquals(testRegion, regionDao.findById(TEST_LID).block());
    }

    @Test
    void findByRegionName()
    {
        Flux<Region> regionFlux = regionDao.findByRegionName(TEST_REGION_NAME);
        assertEquals(1, (long) regionFlux.count().block());
        List<Region> list = regionFlux.collectList().block();
        assertEquals(Collections.singletonList(testRegion), list);
    }
}