package su.svn.href.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan
public class ApplicationConfig
{
    private ServicesProperties servicesProperties;

    public ApplicationConfig(ServicesProperties servicesProperties)
    {
        this.servicesProperties = servicesProperties;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
