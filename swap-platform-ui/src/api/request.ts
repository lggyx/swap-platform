import axios from 'axios'
import { ElMessage } from 'element-plus'

// 这里改成你后端的实际地址
const BASE_URL = 'http://localhost:8912'

const request = axios.create({
  baseURL: BASE_URL,
  timeout: 15000,
})

// 请求拦截器：自动带 token
request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器：统一处理
request.interceptors.response.use(
  (response) => {
    const res = response.data
    // 后端约定 code=1 表示成功，兼容字符串 "1"
    if (res.code != null && Number(res.code) !== 1) {
      ElMessage.error(res.msg || res.message || '请求失败')
      return Promise.reject(new Error(res.msg || res.message || '请求失败'))
    }
    return res.data
  },
  (error) => {
    const msg = error.response?.data?.msg || error.response?.data?.message || error.message || '网络错误'
    ElMessage.error(msg)
    return Promise.reject(new Error(msg))
  }
)

export default request
