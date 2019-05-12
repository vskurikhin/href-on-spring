package su.svn.href.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@Data
@ConfigurationProperties(prefix = "application.services")
public class ServicesProperties
{
    private Properties locations;

    private Properties departments;

    private Properties employees;

    public Properties getLocations()
    {
        return locations;
    }

    public void setLocations(Properties locations)
    {
        this.locations = locations;
    }

    public Properties getDepartments()
    {
        return departments;
    }

    public void setDepartments(Properties departments)
    {
        this.departments = departments;
    }

    public Properties getEmployees()
    {
        return employees;
    }

    public void setEmployees(Properties employees)
    {
        this.employees = employees;
    }
}
