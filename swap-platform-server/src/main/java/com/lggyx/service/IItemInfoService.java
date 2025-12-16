package com.lggyx.service;

import com.lggyx.dto.ItemDTO;
import com.lggyx.entity.ItemInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.vo.ItemInfoVO;
import com.lggyx.vo.ItemVO;
import com.lggyx.vo.ListItemVO;

import java.util.List;

/**
 * <p>
 * 旧物信息 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IItemInfoService extends IService<ItemInfo> {

    Result<ItemVO> addItem(ItemDTO itemDTO);

    Result<PageResult> getItemList(Long page, Long size,String category, String keyword);

    Result<ItemInfoVO> getItemInfo(Long id);

    Result<ItemVO> updateItem(Long id, ItemDTO itemDTO);

    Result<String> deleteItem(Long id);
}
