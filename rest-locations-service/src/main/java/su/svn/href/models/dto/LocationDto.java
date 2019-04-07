package su.svn.href.models.dto;

import lombok.*;
import su.svn.utils.StringHelper;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationDto
{
    static final long serialVersionUID = -31L;

    private String id;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String stateProvince;

    private CountryDto country;

    public static LocationDto collectFromMap(Map<String, Object> map)
    {
        String locationId = map.get("LOCATION_ID").toString();
        String streetAddress = StringHelper.stringOrNULL(map.getOrDefault("STREET_ADDRESS", "null"));
        String postalCode = StringHelper.stringOrNULL(map.getOrDefault("POSTAL_CODE", "null"));
        String city = map.get("CITY").toString();
        String stateProvince = StringHelper.stringOrNULL(map.getOrDefault("STATE_PROVINCE", "null"));

        return new LocationDto(
            locationId, streetAddress, postalCode, city, stateProvince,
            CountryDto.collectFromMap(map)
        );
    }
}
