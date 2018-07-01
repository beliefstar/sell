package com.zhenxin.sell.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigProperties {

    @Value("${img.server}")
    private String imgServer;

    @Value("${app.server}")
    private String appServer;
}
