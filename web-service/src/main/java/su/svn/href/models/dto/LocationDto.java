package su.svn.href.models.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationDto
{
    static final long serialVersionUID = -31L;

    private long id;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String stateProvince;

    private CountryDto country;
}
