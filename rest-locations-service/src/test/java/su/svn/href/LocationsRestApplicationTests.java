package su.svn.href;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import su.svn.href.configs.ApplicationConfig;
import su.svn.href.configs.R2dbcConfiguration;
import su.svn.href.configs.YamlApplProperties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LocationsRestApplication.class)
@DisplayName("Class LocationsRestApplication Integration of Spring IoC/DI")
public class LocationsRestApplicationTests
{
    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    R2dbcConfiguration r2dbcConfiguration;

    @Autowired
    YamlApplProperties yamlApplProperties;

    @Test
    void contextLoads()
    {
        assertThat(applicationConfig).hasFieldOrPropertyWithValue("yp", yamlApplProperties);
        assertThat(r2dbcConfiguration).hasFieldOrPropertyWithValue("yap", yamlApplProperties);
        assertThat(yamlApplProperties).hasFieldOrProperty("locale").isNotNull();
        assertThat(yamlApplProperties).hasFieldOrProperty("r2dbc").isNotNull();
        assertThat(yamlApplProperties).hasFieldOrProperty("paging").isNotNull();
        System.out.println("locale = " + yamlApplProperties.getLocale());
        System.out.println("r2dbc  = " + yamlApplProperties.getR2dbc());
        System.out.println("paging = " + yamlApplProperties.getPaging());
    }
}
