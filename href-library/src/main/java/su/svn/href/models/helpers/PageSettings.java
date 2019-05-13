package su.svn.href.models.helpers;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PageSettings
{
    private int min;

    private int max;

    private int size;

    public PageSettings(int min, int max)
    {
        this(min, max, 0);
    }

    public int getLimit(int size)
    {
        return this.size = size < this.min ? this.min : (size > this.max ? this.max : size);
    }

    public int getOffset(int page, int size)
    {
        return (page < 1 ? 0 : page - 1) * size;
    }

    int getOffset(int page)
    {
        return getOffset(page, this.size);
    }
}
