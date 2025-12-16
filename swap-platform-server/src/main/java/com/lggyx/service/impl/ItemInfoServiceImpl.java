package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.ItemDTO;
import com.lggyx.entity.ItemInfo;
import com.lggyx.entity.Seller;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.ItemInfoMapper;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IItemInfoService;
import com.lggyx.service.ISellerService;
import com.lggyx.vo.ItemInfoVO;
import com.lggyx.vo.ItemVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 旧物信息 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class ItemInfoServiceImpl extends ServiceImpl<ItemInfoMapper, ItemInfo> implements IItemInfoService {
    @Resource
    private ItemInfoMapper itemInfoMapper;
    @Resource
    private ISellerService sellerService;

    @Override
    public Result<ItemVO> addItem(ItemDTO itemDTO) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.SELLER_ROLE_CODE) {
            return Result.error("权限不足");
        }
        ItemInfo itemInfo = new ItemInfo();
        BeanUtils.copyProperties(itemDTO, itemInfo);
        String account = BaseContext.getCurrentAccount().substring(7);
        Seller seller = sellerService.getOne(Wrappers.<Seller>lambdaQuery().eq(Seller::getSellerName, account));
        itemInfo.setSellerName(seller.getSellerName());
        itemInfo.setContactName(seller.getRealName());
        itemInfo.setContactPhone(seller.getPhone());
        int result = itemInfoMapper.insert(itemInfo);
        ItemVO itemVO = new ItemVO();
        itemVO.setItemId(itemInfo.getId());
        return result > 0 ? Result.success(SuccessCode.ADD_SUCCESS, itemVO) : Result.error("添加失败");
    }

    @Override
    public Result<PageResult> getItemList(Long page, Long size, String category, String keyword) {
        Page<ItemInfo> itemInfoPage = new Page<>(page, size);
        Page<ItemInfo> itemInfoPageResult = itemInfoMapper.selectPage(itemInfoPage, Wrappers.<ItemInfo>lambdaQuery()
                .like(StringUtils.isNotBlank(category), ItemInfo::getItemCategory, category)
                .like(StringUtils.isNotBlank(keyword), ItemInfo::getItemName, keyword));
        List<ItemInfoVO> itemInfoList = itemInfoPageResult.getRecords().stream().map(itemInfo -> {
            ItemInfoVO itemInfoVO = new ItemInfoVO();
            BeanUtils.copyProperties(itemInfo, itemInfoVO);
            return itemInfoVO;
        }).toList();
        PageResult pageResult = new PageResult(
                itemInfoList.size(),
                itemInfoPageResult.getCurrent(),
                itemInfoPageResult.getSize(),
                itemInfoList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result<ItemInfoVO> getItemInfo(Long id) {
        ItemInfo itemInfo = itemInfoMapper.selectById(id);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        BeanUtils.copyProperties(itemInfo, itemInfoVO);
        return Result.success(itemInfoVO);
    }

    @Override
    public Result<ItemVO> updateItem(Long id, ItemDTO itemDTO) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.SELLER_ROLE_CODE) {
            return Result.error("权限不足");
        }
        ItemInfo itemInfo = itemInfoMapper.selectById(id);
        if (itemInfo == null) {
            return Result.error("无效的ID");
        }
        itemInfo.setItemName(itemDTO.getItemName());
        itemInfo.setItemCategory(itemDTO.getItemCategory());
        itemInfo.setItemImage(itemDTO.getItemImage());
        itemInfo.setExchangeRequest(itemDTO.getExchangeRequest());
        itemInfo.setItemDetail(itemDTO.getItemDetail());
        int result = itemInfoMapper.updateById(itemInfo);
        ItemVO itemVO = new ItemVO();
        itemVO.setItemId(itemInfo.getId());
        return result > 0 ? Result.success(SuccessCode.SUCCESS, itemVO) : Result.error("更新失败");
    }

    @Override
    public Result<String> deleteItem(Long id) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.SELLER_ROLE_CODE) {
            return Result.error("权限不足");
        }
        return itemInfoMapper.deleteById(id) > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("删除失败");
    }
}
