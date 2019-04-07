package su.svn.href.models.dto;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static su.svn.href.models.EmployeeTest.testEmployee;
import static su.svn.href.models.LocationTest.testLocation;
import static su.svn.href.models.ManagerTest.testManager;
import static su.svn.utils.TestData.TEST_DEPARTMENT_NAME;
import static su.svn.utils.TestData.TEST_ID;

public class DepartmentDtoTest
{
    public static DepartmentDto testDepartmentDto = new DepartmentDto(
        TEST_ID, TEST_DEPARTMENT_NAME, testManager, testLocation, new LinkedList<>()
    );
}