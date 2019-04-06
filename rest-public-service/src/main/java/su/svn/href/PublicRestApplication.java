package su.svn.href;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import su.svn.href.configs.YamlApplProperties;

@SpringBootApplication
@EnableConfigurationProperties(YamlApplProperties.class)
public class PublicRestApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PublicRestApplication.class, args);
    }
}
