package com.lggyx.web.items;

import com.lggyx.dto.ItemDTO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IItemInfoService;
import com.lggyx.vo.ItemInfoVO;
import com.lggyx.vo.ItemVO;
import com.lggyx.vo.ListItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "旧物信息管理")
public class ItemController {
    @Resource
    private IItemInfoService itemInfoService;
    @PostMapping("/items")
    @Operation(summary = "发布旧物信息（卖家）")
    public Result<ItemVO> addItem(@RequestBody ItemDTO itemDTO) {
        return itemInfoService.addItem(itemDTO);
    }
    @GetMapping("/items")
    @Operation(summary = "获取旧物列表")
    public Result<PageResult> getItemList(
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keywords
    ) {
        return itemInfoService.getItemList(page, size, category, keywords);
    }
    @GetMapping("/items/{id}")
    @Operation(summary = "获取旧物详情")
    public Result<ItemInfoVO> getItem(@PathVariable Long id) {
        return itemInfoService.getItemInfo(id);
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "更新旧物信息（卖家）")
    public Result<ItemVO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        return itemInfoService.updateItem(id, itemDTO);
    }
    @DeleteMapping("/items/{id}")
    @Operation(summary = "删除旧物信息（卖家）")
    public Result<String> deleteItem(@PathVariable Long id) {
        return itemInfoService.deleteItem(id);
    }
}
