package su.svn.href.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Manager
{
    static final long serialVersionUID = -59L;

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
