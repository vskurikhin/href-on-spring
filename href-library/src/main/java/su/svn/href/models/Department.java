package su.svn.href.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("departments")
public class Department
{
    static final long serialVersionUID = -40L;

    @Id
    @Column("department_id")
    private Long id;

    @Column("department_name")
    private String departmentName = "";

    @Column("manager_id")
    private Long managerId;

    @Column("location_id")
    private Long locationId;

    public static class Builder
    {
        private Long id;
        private String departmentName;
        private Long managerId;
        private Long locationId;

        private Builder() { /* None */ }

        public Builder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public Builder setDepartmentName(String departmentName)
        {
            this.departmentName = departmentName;
            return this;
        }

        public Builder setManagerId(Long managerId)
        {
            this.managerId = managerId;
            return this;
        }

        public Builder setLocationId(Long locationId)
        {
            this.locationId = locationId;
            return this;
        }

        public Department build()
        {
            return new Department(id, departmentName, managerId, locationId);
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }
}
