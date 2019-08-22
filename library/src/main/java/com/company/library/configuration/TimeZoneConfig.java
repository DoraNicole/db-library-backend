package com.company.library.configuration;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

}
