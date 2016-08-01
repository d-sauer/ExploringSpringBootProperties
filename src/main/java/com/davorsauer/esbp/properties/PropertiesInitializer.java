package com.davorsauer.esbp.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author Davor Sauer
 */
public class PropertiesInitializer implements ApplicationContextInitializer {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        environment.getPropertySources().forEach(propertySource -> logger.info("Property source: {}", propertySource.getName()));
    }
}
