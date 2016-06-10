package com.davorsauer.esbp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import javax.annotation.Resource;

/**
 * @author Davor Sauer
 */
@SpringBootApplication
@Resource(name = Application.JNDI_PROPERTY_NAME, lookup = Application.JNDI_PROPERTY_NAME, type = String.class)  // Replacement web.xml <resource-ref>
public class Application extends SpringBootServletInitializer {

    public static final String PROPERTY_PREFIX = "esbp";

    public static final String JNDI_PROPERTY_NAME = "esbp/config";

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
