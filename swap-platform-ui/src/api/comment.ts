import request from './request'
import type { CommentVO, PageResult } from './types'

// 获取评论列表
export const getCommentList = (params: {
  itemId: number
  page?: number
  size?: number
}) => request.get('/api/comments', { params })

// 发布评论
export const addComment = (data: {
  itemId: number
  content: string
}) => request.post('/api/comments', data)

// 回复评论
export const replyComment = (id: number, data: { reply: string }) =>
  request.put(`/api/comments/${id}/reply`, data)
