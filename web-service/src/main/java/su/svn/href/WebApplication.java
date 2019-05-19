
package su.svn.href;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import su.svn.href.configs.ServicesProperties;

@SpringBootApplication
@EnableConfigurationProperties({ServicesProperties.class})
public class WebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }
}

