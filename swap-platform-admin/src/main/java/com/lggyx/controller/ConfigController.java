package com.lggyx.controller;


import com.lggyx.result.Result;
import com.lggyx.service.IConfigService;
import com.lggyx.vo.ConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "系统配置管理")
public class ConfigController {
    @Resource
    private IConfigService configService;

    @GetMapping("/config/banners")
    @Operation(summary = "获取Banner配置")
    public Result<List<ConfigVO>> getBanners() {
        return configService.getBanners();
    }

    @PutMapping("/config/{configName}")
    @Operation(summary = "更新配置（管理员）")
    public Result<String> updateConfig(@PathVariable String configName, @RequestBody Map<String, String> body) {
        return configService.updateConfig(configName, body.get("value"));
    }
}
