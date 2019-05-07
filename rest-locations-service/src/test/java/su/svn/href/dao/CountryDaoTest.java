package su.svn.href.dao;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
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
import su.svn.href.models.Country;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.models.CountryTest.createTestTableForCountries;
import static su.svn.href.models.CountryTest.testCountry;
import static su.svn.href.models.RegionTest.createTestTableForRegions;
import static su.svn.utils.TestData.TEST_COUNTRY_NAME;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("Class CountryDao")
class CountryDaoTest
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
            createTestTableForRegions(client);
            createTestTableForCountries(client);

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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CountryDao countryDao;

    @AfterAll
    static void clean()
    {
        databaseClientExecuteSql(client, "DROP TABLE IF EXISTS regions CASCADE");
    }

    @BeforeEach
    void setUp()
    {
    }

    @Test
    void findByCountryName_found()
    {
        List<Country> expected = Collections.singletonList(testCountry);
        List<Country> list = countryDao.findByCountryName(TEST_COUNTRY_NAME).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByCountryName_notFound()
    {
        List<Country> expected = Collections.emptyList();
        List<Country> list = countryDao.findByCountryName("").collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findAll()
    {
        List<Country> expected = Collections.singletonList(testCountry);
        List<Country> list = countryDao.findAll(0, 1).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findAllOrderById()
    {
        List<Country> expected = Collections.singletonList(testCountry);
        List<Country> list = countryDao.findAllOrderById(0, 1).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findAllOrderByCountryName()
    {
        List<Country> expected = Collections.singletonList(testCountry);
        List<Country> list = countryDao.findAllOrderByCountryName(0, 1).collectList().block();
        assertEquals(expected, list);
    }
}