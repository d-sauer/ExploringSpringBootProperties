package com.davorsauer.esbp.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Davor Sauer
 */
@Component
public class ConfigSourcesConfigurer extends PropertySourcesPlaceholderConfigurer implements EnvironmentAware, InitializingBean {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        super.setEnvironment(environment);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<PropertySource> propertySources = new LinkedList<>();
        propertySources.add(getCustomLoadedPropertySource());

//        injectNewPropertySourcesOnTop(propertySources);
        injectNewPropertySourcesBeforeSystemProperties(propertySources);
    }

    private void injectNewPropertySourcesOnTop(List<PropertySource> propertySources) {
        MutablePropertySources envPropSources = ((ConfigurableEnvironment) environment).getPropertySources();

        for (int i = propertySources.size() - 1; i >= 0; i--) {
            envPropSources.addFirst(propertySources.get(i));
        }
    }

    private void injectNewPropertySourcesBeforeSystemProperties(List<PropertySource> propertySources) {
        MutablePropertySources envPropSources = ((ConfigurableEnvironment) environment).getPropertySources();

        for (int i = propertySources.size() - 1; i >= 0; i--) {
            envPropSources.addBefore("systemProperties", propertySources.get(i));
        }
    }

    private PropertySource getCustomLoadedPropertySource() {
        return new MapPropertySource("Custom loaded properties", getProperties());
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("load.a", "A");
        properties.put("load.b", "B");
        properties.put("test.keyA", "c_s_c");

        return properties;
    }
}