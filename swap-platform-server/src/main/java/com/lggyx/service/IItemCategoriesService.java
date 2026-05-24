package com.lggyx.service;

import com.lggyx.dto.CategoryDTO;
import com.lggyx.entity.ItemCategories;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;
import com.lggyx.vo.CategoryVO;

import java.util.List;

/**
 * <p>
 * 旧物类型 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IItemCategoriesService extends IService<ItemCategories> {

    Result<List<CategoryVO>> getAllCategories();

    Result<String> addCategory(CategoryDTO categoryDTO);
}
