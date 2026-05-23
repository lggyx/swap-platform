package com.lggyx.service;

import com.lggyx.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.vo.ConfigVO;

import java.util.List;

public interface IConfigService extends IService<Config> {

    List<ConfigVO> getBanners();

    String updateConfig(String configName, String value);
}
