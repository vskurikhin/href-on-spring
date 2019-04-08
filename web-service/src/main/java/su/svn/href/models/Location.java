package su.svn.href.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Location
{
    static final long serialVersionUID = -30L;

    private long id;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String stateProvince;

    private String countryId;
}
