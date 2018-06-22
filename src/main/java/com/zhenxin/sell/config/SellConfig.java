package com.zhenxin.sell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SellConfig {

    @Value("${img.server}")
    private String imgServer;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer fmc = new FreeMarkerConfigurer();
        Map<String, Object> map = new HashMap<>();
        map.put("imgServer", imgServer);

        fmc.setFreemarkerVariables(map);
        fmc.setTemplateLoaderPath("classpath:/templates");
        return fmc;
    }
}
