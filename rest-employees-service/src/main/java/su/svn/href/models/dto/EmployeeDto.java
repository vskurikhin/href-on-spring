package su.svn.href.models.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.href.models.Department;
import su.svn.href.models.Manager;
import su.svn.utils.StringHelper;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmployeeDto
{
    static final long serialVersionUID = -51L;

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date hireDate;

    private String jobId; // TODO

    private Double salary;

    private Double commissionPct;

    private Manager manager;

    private Department department;

    public static EmployeeDto collectFromMap(Map<String, Object> map)
    {
        System.out.println("map = " + map);

        long employeeId = Long.parseLong(map.get("EMPLOYEE_ID").toString());
        String firstName = StringHelper.valueOrNULL(map, "FIRST_NAME");
        String lastName = StringHelper.valueOrNULL(map, "LAST_NAME");
        String email = StringHelper.valueOrNULL(map, "EMAIL");
        String phoneNumber = StringHelper.valueOrNULL(map, "PHONE_NUMBER");
        Date hireDate = StringHelper.dateOrNULL(map, "HIRE_DATE");
        String jobId = StringHelper.valueOrNULL(map, "JOB_ID");
        Double salary = StringHelper.doubleOrNULL(map, "SALARY");
        Double commissionPct = StringHelper.doubleOrNULL(map, "COMMISSION_PCT");

        Long managerId = StringHelper.longOrNULL(map, "MANAGER_ID");
        String managerFirstName = StringHelper.valueOrNULL(map, "MANAGER_FIRST_NAME");
        String managerLastName = StringHelper.valueOrNULL(map, "MANAGER_LAST_NAME");
        String managerEmail = StringHelper.valueOrNULL(map, "MANAGER_EMAIL");
        String managerPhoneNumber = StringHelper.valueOrNULL(map, "MANAGER_PHONE_NUMBER");

        Long departmentId = StringHelper.longOrNULL(map, "DEPARTMENT_ID");
        String departmentName = StringHelper.valueOrNULL(map, "DEPARTMENT_NAME");
        Long locationId = StringHelper.longOrNULL(map, "LOCATION_ID");
        Long departmentManagerId = StringHelper.longOrNULL(map, "DEPT_MANAGER_ID");

        return new EmployeeDto(
            employeeId, firstName, lastName, email, phoneNumber, hireDate, jobId, salary, commissionPct,
            null != managerId
                ? new Manager(managerId, managerFirstName, managerLastName, managerEmail, managerPhoneNumber)
                : null,
            null != departmentId
                ? new Department(departmentId, departmentName, departmentManagerId, locationId)
                : null
        );
    }
}
