package su.svn.href.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import su.svn.href.models.Department;
import su.svn.href.models.Manager;
import su.svn.href.models.MoneySerializer;

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

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date hireDate;

    private String jobId; // TODO

    @JsonSerialize(using = MoneySerializer.class)
    private Double salary;

    @JsonSerialize(using = MoneySerializer.class)
    private Double commissionPct;

    private Manager manager;

    private Department department;
}
