package su.svn.href.models.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.href.models.Department;
import su.svn.href.models.Manager;

import java.util.Date;

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
}
