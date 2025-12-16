package com.lggyx.web.exchangeDeals;


import com.lggyx.dto.ExchangeDealsDTO;
import com.lggyx.dto.GetExchangeDealsDTO;
import com.lggyx.result.Result;
import com.lggyx.service.IExchangeDealsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 置换交易 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@RestController
@RequestMapping("/api")
@Tag(name = "置换交易管理")
public class ExchangeDealsController {
    @Resource
    private IExchangeDealsService exchangeDealsService;
    @PostMapping("/exchange-deals")
    @Operation(summary = "发起置换申请")
    public Result< String> addExchangeDeals(@RequestBody ExchangeDealsDTO exchangeDealsDTO) {
        return exchangeDealsService.addExchangeDeals(exchangeDealsDTO);
    }
    @GetMapping("/exchange-deals")
    @Operation(summary = "获取置换交易列表")
    public Result<List<GetExchangeDealsDTO>> getExchangeDealsList(){
        return exchangeDealsService.getExchangeDealsList();
    }
    @PutMapping("/exchange-deals/{dealId}/approve")
    @Operation(summary = "置换交易审核（卖家）")
    public Result<String> approveExchangeDeals(@PathVariable Long dealId) {
        return exchangeDealsService.approveExchangeDeals(dealId);
    }

}
