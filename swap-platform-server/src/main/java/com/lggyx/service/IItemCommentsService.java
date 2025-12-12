package com.lggyx.service;

import com.lggyx.dto.CommentDTO;
import com.lggyx.dto.ReplyDTO;
import com.lggyx.entity.ItemComments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.Result;
import com.lggyx.vo.CommentVO;

import java.util.List;

/**
 * <p>
 * 旧物评论 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IItemCommentsService extends IService<ItemComments> {

    Result<String> addComment(CommentDTO commentDTO);

    Result<List<CommentVO>> getCommentList(Long itemId, Long page, Long size);

    Result<String> replyComment(Long id, ReplyDTO replyDTO);
}
