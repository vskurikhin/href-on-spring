package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.Department;
import su.svn.href.models.Employee;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmployeeDataTables<T>
{
    static final long serialVersionUID = -63L;

    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    private List<Employee> data;
}
