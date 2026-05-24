import request from './request'
import type { ExchangeDealsVO } from './types'

// 获取置换交易列表
export const getExchangeDealsList = (params?: {
  role?: 'user' | 'seller'
  approved?: string
}) => request.get('/api/exchange-deals', { params })

// 发起置换申请
export const addExchangeDeals = (data: {
  targetItemId: number
  offerItemName: string
  offerItemImage: string
  offerDetail: string
}) => request.post('/api/exchange-deals', data)

// 审核置换申请
export const approveExchangeDeals = (dealId: number) =>
  request.put(`/api/exchange-deals/${dealId}/approve`)
