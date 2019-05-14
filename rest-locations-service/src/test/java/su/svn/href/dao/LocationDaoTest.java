package su.svn.href.dao;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
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
import reactor.test.StepVerifier;
import su.svn.href.models.Location;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static su.svn.href.test.H2Helper.createTestTableForLocations;
import static su.svn.href.test.H2Helper.dropTestTableForLocations;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("Class LocationDao")
class LocationDaoTest
{
    public static Location testLocation = new Location(
        TEST_LID, TEST_STREET_ADDRESS, TEST_POSTAL_CODE, TEST_CITY, TEST_STATE_PROVINCE, TEST_SID
    );
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
    LocationDao locationDao;

    @BeforeEach
    void setUp()
    {
        createTestTableForLocations(client);
        client.insert()
            .into(Location.class)
            .using(testLocation)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    @AfterEach
    void clean()
    {
        dropTestTableForLocations(client);
    }

    @Test
    void findByStreetAddress_found()
    {
        List<Location> expected = Collections.singletonList(testLocation);
        List<Location> list = locationDao.findByStreetAddress(0, 1, TEST_STREET_ADDRESS).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void findByStreetAddress_notFound()
    {
        List<Location> expected = Collections.emptyList();
        List<Location> list = locationDao.findByStreetAddress("").collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void updateStreetAddress()
    {
        Location expectedLocation = new Location(
            testLocation.getId(), testLocation.getStreetAddress() + "_update", testLocation.getPostalCode(),
            testLocation.getCity(), testLocation.getStateProvince(), testLocation.getCountryId()
        );
        locationDao.updateStreetAddress(testLocation.getId(), expectedLocation.getStreetAddress()).block();
        List<Location> expected = Collections.singletonList(expectedLocation);
        List<Location> list = locationDao.findByStreetAddress(expectedLocation.getStreetAddress()).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void updatePostalCodes()
    {
        Location expectedLocation = new Location(
            testLocation.getId(), testLocation.getStreetAddress(), "update",
            testLocation.getCity(), testLocation.getStateProvince(), testLocation.getCountryId()
        );
        locationDao.updatePostalCode(testLocation.getId(), expectedLocation.getPostalCode()).block();
        List<Location> expected = Collections.singletonList(expectedLocation);
        List<Location> list = locationDao.findByPostalCode(expectedLocation.getPostalCode()).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void updateCity()
    {
        Location expectedLocation = new Location(
            testLocation.getId(), testLocation.getStreetAddress(), testLocation.getPostalCode(),
            testLocation.getCity() + "_update", testLocation.getStateProvince(), testLocation.getCountryId()
        );
        locationDao.updateCity(testLocation.getId(), expectedLocation.getCity()).block();
        List<Location> expected = Collections.singletonList(expectedLocation);
        List<Location> list = locationDao.findByCity(expectedLocation.getCity()).collectList().block();
        assertEquals(expected, list);
    }

    @Test
    void updateStateProvince()
    {
        Location expectedLocation = new Location(
            testLocation.getId(), testLocation.getStreetAddress(), testLocation.getPostalCode(),
            testLocation.getCity(), "update", testLocation.getCountryId()
        );
        locationDao.updateStateProvince(testLocation.getId(), expectedLocation.getStateProvince()).block();
        List<Location> expected = Collections.singletonList(expectedLocation);
        List<Location> list = locationDao.findByStateProvince(expectedLocation.getStateProvince()).collectList().block();
        assertEquals(expected, list);
    }
}