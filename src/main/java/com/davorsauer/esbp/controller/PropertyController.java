package com.davorsauer.esbp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.NamingException;

/**
 * @author Davor Sauer
 */
@Controller
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private Environment environment;



    @RequestMapping(value = {"", "/"})
    @ResponseBody
    public String index() throws NamingException {
        return "Use '/property?propertyKey' to get property value";
    }


    @RequestMapping(value = "property", params = "key")
    @ResponseBody
    public String getPropertyValue(@RequestParam String key) throws NamingException {
        logger.info("Request property for key: {}", key);
        String property = environment.getProperty(key);
        logger.info("Property: {}={}", key, property);

        return property;
    }


    @RequestMapping(value = "jndi", params = "key")
    @ResponseBody
    public String getJndiPropertyValue(@RequestParam String key) throws NamingException {
        logger.info("Request property for key: {}", key);

        JndiObjectFactoryBean jndiFactoryBean = new JndiObjectFactoryBean();
        jndiFactoryBean.setJndiName(key);
        jndiFactoryBean.setExpectedType(String.class);
        jndiFactoryBean.setLookupOnStartup(true);

        try {
            jndiFactoryBean.afterPropertiesSet();
        } catch (NamingException e) {
            jndiFactoryBean.setJndiName("java:comp/env/" + key);
            jndiFactoryBean.afterPropertiesSet();
        }

        String property = (String) jndiFactoryBean.getObject();
        logger.info("Property: {}={}", key, property);

        return property;
    }

}
