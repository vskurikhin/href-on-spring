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
public class DepartmentFull
{
    static final long serialVersionUID = -7L;

    @Id
    @Column("department_id")
    private long id;

    @Column("department_name")
    private String departmentName;

    private Manager manager;

    private Location location;
}
