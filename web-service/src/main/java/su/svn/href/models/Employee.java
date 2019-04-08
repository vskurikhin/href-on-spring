package su.svn.href.models;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Employee
{
    static final long serialVersionUID = -50L;

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date hireDate;

    private String jobId;

    private Double salary;

    private Double commissionPct;

    private Long managerId;

    private Long departmentId;
}
