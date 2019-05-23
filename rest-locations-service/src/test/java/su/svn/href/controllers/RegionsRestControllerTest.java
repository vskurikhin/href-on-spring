package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
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
import su.svn.href.dao.RegionDao;
import su.svn.href.models.Region;

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
import static su.svn.href.controllers.Constants.REST_V1_REGIONS;
import static su.svn.utils.TestData.createRegion0;
import static su.svn.utils.TestData.createRegion1;
import static su.svn.utils.TestData.createRegion2;
import static su.svn.utils.TestUtil.convertObjectToJsonBytes;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegionsRestController.class)
@DisplayName("Class RegionsRestController")
class RegionsRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionDao regionDao;

    private Region region0;
    private Region region1;
    private Region region2;

    @BeforeEach
    void setUp()
    {
        region0 = createRegion0();
        region1 = createRegion1();
        region2 = createRegion2();
    }

    @Test
    @DisplayName("when creating region, it is created")
    void create_isCreated() throws Exception
    {
        Region saved = createRegion0();
        saved.setId(4L);

        when(regionDao.save(region0)).thenReturn(Mono.just(saved));

        System.out.println(REST_API + REST_V1_REGIONS);
        mvc.perform(
            post("/rest/api/v1/regions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(region0))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when creating region, got bad request")
    void create_isBadRequest() throws Exception
    {
        mvc.perform(
            post("/rest/api/v1/regions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(region1))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("read all")
    void readAll() throws Exception
    {
        when(regionDao.findAll()).thenReturn(Flux.just(region1, region2));

        MvcResult result = mvc
            .perform(get("/rest/api/v1/regions/all").contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(region1.getId().intValue())))
            .andExpect(jsonPath("$[0].regionName", is(region1.getRegionName())))
            .andExpect(jsonPath("$[1].id", is(region2.getId().intValue())))
            .andExpect(jsonPath("$[1].regionName", is(region2.getRegionName())));
    }

    private void readById(long id, Region region) throws Exception
    {
        when(regionDao.findById(id)).thenReturn(Mono.just(region));

        MvcResult result = mvc
            .perform(get("/rest/api/v1/regions/{id}", id).contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id", is(region.getId().intValue())))
            .andExpect(jsonPath("$.regionName", is(region.getRegionName())));
    }

    @Test
    @DisplayName("read by id")
    void readById_isOk() throws Exception
    {
        readById(1L, region1);
        readById(2L, region2);
    }

    @Test
    @DisplayName("when reading, got bad request")
    void readById_isBadRequest() throws Exception
    {
        mvc.perform(get("/rest/api/v1/regions/{id}", -1L).contentType(APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when update is ok")
    void update_isOk() throws Exception
    {
        Region saved = createRegion1();

        when(regionDao.save(region1)).thenReturn(Mono.just(saved));

        mvc.perform(
            put("/rest/api/v1/regions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(region1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when updating, got bad request")
    void update_isBadRequest() throws Exception
    {
        mvc.perform(
            put("/rest/api/v1/regions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(region0))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when updating, got empty")
    void update_returnEmpty_exception() throws Exception
    {
        when(regionDao.save(region0)).thenReturn(Mono.empty());

        MvcResult result = mvc.perform(
            post("/rest/api/v1/regions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(region0))
        ).andReturn();

        mvc.perform(asyncDispatch(result))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when updating, got exception")
    void update_exception() throws Exception
    {
        when(regionDao.save(region0)).thenThrow(PostgresqlServerErrorException.class);

        mvc.perform(
            post("/rest/api/v1/regions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(region0))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("delete")
    void deleteTest() throws Exception
    {
        when(regionDao.findById(1L)).thenReturn(Mono.just(region1));
        when(regionDao.delete(region1)).thenReturn(Mono.just(region1).then());

        mvc.perform(delete("/rest/api/v1/regions/{id}" , 1L))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("when deleting, got bad request")
    void delete_isBadRequest() throws Exception
    {
        when(regionDao.findById(0L)).thenReturn(Mono.empty());

        mvc.perform(delete("/rest/api/v1/regions/{id}" , 0L))
            .andExpect(status().isBadRequest());
    }
}