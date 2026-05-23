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
    // 后端返回 code 非 200 视为业务错误
    if (res.code !== 200) {
      ElMessage.error(res.msg || '请求失败')
      return Promise.reject(new Error(res.msg || '请求失败'))
    }
    return res.data
  },
  (error) => {
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request
