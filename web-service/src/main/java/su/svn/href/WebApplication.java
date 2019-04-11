package su.svn.href;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import su.svn.href.configs.ServiceDepartmentsProperties;
import su.svn.href.configs.ServiceEmployeesProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    ServiceDepartmentsProperties.class,
    ServiceEmployeesProperties.class
})
public class WebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }
}