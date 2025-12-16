package com.lggyx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lggyx.dto.AnnouncementsDTO;
import com.lggyx.entity.Announcements;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.vo.AnnouncementsVO;

/**
 * <p>
 * 平台公告 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
public interface IAnnouncementsService extends IService<Announcements> {

    Result<PageResult> getAnnouncementsList(Integer page, Integer size);

    Result<AnnouncementsVO> getAnnouncementsDetail(Long id);

    Result<String> addAnnouncements(AnnouncementsDTO announcementsDTO);
}
