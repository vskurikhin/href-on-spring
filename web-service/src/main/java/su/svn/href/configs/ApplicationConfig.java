package su.svn.href.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan
public class ApplicationConfig
{
    private ServiceDepartmentsProperties sdp;

    private ServiceEmployeesProperties sep;

    public ApplicationConfig(ServiceDepartmentsProperties serviceDepartmentsProperties,
                             ServiceEmployeesProperties serviceEmployeesProperties)
    {
        sdp = serviceDepartmentsProperties;
        sep = serviceEmployeesProperties;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
