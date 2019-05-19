package su.svn.href.configs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@EnableConfigurationProperties({ServicesProperties.class})
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
