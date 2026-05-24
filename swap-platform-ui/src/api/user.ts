import request from './request'
import type { LoginVO, RegisterVO, ProfileVO, User } from './types'

// 用户注册
export const register = (data: {
  username: string
  password: string
  realName: string
  gender: string
  phone: string
  email: string
  photo?: string
}) => request.post('/api/auth/register', data)

// 卖家注册
export const registerSeller = (data: {
  sellerName: string
  password: string
  realName: string
  gender: string
  phone: string
  email: string
  photo?: string
}) => request.post('/api/auth/seller/register', data)

// 登录
export const login = (data: {
  username: string
  password: string
  userType?: string
}) => request.post('/api/auth/login', data)

// 登出
export const logout = () => request.post('/api/auth/logout')

// 获取个人信息
export const getProfile = () => request.get('/api/user/profile')

// 更新个人信息
export const updateProfile = (data: {
  realName: string
  gender: string
  phone: string
  email: string
  photo?: string
}) => request.put('/api/user/profile', data)

// 修改密码
export const updatePassword = (data: {
  oldPassword: string
  newPassword: string
}) => request.put('/api/user/password', data)

// 免 token：获取所有用户
export const getAllUsers = () => request.get('/user')

// 免 token：根据用户名获取用户
export const getUserByUsername = (data: { username: string }) =>
  request.post('/user', data)

// 管理员：获取用户列表
export const adminGetUsers = () => request.get('/api/admin/users')

// 管理员：数据统计
export const adminStatistics = () => request.get('/api/admin/statistics')

// 管理员：审核物品
export const adminAuditItem = (itemId: number, data: { approved?: boolean; approvalReply?: string }) =>
  request.put(`/api/admin/items/${itemId}/audit`, data)

// 管理员：删除评论
export const adminDeleteComment = (commentId: number) =>
  request.delete(`/api/admin/comments/${commentId}`)

// 管理员：添加分类
export const adminAddCategory = (data: { category: string }) =>
  request.post('/api/categories', data)

// 管理员：更新配置
export const adminUpdateConfig = (configName: string, data: Record<string, string>) =>
  request.put(`/api/config/${configName}`, data)

// 管理员：发布公告
export const adminAddAnnouncement = (data: {
  title: string
  introduction: string
  image?: string
  content: string
}) => request.post('/api/announcements', data)
