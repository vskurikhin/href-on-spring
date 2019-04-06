package su.svn.href.configs;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.function.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import su.svn.href.dao.RegionDao;

import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfiguration extends AbstractR2dbcConfiguration
{
    private YamlApplProperties yap;

    public R2dbcConfiguration(YamlApplProperties yamlApplProperties)
    {
        this.yap = yamlApplProperties;
    }

    @Bean
    public RegionDao regionDao(R2dbcRepositoryFactory factory)
    {
        return factory.getRepository(RegionDao.class);
    }

    @Bean
    public ReactiveDataAccessStrategy reactiveDataAccessStrategy()
    {
        return new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
    }

    @Bean
    public R2dbcRepositoryFactory factory(DatabaseClient client, ReactiveDataAccessStrategy strategy)
    {
        RelationalMappingContext context = new RelationalMappingContext();
        context.afterPropertiesSet();
        return new R2dbcRepositoryFactory(client, context, strategy);
    }

    @Override
    public ConnectionFactory connectionFactory()
    {
        Properties r2dbc = yap.getR2dbc();
        String host  = null;
        String database  = null;
        String username  = null;
        String password = null;

        if ( ! Objects.isNull(r2dbc)) {
            host = r2dbc.getProperty("host");
            database = r2dbc.getProperty("database");
            username = r2dbc.getProperty("username");
            password = r2dbc.getProperty("password");
        }

        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(null != host ? host : "localhost")
                .database(null != database ? database : "db")
                .username(null != username ? username : "dbuser")
                .password(null != password ? password : "password")
                .build()
        );
    }
}
