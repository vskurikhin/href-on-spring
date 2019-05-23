package su.svn.href.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import sun.misc.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("countries")
public class Country
{
    static final long serialVersionUID = -20L;

    private static Pattern pattern = Pattern.compile("[A-Z][A-Z]");

    @Id
    @Column("country_id")
    private String id = "";

    @Column("country_name")
    private String countryName;

    @Column("region_id")
    private Long regionId;

    public static boolean isValidId(String id)
    {
        return null != id && id.length() == 2 && pattern.matcher(id).matches();
    }
}
