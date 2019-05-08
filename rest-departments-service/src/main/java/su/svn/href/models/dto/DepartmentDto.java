package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.Employee;
import su.svn.href.models.Location;
import su.svn.href.models.Manager;
import su.svn.utils.StringHelper;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DepartmentDto
{
    static final long serialVersionUID = -41L;

    private long id;

    private String departmentName = "";

    private Manager manager;

    private Location location;

    private Collection<Employee> employees = new LinkedList<>();

    public static DepartmentDto collectFromMap(@NotNull Map<String, Object> map)
    {
        long departmentId = Long.parseLong(map.get("DEPARTMENT_ID").toString());
        String departmentName = StringHelper.valueOrNULL(map, "DEPARTMENT_NAME");

        Long managerId = StringHelper.longOrNULL(map, "MANAGER_ID");
        String firstName = StringHelper.valueOrNULL(map, "FIRST_NAME");
        String lastName = StringHelper.valueOrNULL(map, "LAST_NAME");
        String email = StringHelper.valueOrNULL(map, "EMAIL");
        String phoneNumber = StringHelper.valueOrNULL(map, "PHONE_NUMBER");

        Long locationId = StringHelper.longOrNULL(map, "LOCATION_ID");
        String streetAddress = StringHelper.valueOrNULL(map, "STREET_ADDRESS");
        String postalCode = StringHelper.valueOrNULL(map, "POSTAL_CODE");
        String city = StringHelper.valueOrNULL(map, "CITY");
        String stateProvince = StringHelper.valueOrNULL(map, "STATE_PROVINCE");
        String countryId = StringHelper.valueOrNULL(map, "COUNTRY_ID");

        return new DepartmentDto(
            departmentId, departmentName,
            null == managerId ? null : new Manager(managerId, firstName, lastName, email, phoneNumber),
            null != locationId
                ? new Location(locationId, streetAddress, postalCode, city, stateProvince, countryId)
                : null,
            new LinkedList<>()
        );
    }
}
