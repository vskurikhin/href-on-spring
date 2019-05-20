package su.svn.href.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("employees")
public class Employee
{
    static final long serialVersionUID = -50L;

    @Id
    @Column("employee_id")
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("hire_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date hireDate;

    @Column("job_id")
    private String jobId;

    @Column("salary")
    @JsonSerialize(using = MoneySerializer.class)
    private Double salary;

    @Column("commission_pct")
    @JsonSerialize(using = MoneySerializer.class)
    private Double commissionPct;

    @Column("manager_id")
    private Long managerId;

    @Column("department_id")
    private Long departmentId;


    public static class Builder
    {
        private Long id;
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

        private Builder() { /* None */ }

        public Builder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName)
        {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName)
        {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email)
        {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setHireDate(Date hireDate)
        {
            this.hireDate = hireDate;
            return this;
        }

        public Builder setJobId(String jobId)
        {
            this.jobId = jobId;
            return this;
        }

        public Builder setSalary(Double salary)
        {
            this.salary = salary;
            return this;
        }

        public Builder setCommissionPct(Double commissionPct)
        {
            this.commissionPct = commissionPct;
            return this;
        }

        public Builder setManagerId(Long managerId)
        {
            this.managerId = managerId;
            return this;
        }

        public Builder setDepartmentId(Long departmentId)
        {
            this.departmentId = departmentId;
            return this;
        }

        public Employee build()
        {
            return new Employee(
                id, firstName, lastName, email, phoneNumber, hireDate,
                jobId, salary, commissionPct, managerId, departmentId
            );
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }
}
