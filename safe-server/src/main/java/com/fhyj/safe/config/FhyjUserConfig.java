package com.fhyj.safe.config;

import com.fhyj.safe.config.bean.FhyjUserProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/15 9:38
 */
@Configuration
public class FhyjUserConfig {

    @Bean
    @ConfigurationProperties(prefix = "fhyj.user")
    public FhyjUserProperties fhyjUserProperties() {
        return new FhyjUserProperties();
    }
}
