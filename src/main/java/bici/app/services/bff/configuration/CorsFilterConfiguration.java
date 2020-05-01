package bici.app.services.bff.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class CorsFilterConfiguration {

    /**
     * Priority order for the cors filter
     */
    private static final int CORS_FILTER_ORDER = 0;

    /**
     * Resources affected by the cors filter
     */
    private static final String RESOURCES_FILTERED = "/**";

    /**
     * Front end origin definition
     */
    @Value("${api.origins.front.server}")
    private String frontServer;

    /**
     * Configures and registers the cors filter
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(frontServer.split(","))
                .map(String::trim).collect(Collectors.toList()));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.ACCEPT, HttpHeaders.ACCEPT_ENCODING,
                HttpHeaders.ACCEPT_LANGUAGE,
                HttpHeaders.CACHE_CONTROL, HttpHeaders.CONNECTION, HttpHeaders.CONTENT_LENGTH,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.HOST, HttpHeaders.ORIGIN, HttpHeaders.PRAGMA, HttpHeaders.REFERER,
                HttpHeaders.USER_AGENT, HttpHeaders.AUTHORIZATION));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(RESOURCES_FILTERED, configuration);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }
}
