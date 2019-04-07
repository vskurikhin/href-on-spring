package su.svn.href.models.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.href.models.Region;
import su.svn.utils.StringHelper;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CountryDto
{
    static final long serialVersionUID = -21L;

    private String id;

    private String countryName;

    private Region region;

    public static CountryDto collectFromMap(Map<String, Object> map)
    {
        long regionId = Long.parseLong(map.get("REGION_ID").toString());
        String regionName = StringHelper.stringOrNULL(map.getOrDefault("REGION_NAME", "null"));

        String countryId = map.get("COUNTRY_ID").toString();
        String countryName = StringHelper.stringOrNULL(map.getOrDefault("COUNTRY_NAME", "null"));

        return new CountryDto(countryId, countryName, new Region(regionId, regionName));
    }
}
