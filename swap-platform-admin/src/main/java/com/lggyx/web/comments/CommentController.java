package com.lggyx.web.comments;

import com.lggyx.dto.CommentDTO;
import com.lggyx.dto.ReplyDTO;
import com.lggyx.result.Result;
import com.lggyx.service.IItemCommentsService;
import com.lggyx.vo.CommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "评论管理")
public class CommentController {
    @Resource
    private IItemCommentsService itemCommentsService;

    @PostMapping("/comments")
    @Operation(summary = "发布评论")
    public Result<String> addComment(@RequestBody CommentDTO commentDTO) {
        return itemCommentsService.addComment(commentDTO);
    }

    @GetMapping("/comments")
    @Operation(summary = "获取评论列表")
    public Result<List<CommentVO>> getCommentList(
            @RequestParam Long itemId,
            @RequestParam(required = false, defaultValue = "1") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ) {
        return itemCommentsService.getCommentList(itemId, page, size);
    }
    @PutMapping("/comments/{id}/reply")
    @Operation(summary = "回复评论（卖家）")
    public Result<String> replyComment(@PathVariable Long id, @RequestBody ReplyDTO replyDTO) {
        return itemCommentsService.replyComment(id, replyDTO);
    }
}
