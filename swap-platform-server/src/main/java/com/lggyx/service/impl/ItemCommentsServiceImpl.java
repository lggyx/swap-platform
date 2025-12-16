package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.CommentDTO;
import com.lggyx.dto.ReplyDTO;
import com.lggyx.entity.AdminUser;
import com.lggyx.entity.ItemComments;
import com.lggyx.entity.Seller;
import com.lggyx.entity.User;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.AdminUserMapper;
import com.lggyx.mapper.ItemCommentsMapper;
import com.lggyx.mapper.SellerMapper;
import com.lggyx.mapper.UserMapper;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IItemCommentsService;
import com.lggyx.vo.CommentVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 旧物评论 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class ItemCommentsServiceImpl extends ServiceImpl<ItemCommentsMapper, ItemComments> implements IItemCommentsService {
    @Resource
    private ItemCommentsMapper itemCommentsMapper;
    @Resource
    private SellerMapper sellerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Result<String> addComment(CommentDTO commentDTO) {
        ItemComments itemComments = new ItemComments();
        BeanUtils.copyProperties(commentDTO, itemComments);
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        switch (roleCode) {
            case USER_ROLE_CODE:
                Long userId = userMapper.selectOne(
                        Wrappers.<User>lambdaQuery()
                                .eq(User::getUsername, BaseContext.getCurrentAccount().substring(7))
                ).getId();
                itemComments.setUserId(userId);
                break;
            case SELLER_ROLE_CODE:
                Long sellerId = sellerMapper.selectOne(
                        Wrappers.<Seller>lambdaQuery()
                                .eq(Seller::getSellerName, BaseContext.getCurrentAccount().substring(7))
                ).getId();
                itemComments.setUserId(sellerId);
                break;
            case ADMIN_ROLE_CODE:
                Long adminId = adminUserMapper.selectOne(
                        Wrappers.<AdminUser>lambdaQuery()
                                .eq(AdminUser::getUsername, BaseContext.getCurrentAccount().substring(7))
                ).getId();
                itemComments.setUserId(adminId);
                break;
            default:
                return Result.error("权限不足");
        }
        return itemCommentsMapper.insert(itemComments) > 0 ?
                Result.success(SuccessCode.SUCCESS, "评论成功") :
                Result.error("评论失败");
    }

    @Override
    public Result<PageResult> getCommentList(Long itemId, Long page, Long size) {
        //todo 分页
        Page<ItemComments> itemCommentsPage = new Page<>(page, size);
        List<ItemComments> itemCommentsList = itemCommentsMapper.selectList(itemCommentsPage,
                Wrappers.<ItemComments>lambdaQuery()
                        .eq(ItemComments::getItemId, itemId)
                        .orderByDesc(ItemComments::getAddTime)
        );
        List<CommentVO> commentVOList = itemCommentsList.stream().map(itemComments -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(itemComments, commentVO);
            return commentVO;
        }).toList();
        PageResult pageResult = new PageResult(
                itemCommentsList.size(),
                itemCommentsPage.getCurrent(),
                itemCommentsPage.getSize(),
                commentVOList);
        return Result.success(pageResult);
    }

    @Override
    public Result<String> replyComment(Long id, ReplyDTO replyDTO) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.SELLER_ROLE_CODE) {
            return Result.error("权限不足");
        }
        ItemComments itemComments = itemCommentsMapper.selectById(id);
        if (itemComments == null) {
            return Result.error("无效的ID");
        }
        itemComments.setReply(replyDTO.getReply());
        return itemCommentsMapper.updateById(itemComments) > 0 ?
                Result.success(SuccessCode.SUCCESS, "回复成功") :
                Result.error("回复失败");
    }
}
