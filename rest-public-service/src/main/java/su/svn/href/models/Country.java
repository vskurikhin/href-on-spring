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
@Table("countries")
public class Country
{
    static final long serialVersionUID = -2L;

    @Id
    @Column("country_id")
    private String id;

    @Column("country_name")
    private String countryName;

    @Column("region_id")
    private long regionId;
}
