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
@SpringBootTest(classes = DepartmentsRestApplication.class)
@DisplayName("Class DepartmentsRestApplication integration of Spring IoC/DI")
class DepartmentsRestApplicationTest
{
    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    R2dbcConfiguration r2dbcConfiguration;

    @Autowired
    YamlApplProperties yamlApplProperties;

    @SuppressWarnings("Duplicates")
    @Test
    void contextLoads()
    {
        assertThat(applicationConfig).hasFieldOrPropertyWithValue("yp", yamlApplProperties);
        assertThat(r2dbcConfiguration).hasFieldOrPropertyWithValue("yap", yamlApplProperties);
        assertThat(yamlApplProperties).hasFieldOrProperty("locale").isNotNull();
        assertThat(yamlApplProperties).hasFieldOrProperty("r2dbc").isNotNull();
        assertThat(yamlApplProperties).hasFieldOrProperty("paging").isNotNull();
    }
}
