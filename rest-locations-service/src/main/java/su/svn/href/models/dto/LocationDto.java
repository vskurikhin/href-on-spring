package su.svn.href.models.dto;

import lombok.*;
import su.svn.utils.StringHelper;

import javax.validation.constraints.NotNull;
import java.util.Map;

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

    public static LocationDto collectFromMap(@NotNull Map<String, Object> map)
    {
        long locationId = Long.parseLong(map.get("LOCATION_ID").toString());
        String streetAddress = StringHelper.valueOrNULL(map, "STREET_ADDRESS");
        String postalCode = StringHelper.valueOrNULL(map, "POSTAL_CODE");
        String city = StringHelper.valueOrNULL(map, "CITY");
        String stateProvince = StringHelper.valueOrNULL(map, "STATE_PROVINCE");
        Object countryId = map.get("COUNTRY_ID");

        return new LocationDto(
            locationId, streetAddress, postalCode, city, stateProvince,
            null != countryId ? CountryDto.collectFromMap(map) : null
        );
    }
}
