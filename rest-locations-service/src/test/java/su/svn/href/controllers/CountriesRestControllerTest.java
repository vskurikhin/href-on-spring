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
import su.svn.href.dao.CountryDao;
import su.svn.href.models.Country;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.CountryFinder;
import su.svn.href.services.LocationFinder;

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
@WebMvcTest(CountriesRestController.class)
@DisplayName("Class CountriesRestController")
class CountriesRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryDao countryDao;

    @MockBean(name = "countryMapFinder")
    private CountryFinder countryFinder;

    @MockBean
    private PageSettings paging;

    private Country country0;
    private Country country1;
    private Country country2;

    @BeforeEach
    void setUp()
    {
        country0 = createCountry0();
        country1 = createCountry1();
        country2 = createCountry2();
    }

    @Test
    @DisplayName("when creating country, it is created")
    void create_isCreated() throws Exception
    {
        Country saved = createCountry0();
        saved.setId("CC");
        saved.setRegionId(1L);

        when(countryDao.save(saved)).thenReturn(Mono.just(saved));

        mvc.perform(
            post("/rest/api/v1/countries")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(saved))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when creating region, got bad request")
    void create_isBadRequest() throws Exception
    {
        Country test = createCountry0();
        country0.setId("");
        mvc.perform(
            post("/rest/api/v1/countries")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(test))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("read all")
    void readAll() throws Exception
    {
        PageSettings pageSettings = new PageSettings(1, 10, 2);
        int limit = pageSettings.getLimit(2);
        int offset = pageSettings.getOffset(1, 2);

        when(paging.getLimit(2)).thenReturn(limit);
        when(paging.getOffset(1,2)).thenReturn(offset);
        when(countryFinder.findAllCountries(offset, limit, "none"))
            .thenReturn(Flux.just(country1, country2));


        MvcResult result = mvc
            .perform(get("/rest/api/v1/countries/range?page=1&size=2&sort=none")
                .contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
           //.andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(country1.getId())))
            .andExpect(jsonPath("$[0].countryName", is(country1.getCountryName())))
            .andExpect(jsonPath("$[0].regionId", is(country1.getRegionId().intValue())))
            .andExpect(jsonPath("$[1].id", is(country2.getId())))
            .andExpect(jsonPath("$[1].countryName", is(country2.getCountryName())))
            .andExpect(jsonPath("$[1].regionId", is(country2.getRegionId().intValue())));
    }

    @Test
    @DisplayName("read all")
    void readAllOrderById() throws Exception
    {
        PageSettings pageSettings = new PageSettings(1, 10, 2);
        int limit = pageSettings.getLimit(2);
        int offset = pageSettings.getOffset(1, 2);

        when(paging.getLimit(2)).thenReturn(limit);
        when(paging.getOffset(1,2)).thenReturn(offset);
        when(countryFinder.findAllCountries(offset, limit, "id"))
            .thenReturn(Flux.just(country1, country2));


        MvcResult result = mvc
            .perform(get("/rest/api/v1/countries/range?page=1&size=2&sort=id")
                .contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            //.andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(country1.getId())))
            .andExpect(jsonPath("$[0].countryName", is(country1.getCountryName())))
            .andExpect(jsonPath("$[0].regionId", is(country1.getRegionId().intValue())))
            .andExpect(jsonPath("$[1].id", is(country2.getId())))
            .andExpect(jsonPath("$[1].countryName", is(country2.getCountryName())))
            .andExpect(jsonPath("$[1].regionId", is(country2.getRegionId().intValue())));
    }

    @Test
    @DisplayName("read all")
    void readAllOrderByCountryName() throws Exception
    {
        PageSettings pageSettings = new PageSettings(1, 10, 2);
        int limit = pageSettings.getLimit(2);
        int offset = pageSettings.getOffset(1, 2);

        when(paging.getLimit(2)).thenReturn(limit);
        when(paging.getOffset(1,2)).thenReturn(offset);
        when(countryFinder.findAllCountries(offset, limit, "name"))
            .thenReturn(Flux.just(country1, country2));


        MvcResult result = mvc
            .perform(get(REST_API + REST_V1_COUNTRIES + REST_RANGE + "?page=1&size=2&sort=name")
                .contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            //.andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(country1.getId())))
            .andExpect(jsonPath("$[0].countryName", is(country1.getCountryName())))
            .andExpect(jsonPath("$[0].regionId", is(country1.getRegionId().intValue())))
            .andExpect(jsonPath("$[1].id", is(country2.getId())))
            .andExpect(jsonPath("$[1].countryName", is(country2.getCountryName())))
            .andExpect(jsonPath("$[1].regionId", is(country2.getRegionId().intValue())));
    }

    private void readById(String id, Country country) throws Exception
    {
        when(countryDao.findById(id)).thenReturn(Mono.just(country));

        MvcResult result = mvc
            .perform(get("/rest/api/v1/countries/{id}", id).contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id", is(country.getId())))
            .andExpect(jsonPath("$.countryName", is(country.getCountryName())))
            .andExpect(jsonPath("$.regionId", is(country.getRegionId().intValue())));
    }

    @Test
    @DisplayName("read by id")
    void readById_isOk() throws Exception
    {
        readById("AA", country1);
        readById("BB", country2);
    }

    @Test
    @DisplayName("when reading, got bad request")
    void readById_isBadRequest() throws Exception
    {
        mvc.perform(get("/rest/api/v1/countries/{id}", "A")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        mvc.perform(get("/rest/api/v1/countries/{id}", "AAA")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when update is ok")
    void update_isOk() throws Exception
    {
        Country saved = createCountry1();

        when(countryDao.save(country1)).thenReturn(Mono.just(saved));

        mvc.perform(
            put("/rest/api/v1/countries")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(country1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when updating, got bad request")
    void update_isBadRequest() throws Exception
    {
        mvc.perform(
            put("/rest/api/v1/countries")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(country0))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when updating, got empty")
    void update_returnEmpty_exception() throws Exception
    {
        when(countryDao.save(country1)).thenReturn(Mono.empty());

        MvcResult result = mvc.perform(
            post("/rest/api/v1/countries")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(country1))
        ).andReturn();

        mvc.perform(asyncDispatch(result))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("delete")
    void deleteTest() throws Exception
    {
        when(countryDao.findById("AA")).thenReturn(Mono.just(country1));
        when(countryDao.delete(country1)).thenReturn(Mono.just(country1).then());

        mvc.perform(delete("/rest/api/v1/countries/{id}" , "AA"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("when deleting, got bad request")
    void delete_isBadRequest() throws Exception
    {
        when(countryDao.findById("00")).thenReturn(Mono.empty());

        mvc.perform(delete("/rest/api/v1/countries/{id}" , "00"))
            .andExpect(status().isBadRequest());
    }
}
