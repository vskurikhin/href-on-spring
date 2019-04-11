package su.svn.href.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "application.service.departments")
public class ServiceDepartmentsProperties
{
    public String host = "localhost";

    @NotNull
    public Integer port;
}
