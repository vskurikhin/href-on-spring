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
import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_V1_COUNTRIES;
import static su.svn.href.controllers.Constants.REST_V1_REGIONS;
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
        saved.setId("4");
        saved.setRegionId(1L);

        when(countryDao.save(country0)).thenReturn(Mono.just(saved));

        mvc.perform(
            post(REST_API + REST_V1_COUNTRIES)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(country0))
        ).andExpect(status().isCreated());
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
        when(countryDao.findAll(offset, limit)).thenReturn(Flux.just(country1, country2));


        MvcResult result = mvc
            .perform(get(REST_API + REST_V1_COUNTRIES + "/range?page=1&size=2&sort=none").contentType(APPLICATION_JSON))
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
        when(countryDao.findAllOrderById(offset, limit)).thenReturn(Flux.just(country1, country2));


        MvcResult result = mvc
            .perform(get(REST_API + REST_V1_COUNTRIES + "/range?page=1&size=2&sort=id").contentType(APPLICATION_JSON))
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
        when(countryDao.findAllOrderByCountryName(offset, limit)).thenReturn(Flux.just(country1, country2));


        MvcResult result = mvc
            .perform(get(REST_API + REST_V1_COUNTRIES + "/range?page=1&size=2&sort=name")
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
}