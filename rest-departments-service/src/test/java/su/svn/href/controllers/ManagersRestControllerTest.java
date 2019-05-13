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
import su.svn.href.dao.ManagerDao;
import su.svn.href.models.Manager;
import su.svn.href.models.helpers.PageSettings;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static su.svn.href.controllers.Constants.*;
import static su.svn.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ManagersRestController.class)
@DisplayName("Class ManagersRestController")
class ManagersRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManagerDao managerDao;

    @MockBean
    private PageSettings paging;

    private Manager manager0;
    private Manager manager1;
    private Manager manager2;

    @BeforeEach
    void setUp()
    {
        manager0 = createManager0();
        manager1 = createManager1();
        manager2 = createManager2();
    }

    @Test
    @DisplayName("read all")
    void readAll() throws Exception
    {
        when(managerDao.findAll()).thenReturn(Flux.just(manager1, manager2));

        MvcResult result = mvc
            .perform(get(REST_API + REST_V1_MANAGERS + REST_ALL)
                .contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].id", is(new Long(manager1.getId()).intValue())))
            .andExpect(jsonPath("$[0].firstName", is(manager1.getFirstName())))
            .andExpect(jsonPath("$[0].lastName", is(manager1.getLastName())))
            .andExpect(jsonPath("$[0].email", is(manager1.getEmail())))
            .andExpect(jsonPath("$[0].phoneNumber", is(manager1.getPhoneNumber())))

            .andExpect(jsonPath("$[1].id", is(new Long(manager2.getId()).intValue())))
            .andExpect(jsonPath("$[1].firstName", is(manager2.getFirstName())))
            .andExpect(jsonPath("$[1].lastName", is(manager2.getLastName())))
            .andExpect(jsonPath("$[1].email", is(manager2.getEmail())))
            .andExpect(jsonPath("$[1].phoneNumber", is(manager2.getPhoneNumber())))
            ;
    }

    private void readById(long id, Manager manager) throws Exception
    {
        when(managerDao.findById(id)).thenReturn(Mono.just(manager));

        MvcResult result = mvc
            .perform(get(REST_API + REST_V1_MANAGERS + "/{id}", id).contentType(APPLICATION_JSON))
            .andReturn();

        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))

            .andExpect(jsonPath("$.id", is(new Long(manager.getId()).intValue())))
            .andExpect(jsonPath("$.firstName", is(manager.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(manager.getLastName())))
            .andExpect(jsonPath("$.email", is(manager.getEmail())))
            .andExpect(jsonPath("$.phoneNumber", is(manager.getPhoneNumber())))
            ;
    }

    @Test
    @DisplayName("read by id")
    void readById_isOk() throws Exception
    {
        readById(1L, manager1);
        readById(2L, manager2);
    }

    @Test
    @DisplayName("when reading, got bad request")
    void readById_isBadRequest() throws Exception
    {
        mvc.perform(get(REST_API + REST_V1_MANAGERS + "/{id}", -1L).contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}