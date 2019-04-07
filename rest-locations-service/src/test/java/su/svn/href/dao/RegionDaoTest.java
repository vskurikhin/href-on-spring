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
import reactor.core.publisher.Flux;
import su.svn.href.models.Region;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.models.RegionTest.testRegionsDb;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestData.TEST_REGION_NAME;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class RegionDaoTest
{
    static Region testRegion = new Region(TEST_ID, TEST_REGION_NAME);

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
            testRegionsDb(client);

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
    static void clean()
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS regions CASCADE");
    }

    @Test
    void findById()
    {
        assertEquals(testRegion, regionDao.findById(TEST_ID).block());
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