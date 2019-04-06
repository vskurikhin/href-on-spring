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
@Table("regions")
public class Region
{
    static final long serialVersionUID = -1L;

    @Id
    @Column("region_id")
    private long id;

    @Column("region_name")
    private String regionName;
}
