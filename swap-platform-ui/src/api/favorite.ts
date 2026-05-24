import request from './request'
import type { FavoriteVO, PageResult } from './types'

// 获取收藏列表
export const getFavoriteList = (params?: {
  page?: number
  size?: number
}) => request.get('/api/favorites', { params })

// 添加收藏
export const addFavorite = (data: {
  itemId: number
  itemType: string
  name: string
  image: string
}) => request.post('/api/favorites', data)

// 取消收藏
export const cancelFavorite = (id: number) =>
  request.delete(`/api/favorites/${id}`)
