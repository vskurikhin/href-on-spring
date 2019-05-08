package su.svn.href.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(prefix = "application")
public class YamlApplProperties
{
    private String locale;

    private Properties r2dbc;

    private Properties paging;

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public Properties getR2dbc()
    {
        return r2dbc;
    }

    public void setR2dbc(Properties r2dbc)
    {
        this.r2dbc = r2dbc;
    }

    public Properties getPaging()
    {
        return paging;
    }

    public void setPaging(Properties paging)
    {
        this.paging = paging;
    }
}
