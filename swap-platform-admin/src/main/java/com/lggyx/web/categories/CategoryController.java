package com.lggyx.web.categories;

import com.lggyx.dto.CategoryDTO;
import com.lggyx.result.Result;
import com.lggyx.service.IItemCategoriesService;
import com.lggyx.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "物品分类管理")
public class CategoryController {
    @Resource
    private IItemCategoriesService itemCategoriesService;
    @GetMapping("/categories")
    @Operation(summary = "获取所有旧物类型")
    public Result<List<CategoryVO>> getAllCategories() {
        return itemCategoriesService.getAllCategories();
    }
    @PostMapping("/categories")
    @Operation(summary = "添加旧物类型（管理员）")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return itemCategoriesService.addCategory(categoryDTO);
    }
}
