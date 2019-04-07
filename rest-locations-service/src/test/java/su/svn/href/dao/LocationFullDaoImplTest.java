package su.svn.href.dao;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterAll;
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
import su.svn.href.models.dto.LocationDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.models.CountryTest.testCountriesDb;
import static su.svn.href.models.LocationTest.testLocationsDb;
import static su.svn.href.models.RegionTest.testRegionsDb;
import static su.svn.href.models.dto.LocationDtoTest.testLocationDto;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class LocationFullDaoImplTest
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
            testRegionsDb(client);
            testCountriesDb(client);
            testLocationsDb(client);

            return client;
        }

        @Bean
        public ReactiveDataAccessStrategy reactiveDataAccessStrategy()
        {
            return new DefaultReactiveDataAccessStrategy(new PostgresDialect());
        }

        @Bean
        public LocationFullDao locationFullDao()
        {
            return new LocationFullDaoImpl(client);
        }
    }

    @Autowired
    LocationFullDao locationFullDao;

    @AfterAll
    static void clean()
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS locations CASCADE");
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS countries CASCADE");
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS regions CASCADE");
    }

    @BeforeEach
    void setUp()
    {
    }

    @Test
    void findById()
    {
        assertEquals(testLocationDto, locationFullDao.findById(TEST_ID).block());
    }

    @Test
    void findAll()
    {
        List<LocationDto> expected = Collections.singletonList(testLocationDto);
        List<LocationDto> list = locationFullDao.findAll(0, 1).collectList().block();
        assertEquals(expected, list);
    }
}