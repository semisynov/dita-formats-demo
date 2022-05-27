package ru.vtb.dita.formats.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String uploadDir;
    private DemoProperties demo;

    @Data
    public static class DemoProperties {
        private String input;
        private String ditaFile;
        private String markdownFile;
    }
}
