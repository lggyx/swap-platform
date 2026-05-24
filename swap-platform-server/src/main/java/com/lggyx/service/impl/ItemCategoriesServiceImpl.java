package com.lggyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.CategoryDTO;
import com.lggyx.entity.ItemCategories;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.ItemCategoriesMapper;
import com.lggyx.result.Result;
import com.lggyx.service.IItemCategoriesService;
import com.lggyx.vo.CategoryVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 旧物类型 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class ItemCategoriesServiceImpl extends ServiceImpl<ItemCategoriesMapper, ItemCategories> implements IItemCategoriesService {
    @Resource
    private ItemCategoriesMapper itemCategoriesMapper;

    @Override
    public Result<List<CategoryVO>> getAllCategories() {
        List<ItemCategories> itemCategorieslist = itemCategoriesMapper.selectList(null);
        List<CategoryVO> categoryVOList =
                itemCategorieslist.stream().map(
                        itemCategories -> {
                            CategoryVO categoryVO = new CategoryVO();
                            categoryVO.setId(itemCategories.getId());
                            categoryVO.setCategoryName(itemCategories.getCategoryName());
                            categoryVO.setAddTime(itemCategories.getAddTime());
                            return categoryVO;
                        }
                ).toList();
        return Result.success(categoryVOList);
    }

    @Override
    public Result<String> addCategory(CategoryDTO categoryDTO) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        ItemCategories itemCategories = new ItemCategories();
        itemCategories.setCategoryName(categoryDTO.getCategory());
        if (roleCode == RoleCode.ADMIN_ROLE_CODE) {
            return itemCategoriesMapper.insert(itemCategories) > 0 ?
                    Result.success(SuccessCode.SUCCESS,"添加成功") :
                    Result.error("添加失败");
        }
        return Result.error(ErrorCode.NO_ACCESS_ADMIN_API);
    }
}
