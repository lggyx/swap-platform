export interface User {
  id: number
  username: string
  realName: string
  gender: string
  phone: string
  email: string
  photo: string
  addTime?: string
}

export interface LoginVO {
  userId: number
  username: string
  role: string
  token: string
  expireTime: string
}

export interface RegisterVO {
  userId: number
  username: string
  token: string
}

export interface ProfileVO {
  id: number
  username: string
  realName: string
  gender: string
  phone: string
  email: string
  photo: string
  addTime: string
}

export interface ItemVO {
  id: number
  itemName: string
  itemCategory: string
  itemImage: string
  exchangeRequest: string
  itemDetail: string
  likeCount: number
  dislikeCount: number
  addTime: string
  sellerName: string
  contactName: string
  contactPhone: string
}

export interface ItemInfoVO {
  id: number
  itemName: string
  itemCategory: string
  itemImage: string
  exchangeRequest: string
  itemDetail: string
  likeCount: number
  dislikeCount: number
  addTime: string
  sellerName: string
  contactName: string
  contactPhone: string
}

export interface CategoryVO {
  id: number
  categoryName: string
  addTime: string
}

export interface CommentVO {
  id: number
  itemId: number
  userId: number
  username: string
  content: string
  reply: string | null
  addTime: string
}

export interface FavoriteVO {
  id: number
  itemId: number
  itemType: string
  name: string
  image: string
  addTime: string
}

export interface ExchangeDealsVO {
  id: number
  username: string
  realName: string
  phone: string
  targetItemName: string
  targetItemImage: string
  offerItemName: string
  offerItemImage: string
  offerDetail: string
  approved: string
  approvalReply: string
  addTime: string
}

export interface AnnouncementsVO {
  id: number
  title: string
  introduction: string
  image: string
  content: string
  addTime: string
}

export interface PageResult<T = any> {
  total: number
  page: number
  size: number
  records: T[]
}

export interface BannerConfig {
  name: string
  value: string
}

export interface StatisticsVO {
  totalUsers: number
  totalSellers: number
  totalItems: number
  totalDeals: number
}

export interface ConfigVO {
  name: string
  value: string
}
