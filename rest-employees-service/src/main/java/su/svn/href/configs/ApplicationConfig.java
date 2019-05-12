package su.svn.href.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import su.svn.href.models.helpers.PageSettings;

import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan
public class ApplicationConfig
{
    private YamlApplProperties yp;

    public ApplicationConfig(YamlApplProperties yamlApplProperties) {
        yp = yamlApplProperties;
    }

    @SuppressWarnings("Duplicates")
    @Bean
    public PageSettings paging()
    {
        Properties paging = yp.getPaging();
        Integer min = null;
        Integer max = null;

        try {
            if ( ! Objects.isNull(paging)) {
                min = Integer.parseInt(paging.getProperty("min"));
                max = Integer.parseInt(paging.getProperty("max"));
            }
        }
        catch (NumberFormatException ignored) { /* TODO */ }

        return new PageSettings(min != null ? min : 10, max != null ? max : 1000);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
