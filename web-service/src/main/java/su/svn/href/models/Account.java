package su.svn.href.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("accounts")
public class Account
{
    @Id
    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("active")
    private boolean active;
}
