package su.svn.href.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Region
{
    static final long serialVersionUID = -10L;

    private long id;

    private String regionName;
}
