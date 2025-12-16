package com.lggyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.ExchangeDealsDTO;
import com.lggyx.dto.GetExchangeDealsDTO;
import com.lggyx.entity.ExchangeDeals;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.ExchangeDealsMapper;
import com.lggyx.result.Result;
import com.lggyx.service.IExchangeDealsService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 置换交易 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class ExchangeDealsServiceImpl extends ServiceImpl<ExchangeDealsMapper, ExchangeDeals> implements IExchangeDealsService {
    @Resource
    private ExchangeDealsMapper exchangeDealsMapper;

    @Override
    public Result<String> addExchangeDeals(ExchangeDealsDTO exchangeDealsDTO) {
        ExchangeDeals exchangeDeals = new ExchangeDeals();
        BeanUtils.copyProperties(exchangeDealsDTO, exchangeDeals);
        return exchangeDealsMapper.insert(exchangeDeals) > 0 ?
                Result.success(SuccessCode.SUCCESS) :
                Result.error(ErrorCode.SYSTEM_ERROR);
    }

    @Override
    public Result<List<GetExchangeDealsDTO>> getExchangeDealsList() {
        List<ExchangeDeals> list = exchangeDealsMapper.selectList(null);
        return Result.success(SuccessCode.SUCCESS, list.stream()
                .map(exchangeDeals -> {
                    GetExchangeDealsDTO getExchangeDealsDTO = new GetExchangeDealsDTO();
                    BeanUtils.copyProperties(exchangeDeals, getExchangeDealsDTO);
                    return getExchangeDealsDTO;
                }).toList());
    }

    @Override
    public Result<String> approveExchangeDeals(Long dealId) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.SELLER_ROLE_CODE) {
            return Result.error(ErrorCode.NO_ACCESS_ADMIN_API);
        }
        int update = exchangeDealsMapper.updateById(new ExchangeDeals().setId(dealId).setApproved("是").setApprovalReply("同意置换，请尽快联系对方完成交易。"));
        return update > 0 ?
                Result.success(SuccessCode.SUCCESS) :
                Result.error(ErrorCode.SYSTEM_ERROR);
    }
}
