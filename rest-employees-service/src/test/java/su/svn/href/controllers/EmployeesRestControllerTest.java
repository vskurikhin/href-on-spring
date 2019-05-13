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
import su.svn.href.dao.EmployeeDao;
import su.svn.href.dao.EmployeeFullDao;
import su.svn.href.models.Employee;
import su.svn.href.models.dto.EmployeeDto;
import su.svn.href.models.helpers.PageSettings;

import java.util.function.BiConsumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static su.svn.href.controllers.Constants.*;
import static su.svn.utils.TestData.*;
import static su.svn.utils.TestUtil.convertObjectToJsonBytes;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeesRestController.class)
@DisplayName("Class EmployeesRestController")
class EmployeesRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeDao employeeDao;

    @MockBean
    private EmployeeFullDao employeeFullDao;

    @MockBean
    private PageSettings paging;

    private Employee employee0;
    private Employee employee1;
    private Employee employee2;

    private EmployeeDto employeeDto0;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;

    @BeforeEach
    void setUp()
    {
        employee0 = createEmployee0();
        employee1 = createEmployee1();
        employee2 = createEmployee2();
        employeeDto0 = createEmployeeDto0();
        employeeDto1 = createEmployeeDto1();
        employeeDto2 = createEmployeeDto2();
    }

    @Test
    @DisplayName("when creating employee, it is created")
    void create_isCreated() throws Exception
    {
        Employee saved = createEmployee0();
        saved.setId(4L);

        when(employeeDao.save(saved)).thenReturn(Mono.just(saved));

        mvc.perform(
            post(REST_API + REST_V1_EMPLOYEES)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(saved))
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when creating region, got bad request")
    void create_isBadRequest() throws Exception
    {
        Employee test = createEmployee0();
        employee0.setId(-1L);
        mvc.perform(
            post(REST_API + REST_V1_EMPLOYEES)
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
            .perform(get(REST_API + REST_V1_EMPLOYEES + range + "?page=1&size=2&sort=" + sort)
                .contentType(APPLICATION_JSON))
            .andReturn();

    }

    private void readRange(BiConsumer<Integer, Integer> findMock, String sort, Employee... employees) throws Exception
    {
        MvcResult result = prepareReadRange(findMock, sort, REST_RANGE);

        mvc.perform(asyncDispatch(result))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))

            .andExpect(jsonPath("$[0].id", is(employees[0].getId().intValue())))
            .andExpect(jsonPath("$[0].firstName", is(employees[0].getFirstName())))
            .andExpect(jsonPath("$[0].lastName", is(employees[0].getLastName())))
            .andExpect(jsonPath("$[0].email", is(employees[0].getEmail())))
            .andExpect(jsonPath("$[0].phoneNumber", is(employees[0].getPhoneNumber())))
            // TODO .andExpect(jsonPath("$[0].hireDate", is(employees[0].getHireDate())))
            .andExpect(jsonPath("$[0].jobId", is(employees[0].getJobId())))
            .andExpect(jsonPath("$[0].salary", is(employees[0].getSalary())))
            .andExpect(jsonPath("$[0].commissionPct", is(employees[0].getSalary())))
            .andExpect(jsonPath("$[0].managerId", is(employees[0].getManagerId().intValue())))
            .andExpect(jsonPath("$[0].departmentId", is(employees[0].getDepartmentId().intValue())))

            .andExpect(jsonPath("$[1].id", is(employees[1].getId().intValue())))
            .andExpect(jsonPath("$[1].firstName", is(employees[1].getFirstName())))
            .andExpect(jsonPath("$[1].lastName", is(employees[1].getLastName())))
            .andExpect(jsonPath("$[1].email", is(employees[1].getEmail())))
            .andExpect(jsonPath("$[1].phoneNumber", is(employees[1].getPhoneNumber())))
            // TODO .andExpect(jsonPath("$[1].hireDate", is(employees[1].getHireDate())))
            .andExpect(jsonPath("$[1].jobId", is(employees[1].getJobId())))
            .andExpect(jsonPath("$[1].salary", is(employees[1].getSalary())))
            .andExpect(jsonPath("$[1].commissionPct", is(employees[1].getSalary())))
            .andExpect(jsonPath("$[1].managerId", is(employees[1].getManagerId().intValue())))
            .andExpect(jsonPath("$[1].departmentId", is(employees[1].getDepartmentId().intValue())))
            ;
    }

    @Test
    @DisplayName("read all")
    void readAll() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAll(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "none", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by id")
    void readAllOrderById() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderById(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "id", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by firstName")
    void readAllOrderByFirstName() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderByFirstName(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "first_name", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by lastName")
    void readAllOrderByLastName() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderByLastName(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "last_name", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by email")
    void readAllOrderByEmail() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderByEmail(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "email", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by phoneNumber")
    void readAllOrderByPhoneNumber() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderByPhoneNumber(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "phone_number", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by hireDate")
    void readAllOrderByHireDate() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderByHireDate(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "hire_date", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by salary")
    void readAllOrderBySalary() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderBySalary(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "salary", employee1, employee2);
    }

    @Test
    @DisplayName("read all order by commissionPct")
    void readAllOrderByCommissionPct() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeDao.findAllOrderByCommissionPct(offset, limit)).thenReturn(Flux.just(employee1, employee2));

        readRange(findMock, "commission_pct", employee1, employee2);
    }

    private void readFullRange(BiConsumer<Integer, Integer> findMock, String sort, EmployeeDto... employees)
    throws Exception
    {
        MvcResult result = prepareReadRange(findMock, sort, REST_RANGE_FULL);
/*
    [{
        "id":1, "firstName":"test_first_name_0", "lastName":"test_last_name_0", "email":"test_email_0", "phoneNumber":
        "test_phone_number_0", "hireDate":"2019-05-11T21:00:00.000+0000", "jobId":"test_jobId_1", "salary":
        1.0, "commissionPct":1.0, "manager":{
            "id":1, "firstName":"test_first_name_0", "lastName":"test_last_name_0", "email":
            "test_email_0", "phoneNumber":"test_phone_number_0"
        },"department":{
            "id":1, "departmentName":"test_department_name_1", "managerId":1, "locationId":1
        }
    },{
        "id":2, "firstName":"test_first_name_0", "lastName":"test_last_name_0", "email":"test_email_0", "phoneNumber":
        "test_phone_number_0", "hireDate":"2019-05-11T21:00:00.000+0000", "jobId":"test_jobId_2", "salary":
        2.0, "commissionPct":2.0, "manager":{
            "id":2, "firstName":"test_first_name_0", "lastName":"test_last_name_0", "email":
            "test_email_0", "phoneNumber":"test_phone_number_0"
        },"department":{
            "id":2, "departmentName":"test_department_name_2", "managerId":2, "locationId":2
        }
    }]
*/
        mvc.perform(asyncDispatch(result))
            // .andDo(print());
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))


            .andExpect(jsonPath("$[0].id", is(((Long) employees[0].getId()).intValue())))
            .andExpect(jsonPath("$[0].firstName", is(employees[0].getFirstName())))
            .andExpect(jsonPath("$[0].lastName", is(employees[0].getLastName())))
            .andExpect(jsonPath("$[0].email", is(employees[0].getEmail())))
            .andExpect(jsonPath("$[0].phoneNumber", is(employees[0].getPhoneNumber())))
            // TODO .andExpect(jsonPath("$[0].hireDate", is(employees[0].getHireDate())))
            .andExpect(jsonPath("$[0].jobId", is(employees[0].getJobId())))
            .andExpect(jsonPath("$[0].salary", is(employees[0].getSalary())))
            .andExpect(jsonPath("$[0].commissionPct", is(employees[0].getSalary())))

            .andExpect(jsonPath("$[0].manager.id", is(((Long) employees[0].getManager().getId()).intValue())))
            .andExpect(jsonPath("$[0].manager.firstName", is(employees[0].getManager().getFirstName())))
            .andExpect(jsonPath("$[0].manager.lastName", is(employees[0].getManager().getLastName())))
            .andExpect(jsonPath("$[0].manager.email", is(employees[0].getManager().getEmail())))
            .andExpect(jsonPath("$[0].manager.phoneNumber", is(employees[0].getManager().getPhoneNumber())))

            .andExpect(jsonPath("$[0].department.id", is((employees[0].getDepartment().getId().intValue()))))
            .andExpect(jsonPath("$[0].department.departmentName", is(employees[0].getDepartment().getDepartmentName())))
            .andExpect(jsonPath("$[0].department.managerId", is((employees[0].getDepartment().getManagerId().intValue()))))
            .andExpect(jsonPath("$[0].department.locationId", is((employees[0].getDepartment().getLocationId().intValue()))))


            .andExpect(jsonPath("$[1].id", is(((Long) employees[1].getId()).intValue())))
            .andExpect(jsonPath("$[1].firstName", is(employees[1].getFirstName())))
            .andExpect(jsonPath("$[1].lastName", is(employees[1].getLastName())))
            .andExpect(jsonPath("$[1].email", is(employees[1].getEmail())))
            .andExpect(jsonPath("$[1].phoneNumber", is(employees[1].getPhoneNumber())))
            // TODO .andExpect(jsonPath("$[1].hireDate", is(employees[1].getHireDate())))
            .andExpect(jsonPath("$[1].jobId", is(employees[1].getJobId())))
            .andExpect(jsonPath("$[1].salary", is(employees[1].getSalary())))
            .andExpect(jsonPath("$[1].commissionPct", is(employees[1].getSalary())))
            .andExpect(jsonPath("$[1].manager.id", is(((Long) employees[1].getManager().getId()).intValue())))
            .andExpect(jsonPath("$[1].manager.firstName", is(employees[1].getManager().getFirstName())))
            .andExpect(jsonPath("$[1].manager.lastName", is(employees[1].getManager().getLastName())))
            .andExpect(jsonPath("$[1].manager.email", is(employees[1].getManager().getEmail())))
            .andExpect(jsonPath("$[1].manager.phoneNumber", is(employees[1].getManager().getPhoneNumber())))
            .andExpect(jsonPath("$[1].department.id", is((employees[1].getDepartment().getId().intValue()))))
            .andExpect(jsonPath("$[1].department.departmentName", is(employees[1].getDepartment().getDepartmentName())))
            .andExpect(jsonPath("$[1].department.managerId", is((employees[1].getDepartment().getManagerId().intValue()))))
            .andExpect(jsonPath("$[1].department.locationId", is((employees[1].getDepartment().getLocationId().intValue()))))
            ;
    }

    @Test
    @DisplayName("read all full")
    void readAllFull() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit)).thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "none", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by id")
    void readAllFullOrderById() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.employee_id"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "id", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by firstName")
    void readAllFullOrderByFirstName() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.first_name"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "first_name", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by lastName")
    void readAllFullOrderByLastName() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.last_name"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "last_name", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by email")
    void readAllFullOrderByEmail() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.email"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "email", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by phoneNumber")
    void readAllFullOrderByPhoneNumber() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.phone_number"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "phone_number", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by hireDate")
    void readAllFullOrderByHireDate() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.hire_date"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "hire_date", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by salary")
    void readAllFullOrderBySalary() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.salary"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "salary", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("read all full order by commissionPct")
    void readAllFullOrderByCommissionPct() throws Exception
    {
        BiConsumer<Integer, Integer> findMock = (offset, limit) ->
            when(employeeFullDao.findAll(offset, limit, "e.commission_pct"))
                .thenReturn(Flux.just(employeeDto1, employeeDto2));

        readFullRange(findMock, "commission_pct", employeeDto1, employeeDto2);
    }

    @Test
    @DisplayName("when update is ok")
    void update_isOk() throws Exception
    {
        Employee saved = createEmployee1();

        when(employeeDao.save(employee1)).thenReturn(Mono.just(saved));

        mvc.perform(
            put(REST_API + REST_V1_EMPLOYEES)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(employee1))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when updating, got bad request")
    void update_isBadRequest() throws Exception
    {
        mvc.perform(
            put(REST_API + REST_V1_EMPLOYEES)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(employee0))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("delete")
    void deleteTest() throws Exception
    {
        when(employeeDao.findById(1L)).thenReturn(Mono.just(employee1));
        when(employeeDao.delete(employee1)).thenReturn(Mono.just(employee1).then());

        mvc.perform(delete(REST_API + REST_V1_EMPLOYEES + "/{id}" , 1L))
            .andExpect(status().isNoContent());
    }
}