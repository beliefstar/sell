package com.zhenxin.sell.controller;

import com.zhenxin.sell.service.ProductKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KillController {

    @Autowired
    private ProductKillService productKillService;

    @GetMapping("/stock")
    public String getStock(String id) {
        return productKillService.getStock(id);
    }

    @GetMapping("/kill")
    public String kill(String id) {
        return productKillService.killProduct(id);
    }
}
