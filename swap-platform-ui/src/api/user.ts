import request from './request'
import type { LoginVO, RegisterVO, ProfileVO } from './types'

// 用户注册
export const register = (data: {
  username: string
  password: string
  realName: string
  gender: string
  phone: string
  email: string
  photo?: string
}) => request.post('/register', data)

// 卖家注册
export const registerSeller = (data: {
  sellerName: string
  password: string
  realName: string
  gender: string
  phone: string
  email: string
  photo?: string
}) => request.post('/seller/register', data)

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
