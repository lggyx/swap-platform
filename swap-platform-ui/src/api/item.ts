import request from './request'
import type { ItemInfoVO, ItemVO, CategoryVO, PageResult } from './types'

// 获取分类列表
export const getCategories = () => request.get('/api/categories')

// 添加分类（管理员）
export const addCategory = (data: { category: string }) =>
  request.post('/api/categories', data)

// 获取旧物列表
export const getItemList = (params?: {
  page?: number
  size?: number
  category?: string
  keywords?: string
}) => request.get('/api/items', { params })

// 发布旧物
export const addItem = (data: {
  itemName: string
  itemCategory: string
  itemImage: string
  exchangeRequest: string
  itemDetail: string
  contactName: string
  contactPhone: string
}) => request.post('/api/items', data)

// 获取旧物详情
export const getItemDetail = (id: number) => request.get(`/api/items/${id}`)

// 更新旧物
export const updateItem = (id: number, data: Parameters<typeof addItem>[0]) =>
  request.put(`/api/items/${id}`, data)

// 删除旧物
export const deleteItem = (id: number) => request.delete(`/api/items/${id}`)

// 点赞/点踩
export const reactItem = (id: number, type: 'like' | 'dislike') =>
  request.post(`/api/items/${id}/reaction`, { type })
