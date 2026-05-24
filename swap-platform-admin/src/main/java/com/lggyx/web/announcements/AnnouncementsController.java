package com.lggyx.web.announcements;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lggyx.dto.AnnouncementsDTO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAnnouncementsService;
import com.lggyx.vo.AnnouncementsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 平台公告 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@RestController
@RequestMapping("/api")
@Tag(name = "公告管理")
public class AnnouncementsController {
    @Resource
    private IAnnouncementsService announcementsService;

    @GetMapping("/announcements")
    @Operation(summary = "获取公告列表")
    public Result<PageResult> getAnnouncementsList(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return announcementsService.getAnnouncementsList(page, size);
    }
    @GetMapping("/announcements/{id}")
    @Operation(summary = "获取公告详情")
    public Result<AnnouncementsVO> getAnnouncementsDetail(@PathVariable Long id) {
        return announcementsService.getAnnouncementsDetail(id);
    }
    @PostMapping("/announcements")
    @Operation(summary = "添加公告（管理员）")
    public Result<String> addAnnouncements(@RequestBody AnnouncementsDTO announcementsDTO) {
        return announcementsService.addAnnouncements(announcementsDTO);
    }

}
