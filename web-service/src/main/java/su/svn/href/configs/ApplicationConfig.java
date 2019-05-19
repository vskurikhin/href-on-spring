package su.svn.href.configs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import su.svn.href.configs.ServicesProperties;

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
