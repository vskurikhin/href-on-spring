package su.svn.href.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.LocationDao;
import su.svn.href.dao.LocationFullDao;
import su.svn.href.models.Location;
import su.svn.href.models.dto.LocationDto;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.LocationFinder;
import su.svn.href.services.LocationUpdater;

import java.util.function.BiConsumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static su.svn.href.controllers.Constants.*;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.convertObjectToJsonBytes;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LocationsRestController.class)
@DisplayName("Class LocationsRestController")
class LocationsRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationDao locationDao;

    @MockBean
    private LocationFullDao locationFullDao;

    @MockBean
    private LocationFinder locationFinder;

    @MockBean
    private LocationUpdater locationUpdater;

    @MockBean
    private PageSettings paging;

    private Location location0;
    private Location location1;
    private Location location2;

    private LocationDto locationDto0;
    private LocationDto locationDto1;
    private LocationDto locationDto2;

    @BeforeEach
    void setUp()
    {
        location0 = createLocation0();
        location1 = createLocation1();
        location2 = createLocation2();
        locationDto0 = createLocationDto0();
        locationDto1 = createLocationDto1();
        locationDto2 = createLocationDto2();
    }

    @Test
    @DisplayName("when creating location, it is created")
    void create_isCreated() throws Exception
    {
        Location saved = createLocation0();
        saved.setId(4L);

        when(locationDao.save(saved)).thenReturn(Mono.just(saved));

        mvc.perform(
            post("/rest/api/v1/locations")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(saved))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when creating region, got bad request")
    void create_isBadRequest() throws Exception
    {
        Location test = createLocation0();
        location0.setId(-1L);
        mvc.perform(
            post("/rest/api/v1/locations")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(test))
        ).andExpect(status().isBadRequest());
    }

    private MvcResult prepareReadRange(BiConsumer<Integer, Integer> findMock, String sort, String range)
    throws Exception
    {
        PageSettings pageSettings = new PageSettings(1, 10, 2);
        int limit = pageSettings.getLimit(2);
        int offset = pageSettings.getOffset(1, 2);

        when(paging.getLimit(2)).thenReturn(limit);
        when(paging.getOffset(1,2)).thenReturn(offset);
        findMock.accept(offset, limit);

        return mvc
            .perform(get("/rest/api/v1/locations" + range + "?page=1&size=2&sort=" + sort)
                .contentType(APPLICATION_JSON))
            .andReturn();

    }

    private void readRange(BiConsumer<Integer, Integer> findMock, String sort, Location... locations) throws Exception
    {
        MvcResult result = prepareReadRange(findMock, sort, REST_RANGE);

        mvc.perform(asyncDispatch(result))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(locations[0].getId().intValue())))
            .andExpect(jsonPath("$[0].streetAddress", is(locations[0].getStreetAddress())))
            .andExpect(jsonPath("$[0].postalCode", is(locations[0].getPostalCode())))
            .andExpect(jsonPath("$[0].city", is(locations[0].getCity())))
            .andExpect(jsonPath("$[0].stateProvince", is(locations[0].getStateProvince())))
            .andExpect(jsonPath("$[0].countryId", is(locations[0].getCountryId())))
            .andExpect(jsonPath("$[1].id", is(locations[1].getId().intValue())))
            .andExpect(jsonPath("$[1].streetAddress", is(locations[1].getStreetAddress())))
            .andExpect(jsonPath("$[1].postalCode", is(locations[1].getPostalCode())))
            .andExpect(jsonPath("$[1].city", is(locations[1].getCity())))
            .andExpect(jsonPath("$[1].stateProvince", is(locations[1].getStateProvince())))
            .andExpect(jsonPath("$[1].countryId", is(locations[1].getCountryId())));
    }

    @Test
    @DisplayName("read all")
    void readAll() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllLocations(offset, limit, "none"))
                .thenReturn(Flux.just(location1, location2));

        readRange(findMock, "none", location1, location2);
    }

    @Test
    @DisplayName("read all order by id")
    void readAllOrderById() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllLocations(offset, limit, "id"))
                .thenReturn(Flux.just(location1, location2));

        readRange(findMock, "id", location1, location2);
    }

    @Test
    @DisplayName("read all order by street")
    void readAllOrderByStreetAddress() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllLocations(offset, limit, "street"))
                .thenReturn(Flux.just(location1, location2));

        readRange(findMock, "street", location1, location2);
    }

    @Test
    @DisplayName("read all order by state")
    void readAllOrderByStateProvince() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllLocations(offset, limit, "state"))
                .thenReturn(Flux.just(location1, location2));

        readRange(findMock, "state", location1, location2);
    }

    @Test
    @DisplayName("read all order by city")
    void readAllOrderByCity() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllLocations(offset, limit, "city"))
                .thenReturn(Flux.just(location1, location2));

        readRange(findMock, "city", location1, location2);
    }

    private void readFullRange(BiConsumer<Integer, Integer> findMock, String sort, LocationDto... locations)
    throws Exception
    {
        MvcResult result = prepareReadRange(findMock, sort, REST_RANGE_FULL);

        mvc.perform(asyncDispatch(result))
             //.andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is((new Long(locations[0].getId()).intValue()))))
            .andExpect(jsonPath("$[0].streetAddress", is(locations[0].getStreetAddress())))
            .andExpect(jsonPath("$[0].postalCode", is(locations[0].getPostalCode())))
            .andExpect(jsonPath("$[0].city", is(locations[0].getCity())))
            .andExpect(jsonPath("$[0].stateProvince", is(locations[0].getStateProvince())))
            .andExpect(jsonPath("$[0].country.id", is(locations[0].getCountry().getId())))
            .andExpect(jsonPath("$[0].country.countryName", is(locations[0].getCountry().getCountryName())))
            .andExpect(jsonPath("$[0].country.region.id", is(locations[0].getCountry().getRegion().getId().intValue())))
            .andExpect(jsonPath("$[0].country.region.regionName", is(locations[0].getCountry().getRegion().getRegionName())))
            .andExpect(jsonPath("$[1].id", is((new Long(locations[1].getId()).intValue()))))
            .andExpect(jsonPath("$[1].streetAddress", is(locations[1].getStreetAddress())))
            .andExpect(jsonPath("$[1].postalCode", is(locations[1].getPostalCode())))
            .andExpect(jsonPath("$[1].city", is(locations[1].getCity())))
            .andExpect(jsonPath("$[1].stateProvince", is(locations[1].getStateProvince())))
            .andExpect(jsonPath("$[1].country.id", is(locations[1].getCountry().getId())))
            .andExpect(jsonPath("$[1].country.countryName", is(locations[1].getCountry().getCountryName())))
            .andExpect(jsonPath("$[1].country.region.id", is(locations[1].getCountry().getRegion().getId().intValue())))
            .andExpect(jsonPath("$[1].country.region.regionName", is(locations[1].getCountry().getRegion().getRegionName())));
    }

    @Test
    @DisplayName("read all full")
    void readAllFull() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllFullLocations(offset, limit, "none"))
                .thenReturn(Flux.just(locationDto1, locationDto2));

        readFullRange(findMock, "none", locationDto1, locationDto2);
    }

    @Test
    @DisplayName("read all full order by street")
    void readAllFullOrderByStreetAddress() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllFullLocations(offset, limit, "street"))
                .thenReturn(Flux.just(locationDto1, locationDto2));

        readFullRange(findMock, "street", locationDto1, locationDto2);
    }

    @Test
    @DisplayName("read all full order by state")
    void readAllFullOrderByStateProvince() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllFullLocations(offset, limit, "state"))
                .thenReturn(Flux.just(locationDto1, locationDto2));

        readFullRange(findMock, "state", locationDto1, locationDto2);
    }

    @Test
    @DisplayName("read all full order by city")
    void readAllFullOrderByCity() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(locationFinder.findAllFullLocations(offset, limit, "city"))
                .thenReturn(Flux.just(locationDto1, locationDto2));

        readFullRange(findMock, "city", locationDto1, locationDto2);
    }

    @Test
    @DisplayName("when update is ok")
    void update_isOk() throws Exception
    {
        Location saved = createLocation1();

        when(locationDao.save(location1)).thenReturn(Mono.just(saved));

        mvc.perform(
            put("/rest/api/v1/locations")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(location1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when updating, got bad request")
    void update_isBadRequest() throws Exception
    {
        mvc.perform(
            put("/rest/api/v1/locations")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(location0))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when update streetAddress is ok")
    void updateStreetAddress() throws Exception
    {
        when(locationUpdater.updateLocation(anyString(), any()))
            .thenReturn(Mono.just(1));

        mvc.perform(
            put("/rest/api/v1/locations/update")
                .contentType(APPLICATION_JSON_UTF8)
                .param("field", "streetAddress")
                .content(convertObjectToJsonBytes(location1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when update postalCode is ok")
    void updatePostalCodes() throws Exception
    {
        when(locationUpdater.updateLocation(anyString(), any()))
            .thenReturn(Mono.just(1));

        mvc.perform(
            put("/rest/api/v1/locations/update")
                .contentType(APPLICATION_JSON_UTF8)
                .param("field", "postalCode")
                .content(convertObjectToJsonBytes(location1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when update city is ok")
    void updateCity() throws Exception
    {
        when(locationUpdater.updateLocation(anyString(), any()))
            .thenReturn(Mono.just(1));

        mvc.perform(
            put("/rest/api/v1/locations/update")
                .contentType(APPLICATION_JSON_UTF8)
                .param("field", "city")
                .content(convertObjectToJsonBytes(location1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when update stateProvince is ok")
    void updateStateProvince() throws Exception
    {
        when(locationUpdater.updateLocation(anyString(), any()))
            .thenReturn(Mono.just(1));

        mvc.perform(
            put("/rest/api/v1/locations/update")
                .contentType(APPLICATION_JSON_UTF8)
                .param("field", "stateProvince")
                .content(convertObjectToJsonBytes(location1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete")
    void deleteTest() throws Exception
    {
        when(locationDao.findById(1L)).thenReturn(Mono.just(location1));
        when(locationDao.delete(location1)).thenReturn(Mono.just(location1).then());

        mvc.perform(delete("/rest/api/v1/locations/{id}", 1L))
            .andExpect(status().isNoContent());
    }
}