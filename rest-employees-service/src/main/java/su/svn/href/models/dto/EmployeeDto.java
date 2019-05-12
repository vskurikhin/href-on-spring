package su.svn.href.models.dto;

import lombok.*;
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
        long employeeId = Long.parseLong(map.get("EMPLOYEE_ID").toString());
        String firstName = StringHelper.valueOrNULL(map, "FIRST_NAME");
        String lastName = StringHelper.valueOrNULL(map, "LAST_NAME");
        String email = StringHelper.valueOrNULL(map, "EMAIL");
        String phoneNumber = StringHelper.valueOrNULL(map, "PHONE_NUMBER");
        Date hireDate = StringHelper.dateOrNULL(map, "HIRE_DATE");
        String jobId = StringHelper.valueOrNULL(map, "JOB_ID");
        Double salary = StringHelper.doubleOrNULL(map, "SALARY");
        Double commissionPct = StringHelper.doubleOrNULL(map, "COMMISSION_PCT");

        Long managerId = StringHelper.longOrNULL(map, "M_MANAGER_ID");
        String managerFirstName = StringHelper.valueOrNULL(map, "M_FIRST_NAME");
        String managerLastName = StringHelper.valueOrNULL(map, "M_LAST_NAME");
        String managerEmail = StringHelper.valueOrNULL(map, "M_EMAIL");
        String managerPhoneNumber = StringHelper.valueOrNULL(map, "M_PHONE_NUMBER");

        Long departmentId = StringHelper.longOrNULL(map, "D_DEPARTMENT_ID");
        String departmentName = StringHelper.valueOrNULL(map, "D_DEPARTMENT_NAME");
        Long departmentManagerId = StringHelper.longOrNULL(map, "D_MANAGER_ID");
        Long locationId = StringHelper.longOrNULL(map, "D_LOCATION_ID");

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
