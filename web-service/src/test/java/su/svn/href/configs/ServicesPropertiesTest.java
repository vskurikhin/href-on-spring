package su.svn.href.configs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ServicesPropertiesTest.TestConfiguration.class})
@ActiveProfiles("happy-path")
class ServicesPropertiesTest
{
    @Autowired
    private ServicesProperties servicesProperties;

    @Test
    public void should_Populate_MyConfigurationProperties()
    {
        assertThat(servicesProperties.getLocations().getProperty("host")).isEqualTo("localhost");
        assertThat(servicesProperties.getLocations().getProperty("port")).isEqualTo("8001");

        assertThat(servicesProperties.getDepartments().getProperty("host")).isEqualTo("localhost");
        assertThat(servicesProperties.getDepartments().getProperty("port")).isEqualTo("8002");

        assertThat(servicesProperties.getEmployees().getProperty("host")).isEqualTo("localhost");
        assertThat(servicesProperties.getEmployees().getProperty("port")).isEqualTo("8003");
    }

    @EnableConfigurationProperties(ServicesProperties.class)
    public static class TestConfiguration { /* nothing */ }
}