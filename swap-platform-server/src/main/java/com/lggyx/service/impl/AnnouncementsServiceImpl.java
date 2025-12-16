package com.lggyx.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.context.BaseContext;
import com.lggyx.dto.AnnouncementsDTO;
import com.lggyx.entity.Announcements;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.AnnouncementsMapper;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAnnouncementsService;
import com.lggyx.vo.AnnouncementsVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 平台公告 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Service
public class AnnouncementsServiceImpl extends ServiceImpl<AnnouncementsMapper, Announcements> implements IAnnouncementsService {
    @Resource
    private AnnouncementsMapper announcementsMapper;

    @Override
    public Result<PageResult> getAnnouncementsList(Integer page, Integer size) {
        Page<Announcements> pageInfo = new Page<>(page, size);
        Page<Announcements> announcementsList = announcementsMapper.selectPage(pageInfo, null);
        List<AnnouncementsVO> announcementsVOList = announcementsList.getRecords().stream().map(announcements -> {
            AnnouncementsVO announcementsVO = new AnnouncementsVO();
            BeanUtils.copyProperties(announcements, announcementsVO);
            return announcementsVO;
        }).toList();
        PageResult pageResult = new PageResult(
                announcementsVOList.size(),
                announcementsList.getCurrent(),
                announcementsList.getSize(),
                announcementsVOList
        );
        return Result.success(
                SuccessCode.SUCCESS,
                pageResult
        );

    }

    @Override
    public Result<AnnouncementsVO> getAnnouncementsDetail(Long id) {
        Announcements announcements = announcementsMapper.selectById(id);
        AnnouncementsVO announcementsVO = new AnnouncementsVO();
        BeanUtils.copyProperties(announcements, announcementsVO);
        return Result.success(
                SuccessCode.SUCCESS,
                announcementsVO
        );
    }

    @Override
    public Result<String> addAnnouncements(AnnouncementsDTO announcementsDTO) {
        RoleCode roleCode = RoleCode.fromAccount(BaseContext.getCurrentAccount());
        if (roleCode != RoleCode.ADMIN_ROLE_CODE) {
            return Result.error(ErrorCode.NO_ACCESS_ADMIN_API);
        }
        Announcements announcements = new Announcements();
        BeanUtils.copyProperties(announcementsDTO, announcements);
        return announcementsMapper.insert(announcements) > 0 ?
                Result.success(SuccessCode.SUCCESS) :
                Result.error(ErrorCode.SYSTEM_ERROR);
    }
}
