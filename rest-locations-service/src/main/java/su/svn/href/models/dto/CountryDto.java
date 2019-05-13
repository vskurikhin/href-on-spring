package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.Region;
import su.svn.utils.StringHelper;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CountryDto
{
    static final long serialVersionUID = -21L;

    private String id = "";

    private String countryName;

    private Region region;

    public static CountryDto collectFromMap(@NotNull Map<String, Object> map)
    {
        Long regionId = StringHelper.longOrNULL(map, "REGION_ID");
        String regionName = StringHelper.valueOrNULL(map, "REGION_NAME");
        Region region = null != regionId ? new Region(regionId, regionName) : null;

        String countryId = map.get("COUNTRY_ID").toString();
        String countryName = StringHelper.valueOrNULL(map, "COUNTRY_NAME");

        return new CountryDto(countryId, countryName, region);
    }
}
