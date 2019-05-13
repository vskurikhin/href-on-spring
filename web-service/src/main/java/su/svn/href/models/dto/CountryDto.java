package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.Region;

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
}
