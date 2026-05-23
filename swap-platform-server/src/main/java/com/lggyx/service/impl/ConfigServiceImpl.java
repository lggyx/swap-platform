package com.lggyx.service.impl;

import com.lggyx.entity.Config;
import com.lggyx.mapper.ConfigMapper;
import com.lggyx.result.Result;
import com.lggyx.service.IConfigService;
import com.lggyx.vo.ConfigVO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    @Override
    public Result<List<ConfigVO>> getBanners() {
        List<Config> configs = list(Wrappers.<Config>lambdaQuery()
                .in(Config::getName, "banner1", "banner2", "banner3"));
        List<ConfigVO> vos = configs.stream().map(config -> {
            ConfigVO vo = new ConfigVO();
            BeanUtils.copyProperties(config, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(vos);
    }

    @Override
    public Result<String> updateConfig(String configName, String value) {
        boolean update = update(null, Wrappers.<Config>lambdaUpdate()
                .set(Config::getValue, value)
                .eq(Config::getName, configName));
        return Result.success(update ? "修改成功" : "修改失败");
    }
}
