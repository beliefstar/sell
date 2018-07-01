package com.zhenxin.sell.config;

import com.zhenxin.sell.intercept.AuthIntercept;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
public class SellConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private AuthIntercept authIntercept;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer fmc = new FreeMarkerConfigurer();
        Map<String, Object> map = new HashMap<>();
        map.put("imgServer", configProperties.getImgServer());
        map.put("appServer", configProperties.getAppServer());

        fmc.setFreemarkerVariables(map);
        fmc.setTemplateLoaderPath("classpath:/templates");
        return fmc;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercept)
                .addPathPatterns("/**")
                .excludePathPatterns("/seller/info/**", "/error");
    }
}
