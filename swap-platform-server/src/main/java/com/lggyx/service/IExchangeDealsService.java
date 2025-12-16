package com.lggyx.service;

import com.lggyx.dto.ExchangeDealsDTO;
import com.lggyx.vo.ExchangeDealsVO;
import com.lggyx.entity.ExchangeDeals;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 置换交易 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IExchangeDealsService extends IService<ExchangeDeals> {

    Result<String> addExchangeDeals(ExchangeDealsDTO exchangeDealsDTO);

    Result<List<ExchangeDealsVO>> getExchangeDealsList();

    Result<String> approveExchangeDeals(Long dealId);
}
