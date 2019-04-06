package su.svn.href.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("employees")
public class Employee
{
    static final long serialVersionUID = -9L;

    @Id
    @Column("employee_id")
    private long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("hire_date")
    private LocalDate hireDate;

    @Column("job_id")
    private String jobId;

    @Column("salary")
    private Double salary;

    @Column("commission_pct")
    private Double commissionPct;

    @Column("manager_id")
    private Long managerId;

    @Column("department_id")
    private Long departmentId;
}
