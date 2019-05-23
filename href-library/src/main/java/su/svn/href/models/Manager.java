package su.svn.href.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.utils.StringHelper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("employees")
public class Manager
{
    static final long serialVersionUID = -59L;

    @Id
    @Column("employee_id")
    private long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    public static boolean isValidId(Long id)
    {
        return null != id && id > 0;
    }

    public static boolean isValidFieldName(String name)
    {
        return StringHelper.isValidFieldName(name, Location.class);
    }

    public static boolean isValidFirstName(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static boolean isValidLastName(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static boolean isValidEmail(String name)
    {
        return StringHelper.isValidValue(name);
    }

    public static boolean isValidPhoneNumber(String name)
    {
        return StringHelper.isValidValue(name);
    }
}
