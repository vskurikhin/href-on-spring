package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.Location;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationDataTables
{
    static final long serialVersionUID = -32L;

    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    private List<Location> data;
}
