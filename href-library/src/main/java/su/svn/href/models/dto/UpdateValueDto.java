package su.svn.href.models.dto;

import lombok.*;
import su.svn.href.models.UpdateValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateValueDto
{
    String name;

    String pk;

    String value;

    public UpdateValue<Long> convertWithLongPk()
    {
        long id = Long.parseLong(pk);

        return new UpdateValue<>(name, id, value);
    }

    public UpdateValue<String> convertWithStringPk()
    {
        return new UpdateValue<>(name, pk, value);
    }
}
