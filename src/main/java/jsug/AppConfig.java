package jsug;

import jsug.domain.model.Cart;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Bean
    DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
                .create(this.dataSourceProperties.getClassLoader())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword())
                .build();
        return new DataSourceSpy(dataSource);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    Cart cart() {
        return new Cart();
    }
}