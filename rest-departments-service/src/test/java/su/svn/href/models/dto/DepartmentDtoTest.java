package su.svn.href.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.href.models.Employee;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.href.models.LocationTest.testLocation;
import static su.svn.href.models.ManagerTest.testManager;
import static su.svn.utils.TestData.*;

public class DepartmentDtoTest
{
    public static DepartmentDto testDepartmentDto = new DepartmentDto(
        TEST_ID, TEST_DEPARTMENT_NAME, testManager, testLocation, new LinkedList<>()
    );

    private List<Employee> testEmployeesList = Collections.emptyList();

    private DepartmentDto departmentDto;

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new DepartmentDto();
    }

    @Nested
    @DisplayName("constructing from map")
    class WhenConstructingFromMap
    {
        @Test
        void collectFromNull()
        {
            assertThrows(NullPointerException.class, () -> DepartmentDto.collectFromMap(null));
        }

        @Test
        void collectFromEmpty()
        {
            assertThrows(NullPointerException.class, () -> DepartmentDto.collectFromMap(new HashMap<>()));
        }

        @Test
        void collectFromMinimalValidMap()
        {
            Map<String, Object> mapTest = new HashMap<String, Object>() {{
                put("DEPARTMENT_ID", TEST_SID);
            }};
            DepartmentDto expected = new DepartmentDto(
                TEST_ID, "NULL", null, null, testDepartmentDto.getEmployees()
            );
            assertEquals(expected, DepartmentDto.collectFromMap(mapTest));
        }
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            departmentDto = new DepartmentDto();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(departmentDto).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("departmentName", "");
            assertThat(departmentDto).hasFieldOrPropertyWithValue("manager", null);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("location", null);
        }

        @Test
        @DisplayName("setter and getter for id")
        void testGetSetId()
        {
            departmentDto.setId(TEST_ID);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertEquals(TEST_ID, departmentDto.getId());
        }

        @Test
        @DisplayName("setter and getter for departmentName")
        void testGetSetCountryName()
        {
            departmentDto.setDepartmentName(TEST_DEPARTMENT_NAME);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("departmentName", TEST_DEPARTMENT_NAME);
            assertEquals(TEST_DEPARTMENT_NAME, departmentDto.getDepartmentName());
        }

        @Test
        @DisplayName("setter and getter for manager")
        void testGetSetManager()
        {
            departmentDto.setManager(testManager);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("manager", testManager);
            assertEquals(testManager, departmentDto.getManager());
        }

        @Test
        @DisplayName("setter and getter for location")
        void testGetSetLocation()
        {
            departmentDto.setLocation(testLocation);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("location", testLocation);
            assertEquals(testLocation, departmentDto.getLocation());
        }

        @Test
        @DisplayName("setter and getter for employees")
        void testGetSetEmployees()
        {
            departmentDto.setEmployees(testEmployeesList);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("employees", testEmployeesList);
            assertEquals(testEmployeesList, departmentDto.getEmployees());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            departmentDto = new DepartmentDto(TEST_ID, TEST_DEPARTMENT_NAME, testManager, testLocation, testEmployeesList);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(departmentDto).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("departmentName", TEST_DEPARTMENT_NAME);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("manager", testManager);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("location", testLocation);
            assertThat(departmentDto).hasFieldOrPropertyWithValue("employees", testEmployeesList);
        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new DepartmentDto(), departmentDto);
            DepartmentDto expected = new DepartmentDto(
                TEST_ID, TEST_DEPARTMENT_NAME, testManager, testLocation, new LinkedList<>()
            );
            assertEquals(expected.hashCode(), departmentDto.hashCode());
            assertEquals(expected, departmentDto);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(departmentDto.toString().length() > 0);
        }
    }
}