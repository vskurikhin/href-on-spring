package su.svn.href.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateValue<K>
{
    String name;

    K pk;

    String value;
}
