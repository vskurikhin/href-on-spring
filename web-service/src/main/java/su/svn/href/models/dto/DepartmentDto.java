package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.Employee;
import su.svn.href.models.Location;
import su.svn.href.models.Manager;

import java.util.Collection;
import java.util.LinkedList;

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
}
