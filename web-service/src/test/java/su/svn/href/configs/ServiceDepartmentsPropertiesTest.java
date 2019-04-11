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
@SpringBootTest(classes = { ServiceDepartmentsPropertiesTest.TestConfiguration.class })
@ActiveProfiles("happy-path")
class ServiceDepartmentsPropertiesTest
{
    @Autowired
    private ServiceDepartmentsProperties sdp;

    @Test
    public void should_Populate_MyConfigurationProperties() {
        assertThat(sdp.getHost()).isEqualTo("localhost");
        assertThat(sdp.getPort()).isEqualTo(8003);
    }

    @EnableConfigurationProperties(ServiceDepartmentsProperties.class)
    public static class TestConfiguration {
        // nothing
    }
}