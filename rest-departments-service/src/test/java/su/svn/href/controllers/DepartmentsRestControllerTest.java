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
import su.svn.href.dao.DepartmentDao;
import su.svn.href.dao.DepartmentFullDao;
import su.svn.href.models.Department;
import su.svn.href.models.dto.DepartmentDto;
import su.svn.href.models.helpers.PageSettings;
import su.svn.href.services.DepartmentFinder;
import su.svn.href.services.DepartmentUpdater;

import java.util.Arrays;
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
@WebMvcTest(DepartmentsRestController.class)
@DisplayName("Class DepartmentsRestController")
class DepartmentsRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepartmentDao departmentDao;

    @MockBean
    private DepartmentFullDao departmentFullDao;

    @MockBean(name = "departmentCaseFinder")
    private DepartmentFinder departmentFinder;

    @MockBean
    private DepartmentUpdater departmentUpdater;

    @MockBean
    private PageSettings paging;

    private Department department0;
    private Department department1;
    private Department department2;

    private DepartmentDto departmentDto0;
    private DepartmentDto departmentDto1;
    private DepartmentDto departmentDto2;

    @BeforeEach
    void setUp()
    {
        department0 = createDepartment0();
        department1 = createDepartment1();
        department2 = createDepartment2();
        departmentDto0 = createDepartmentDto0();
        departmentDto1 = createDepartmentDto1();
        departmentDto2 = createDepartmentDto2();
    }

    @Test
    @DisplayName("when creating department, it is created")
    void create_isCreated() throws Exception
    {
        Department saved = createDepartment0();
        saved.setId(4L);

        when(departmentDao.save(saved)).thenReturn(Mono.just(saved));

        mvc.perform(
            post("/rest/api/v1/departments")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(saved))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when creating region, got bad request")
    void create_isBadRequest() throws Exception
    {
        Department test = createDepartment0();
        department0.setId(-1L);
        mvc.perform(
            post("/rest/api/v1/departments")
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
            .perform(get("/rest/api/v1/departments" + range + "?page=1&size=2&sort=" + sort)
                .contentType(APPLICATION_JSON))
            .andReturn();
    }

    private void readRange(BiConsumer<Integer, Integer> findMock, String sort, Department... departments) throws Exception
    {
        MvcResult result = prepareReadRange(findMock, sort, REST_RANGE);

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(departments[0].getId().intValue())))
            .andExpect(jsonPath("$[0].departmentName", is(departments[0].getDepartmentName())))
            .andExpect(jsonPath("$[0].managerId", is(departments[0].getManagerId().intValue())))
            .andExpect(jsonPath("$[0].locationId", is(departments[0].getLocationId().intValue())))
            .andExpect(jsonPath("$[1].id", is(departments[1].getId().intValue())))
            .andExpect(jsonPath("$[1].departmentName", is(departments[1].getDepartmentName())))
            .andExpect(jsonPath("$[1].managerId", is(departments[1].getManagerId().intValue())))
            .andExpect(jsonPath("$[1].locationId", is(departments[1].getLocationId().intValue())));
    }

    @Test
    @DisplayName("read all")
    void readAll() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(departmentFinder.findAllDepartments(offset, limit, "none"))
                .thenReturn(Flux.just(department1, department2));

        readRange(findMock, "none", department1, department2);
    }

    @Test
    @DisplayName("read all order by id")
    void readAllOrderById() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(departmentFinder.findAllDepartments(offset, limit, "id"))
                .thenReturn(Flux.just(department1, department2));

        readRange(findMock, "id", department1, department2);
    }

    @Test
    @DisplayName("read all order by departmentName")
    void readAllOrderByDepartmentName() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(departmentFinder.findAllDepartments(offset, limit, "name"))
                .thenReturn(Flux.just(department1, department2));

        readRange(findMock, "name", department1, department2);
    }

    private void readFullRange(BiConsumer<Integer, Integer> findMock, String sort, DepartmentDto... departments)
    throws Exception
    {
        MvcResult result = prepareReadRange(findMock, sort, REST_RANGE_FULL);

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].id", is((new Long(departments[0].getId()).intValue()))))
            .andExpect(jsonPath("$[0].departmentName", is(departments[0].getDepartmentName())))

            .andExpect(jsonPath("$[0].manager.id", is(new Long(departments[0].getManager().getId()).intValue())))
            .andExpect(jsonPath("$[0].manager.firstName", is(departments[0].getManager().getFirstName())))
            .andExpect(jsonPath("$[0].manager.lastName", is(departments[0].getManager().getLastName())))
            .andExpect(jsonPath("$[0].manager.email", is(departments[0].getManager().getEmail())))
            .andExpect(jsonPath("$[0].manager.phoneNumber", is(departments[0].getManager().getPhoneNumber())))

            .andExpect(jsonPath("$[0].location.id", is(departments[0].getLocation().getId().intValue())))
            .andExpect(jsonPath("$[0].location.streetAddress", is(departments[0].getLocation().getStreetAddress())))
            .andExpect(jsonPath("$[0].location.postalCode", is(departments[0].getLocation().getPostalCode())))
            .andExpect(jsonPath("$[0].location.city", is(departments[0].getLocation().getCity())))
            .andExpect(jsonPath("$[0].location.stateProvince", is(departments[0].getLocation().getStateProvince())))
            .andExpect(jsonPath("$[0].location.countryId", is(departments[0].getLocation().getCountryId() )))


            .andExpect(jsonPath("$[1].id", is((new Long(departments[1].getId()).intValue()))))
            .andExpect(jsonPath("$[1].departmentName", is(departments[1].getDepartmentName())))

            .andExpect(jsonPath("$[1].manager.id", is(new Long(departments[1].getManager().getId()).intValue())))
            .andExpect(jsonPath("$[1].manager.firstName", is(departments[1].getManager().getFirstName())))
            .andExpect(jsonPath("$[1].manager.lastName", is(departments[1].getManager().getLastName())))
            .andExpect(jsonPath("$[1].manager.email", is(departments[1].getManager().getEmail())))
            .andExpect(jsonPath("$[1].manager.phoneNumber", is(departments[1].getManager().getPhoneNumber())))

            .andExpect(jsonPath("$[1].location.id", is(departments[1].getLocation().getId().intValue())))
            .andExpect(jsonPath("$[1].location.streetAddress", is(departments[1].getLocation().getStreetAddress())))
            .andExpect(jsonPath("$[1].location.postalCode", is(departments[1].getLocation().getPostalCode())))
            .andExpect(jsonPath("$[1].location.city", is(departments[1].getLocation().getCity())))
            .andExpect(jsonPath("$[1].location.stateProvince", is(departments[1].getLocation().getStateProvince())))
            .andExpect(jsonPath("$[1].location.countryId", is(departments[1].getLocation().getCountryId() )))
            ;
    }

    @Test
    @DisplayName("read all full")
    void readAllFull() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(departmentFinder.findAllFullDepartments(offset, limit, "none"))
                .thenReturn(Mono.just(Arrays.asList(departmentDto1, departmentDto2)));

        readFullRange(findMock, "none", departmentDto1, departmentDto2);
    }

    @Test
    @DisplayName("read all full order by id")
    void readAllFullOrderById() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(departmentFinder.findAllFullDepartments(offset, limit, "id"))
                .thenReturn(Mono.just(Arrays.asList(departmentDto1, departmentDto2)));

        readFullRange(findMock, "id", departmentDto1, departmentDto2);
    }

    @Test
    @DisplayName("read all full order by departmentName")
    void readAllFullOrderByDepartmentName() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(departmentFinder.findAllFullDepartments(offset, limit, "name"))
                .thenReturn(Mono.just(Arrays.asList(departmentDto1, departmentDto2)));

        readFullRange(findMock, "name", departmentDto1, departmentDto2);
    }

    @Test
    @DisplayName("when update is ok")
    void update_isOk() throws Exception
    {
        Department saved = createDepartment1();

        when(departmentDao.save(department1)).thenReturn(Mono.just(saved));

        mvc.perform(
            put("/rest/api/v1/departments")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(department1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when updating, got bad request")
    void update_isBadRequest() throws Exception
    {
        mvc.perform(
            put("/rest/api/v1/departments")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(department0))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("delete")
    void deleteTest() throws Exception
    {
        when(departmentDao.findById(1L)).thenReturn(Mono.just(department1));
        when(departmentDao.delete(department1)).thenReturn(Mono.just(department1).then());

        mvc.perform(delete("/rest/api/v1/departments/{id}" , 1L))
            .andExpect(status().isNoContent());
    }
}