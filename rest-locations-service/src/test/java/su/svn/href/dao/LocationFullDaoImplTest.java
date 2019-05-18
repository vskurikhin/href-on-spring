package su.svn.href.dao;

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
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.models.dto.LocationDtoTest.testLocationDto;
import static su.svn.href.test.H2Helper.createH2ConnectionFactory;
import static su.svn.href.test.H2Helper.createTestTableForLocations;
import static su.svn.href.test.H2Helper.dropTestTableForLocations;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("Class LocationFullDao")
class LocationFullDaoImplTest
{
    public static Location testLocation = new Location(
        TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );

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
            insertTestRegionToTable(client);

            createTestTableForCountries(client);
            insertTestCountryToTable(client);

            createTestTableForLocations(client);
            insertTestLocationToTable(client);

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
        dropTestTableForLocations(client);
        dropTestTableForCountries(client);
        dropTestTableForRegions(client);
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

    @Test
    void findAllSortByStreetAddress()
    {
        List<LocationDto> expected = Collections.singletonList(testLocationDto);
        List<LocationDto> list = locationFullDao.findAll(0, 1, "street_address").collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findAllSortByCity()
    {
        List<LocationDto> expected = Collections.singletonList(testLocationDto);
        List<LocationDto> list = locationFullDao.findAll(0, 1, "city").collectList().block();
        assertEquals(expected, list);
    }
}