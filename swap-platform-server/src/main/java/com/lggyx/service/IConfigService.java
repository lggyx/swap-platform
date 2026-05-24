package com.lggyx.service;

import com.lggyx.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;
import com.lggyx.vo.ConfigVO;

import java.util.List;

public interface IConfigService extends IService<Config> {

    Result<List<ConfigVO>> getBanners();

    Result<String> updateConfig(String configName, String value);
}
