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
@Table("locations")
public class Location
{
    static final long serialVersionUID = -30L;

    @Id
    @Column("location_id")
    private String id;

    @Column("street_address")
    private String streetAddress;

    @Column("postal_code")
    private String postalCode;

    @Column("city")
    private String city;

    @Column("state_province")
    private String stateProvince;

    @Column("country_id")
    private String countryId;
}
