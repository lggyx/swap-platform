import request from './request'
import type { AnnouncementsVO, PageResult, BannerConfig } from './types'

// 获取公告列表
export const getAnnouncementsList = (params?: {
  page?: number
  size?: number
}) => request.get('/api/announcements', { params })

// 获取公告详情
export const getAnnouncementDetail = (id: number) =>
  request.get(`/api/announcements/${id}`)

// 获取Banner配置
export const getBanners = () => request.get('/api/config/banners')
