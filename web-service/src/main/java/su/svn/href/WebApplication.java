
package su.svn.href;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import su.svn.href.configs.ServicesProperties;
import su.svn.href.configs.YamlApplProperties;

// https://www.naturalprogrammer.com/courses/332639/lectures/5902512
@SpringBootApplication
@EnableConfigurationProperties({ServicesProperties.class, YamlApplProperties.class})
public class WebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }
}
