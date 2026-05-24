/**
 * Swap Platform 前后端接口批量测试脚本
 * 用法: node scripts/api-test.js [baseUrl] [--once]
 *   baseUrl  默认 http://localhost:8912
 *   --once   只跑一轮（不监听变更）
 */

const BASE_URL = process.argv[2] || 'http://localhost:8912'
const ONCE = process.argv.includes('--once')

// 直接用项目里的 axios（node_modules/axios 原生可用，但 node 没有 fetch，
// 所以我们直接 import axios）
import axios from 'axios'
import { writeFileSync } from 'fs'

axios.defaults.baseURL = BASE_URL
axios.defaults.timeout = 15000
axios.defaults.headers.common['Content-Type'] = 'application/json'

// ==================== 工具函数 ====================
const sleep = (ms) => new Promise(r => setTimeout(r, ms))

function check(res, label) {
  const code = res?.data?.code
  return {
    label,
    ok: code != null && (Number(code) === 1 || Number(code) === 200),
    code: code,
    msg: res?.data?.msg || res?.data?.message || '',
    data: res?.data?.data,
    status: res?.status,
    raw: JSON.stringify(res?.data).slice(0, 200),
  }
}

// ==================== 测试用例定义 ====================
let testCases = []

function CASE(name, fn) {
  testCases.push({ name, fn })
}

// ---------- 认证接口 ----------
CASE('用户注册（重复应失败）', async () => {
  const res = await axios.post('/register', {
    username: 'testuser_' + Date.now(),
    password: '123456',
    realName: '测试用户',
    gender: '男',
    phone: '74955953457',
    email: 'test@example.com',
  })
  return check(res, '用户注册')
})

CASE('卖家注册（重复应失败）', async () => {
  const res = await axios.post('/seller/register', {
    sellerName: 'testseller_' + Date.now(),
    password: '123456',
    realName: '测试卖家',
    gender: '女',
    phone: '74955953457',
    email: 'seller@example.com',
  })
  return check(res, '卖家注册')
})

// 保存登录 token 供后续使用
let globalToken = ''
let userId = null
let adminToken = ''
let sellerToken = ''    // 卖家token
let validItemId = null      // 动态获取的有效旧物ID
let validCommentId = null   // 动态获取的有效评论ID
let validAnnouncementId = null // 动态获取的有效公告ID

// 预取有效ID的辅助函数
async function fetchValidIds() {
  // 获取第一条旧物的ID
  try {
    const itemRes = await axios.get('/api/items', { params: { page: 1, size: 1 } })
    if (itemRes.data?.code === 1 && itemRes.data?.data?.records?.[0]?.id) {
      validItemId = itemRes.data.data.records[0].id
    } else if (itemRes.data?.data?.[0]?.id) {
      validItemId = itemRes.data.data[0].id
    }
  } catch (e) {
    console.warn('  预取旧物ID失败:', e.message)
  }

  // 获取第一条公告的ID
  try {
    const annRes = await axios.get('/api/announcements', { params: { page: 1, size: 1 } })
    if (annRes.data?.code === 1 && annRes.data?.data?.records?.[0]?.id) {
      validAnnouncementId = annRes.data.data.records[0].id
    } else if (annRes.data?.data?.[0]?.id) {
      validAnnouncementId = annRes.data.data[0].id
    }
  } catch (e) {
    console.warn('  预取公告ID失败:', e.message)
  }
}

CASE('普通用户登录（user01/654321）', async () => {
  const res = await axios.post('/api/auth/login', {
    username: 'user01',
    password: '654321',
    userType: 'user',
  })
  const c = check(res, '普通用户登录')
  if (c.ok && res.data.data?.token) {
    globalToken = res.data.data.token
    userId = res.data.data.userId
  }
  return c
})

CASE('卖家登录（seller01/123456）', async () => {
  const res = await axios.post('/api/auth/login', {
    username: 'seller01',
    password: '123456',
    userType: 'seller',
  })
  const c = check(res, '卖家登录')
  if (c.ok && res.data.data?.token) {
    sellerToken = res.data.data.token
  }
  return c
})

CASE('管理员登录（admin/admin123）', async () => {
  const res = await axios.post('/api/auth/login', {
    username: 'admin',
    password: 'admin123',
    userType: 'admin',
  })
  const c = check(res, '管理员登录')
  if (c.ok && res.data.data?.token) {
    adminToken = res.data.data.token
  }
  return c
})

CASE('登录失败：错误密码', async () => {
  const res = await axios.post('/api/auth/login', {
    username: 'user01',
    password: 'wrongpass',
    userType: 'user',
  })
  const c = check(res, '登录失败')
  // 预期 code != 1
  return { ...c, ok: !c.ok, label: '登录失败（期望失败）' }
})

// ---------- 用户相关 ----------
CASE('获取个人信息（需登录）', async () => {
  const res = await axios.get('/api/user/profile', {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  return check(res, '获取个人信息')
})

CASE('更新个人信息（需登录）', async () => {
  const res = await axios.put('/api/user/profile', {
    realName: '更新后的名字',
    gender: '男',
    phone: '74955953457',
    email: 'updated@example.com',
  }, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  return check(res, '更新个人信息')
})

CASE('修改密码（需登录）', async () => {
  const res = await axios.put('/api/user/password', {
    oldPassword: '654321',
    newPassword: '123456',
  }, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  return check(res, '修改密码')
})

CASE('获取所有用户（免token）', async () => {
  const res = await axios.get('/user', {})
  return check(res, '获取所有用户（免token）')
})

CASE('根据用户名获取用户（免token）', async () => {
  const res = await axios.post('/user', { username: 'user01' }, {})
  return check(res, '根据用户名获取用户')
})

// ---------- 管理员接口 ----------
CASE('管理员：用户列表', async () => {
  const res = await axios.get('/api/admin/users', {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '管理员用户列表')
})

CASE('管理员：数据统计', async () => {
  const res = await axios.get('/api/admin/statistics', {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '数据统计')
})

CASE('管理员：审核物品', async () => {
  const itemId = validItemId || 1
  const res = await axios.put(`/api/admin/items/${itemId}/audit`, {
    passed: true,
    remark: '测试审核',
  }, {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '审核物品')
})

CASE('管理员：删除评论', async () => {
  // 用测试脚本发布的评论ID，而不是硬编码1
  const cid = globalThis._lastCommentId || validCommentId
  if (!cid) return { label: '管理员删除评论', ok: false, code: null, msg: '无有效评论ID，跳过', status: null, raw: '' }
  const res = await axios.delete(`/api/admin/comments/${cid}`, {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '删除评论')
})

CASE('管理员：添加分类', async () => {
  const res = await axios.post('/api/categories', {
    category: '测试分类_' + Date.now(),
  }, {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '添加分类')
})

CASE('管理员：更新配置', async () => {
  const res = await axios.put('/api/config/banners', {
    value: JSON.stringify([{ name: 'test', value: 'http://test.com' }]),
  }, {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '更新配置')
})

CASE('管理员：发布公告', async () => {
  const res = await axios.post('/api/announcements', {
    title: '测试公告_' + Date.now(),
    introduction: '简介',
    image: '',
    content: '<p>公告内容</p>',
  }, {
    headers: { Authorization: `Bearer ${adminToken || globalToken}` },
  })
  return check(res, '发布公告')
})

// ---------- 旧物相关 ----------
CASE('获取分类列表', async () => {
  const res = await axios.get('/api/categories')
  return check(res, '获取分类列表')
})

CASE('获取旧物列表（分页）', async () => {
  const res = await axios.get('/api/items', { params: { page: 1, size: 5 } })
  return check(res, '旧物列表')
})

CASE('获取旧物列表（带分类筛选）', async () => {
  const res = await axios.get('/api/categories')
  const cat = res.data?.data?.[0]?.categoryName || ''
  const r2 = await axios.get('/api/items', { params: { page: 1, size: 5, category: cat } })
  return check(r2, '旧物列表（按分类）')
})

CASE('获取旧物列表（带关键词）', async () => {
  const res = await axios.get('/api/items', { params: { page: 1, size: 5, keywords: '手机' } })
  return check(res, '旧物列表（关键词）')
})

CASE('获取旧物详情（动态ID）', async () => {
  const itemId = validItemId || 1
  const res = await axios.get(`/api/items/${itemId}`, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  return check(res, '旧物详情')
})

CASE('获取旧物详情（不存在的id）', async () => {
  const res = await axios.get('/api/items/999999', {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  const c = check(res, '旧物详情（不存在）')
  return { ...c, ok: !c.ok, label: '旧物详情不存在（期望失败）' }
})

CASE('发布旧物（需卖家权限）', async () => {
  const res = await axios.post('/api/items', {
    itemName: '测试物品_' + Date.now(),
    itemCategory: '电子产品',
    itemImage: 'http://localhost:8080/swap_platform/upload/test.jpg',
    exchangeRequest: '想换手机',
    itemDetail: '成色很新',
    contactName: '张三',
    contactPhone: '74955953457',
  }, {
    headers: { Authorization: `Bearer ${sellerToken}` },
  })
  const c = check(res, '发布旧物')
  // 保存 itemId 供后续使用
  if (c.ok && res.data.data?.itemId) {
    globalThis._lastItemId = res.data.data.itemId
  }
  return c
})

CASE('更新旧物（需卖家权限）', async () => {
  const itemId = globalThis._lastItemId || validItemId || 1
  const res = await axios.put(`/api/items/${itemId}`, {
    itemName: '更新后的物品名',
    itemCategory: '电子产品',
    itemImage: 'http://localhost:8080/swap_platform/upload/test.jpg',
    exchangeRequest: '想换电脑',
    itemDetail: '更新了描述',
    contactName: '张三',
    contactPhone: '74955953457',
  }, {
    headers: { Authorization: `Bearer ${sellerToken}` },
  })
  return check(res, '更新旧物')
})

CASE('删除旧物（需卖家权限）', async () => {
  const itemId = globalThis._lastItemId || validItemId || 1
  const res = await axios.delete(`/api/items/${itemId}`, {
    headers: { Authorization: `Bearer ${sellerToken}` },
  })
  return check(res, '删除旧物')
})

CASE('点赞（需登录）', async () => {
  const itemId = globalThis._lastItemId || validItemId || 1
  const res = await axios.post(`/api/items/${itemId}/reaction`, { type: 'like' }, {
    headers: { Authorization: `Bearer ${sellerToken || globalToken}` },
  })
  return check(res, '点赞')
})

CASE('点踩（需登录）', async () => {
  const itemId = globalThis._lastItemId || validItemId || 1
  const res = await axios.post(`/api/items/${itemId}/reaction`, { type: 'dislike' }, {
    headers: { Authorization: `Bearer ${sellerToken || globalToken}` },
  })
  return check(res, '点踩')
})

// ---------- 评论相关 ----------
CASE('获取评论列表', async () => {
  const itemId = validItemId || 1
  const res = await axios.get('/api/comments', { params: { itemId, page: 1, size: 5 } })
  return check(res, '评论列表')
})

CASE('发布评论（需登录）', async () => {
  const itemId = validItemId || 1
  const res = await axios.post('/api/comments', {
    itemId,
    content: '接口测试评论_' + Date.now(),
  }, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  const c = check(res, '发布评论')
  if (c.ok && res.data.data?.id) {
    globalThis._lastCommentId = res.data.data.id
    validCommentId = res.data.data.id
  }
  return c
})

CASE('回复评论（需卖家权限）', async () => {
  const cid = globalThis._lastCommentId || validCommentId
  if (!cid) return { label: '回复评论', ok: false, code: null, msg: '无有效评论ID，跳过', status: null, raw: '' }
  const res = await axios.put(`/api/comments/${cid}/reply`, { reply: '测试回复' }, {
    headers: { Authorization: `Bearer ${sellerToken}` },
  })
  return check(res, '回复评论')
})

// ---------- 收藏相关 ----------
CASE('收藏列表（需登录）', async () => {
  const res = await axios.get('/api/favorites', { params: { page: 1, size: 5 }, headers: { Authorization: `Bearer ${globalToken}` } })
  return check(res, '收藏列表')
})

CASE('添加收藏（需登录）', async () => {
  const itemId = validItemId || 1
  const res = await axios.post('/api/favorites', {
    itemId,
    itemType: '手机',
    name: '测试收藏',
    image: 'http://localhost:8080/swap_platform/upload/test.jpg',
  }, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  const c = check(res, '添加收藏')
  if (c.ok && res.data.data?.id) {
    globalThis._lastFavId = res.data.data.id
  }
  return c
})

CASE('取消收藏（需登录）', async () => {
  const fid = globalThis._lastFavId || 1
  const res = await axios.delete(`/api/favorites/${fid}`, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  return check(res, '取消收藏')
})

// ---------- 置换相关 ----------
CASE('置换列表（需登录）', async () => {
  const res = await axios.get('/api/exchange-deals', { params: { page: 1, size: 5, role: 'user' }, headers: { Authorization: `Bearer ${globalToken}` } })
  return check(res, '置换列表')
})

CASE('发起置换（需登录）', async () => {
  const itemId = validItemId || 1
  const res = await axios.post('/api/exchange-deals', {
    targetItemId: itemId,
    offerItemName: '测试置换物品',
    offerItemImage: 'http://localhost:8080/swap_platform/upload/test.jpg',
    offerDetail: '<p>测试置换详情</p>',
  }, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  const c = check(res, '发起置换')
  if (c.ok && res.data.data?.id) {
    globalThis._lastDealId = res.data.data.id
  }
  return c
})

CASE('审核置换（需卖家/管理员）', async () => {
  const did = globalThis._lastDealId || 1
  const res = await axios.put(`/api/exchange-deals/${did}/approve`, {
    approved: 'true',
    approvalReply: '测试审核通过',
  }, {
    headers: { Authorization: `Bearer ${sellerToken || adminToken}` },
  })
  return check(res, '审核置换')
})

// ---------- 公告相关 ----------
CASE('公告列表', async () => {
  const res = await axios.get('/api/announcements', { params: { page: 1, size: 5 } })
  return check(res, '公告列表')
})

CASE('公告详情（需登录）', async () => {
  // 如果没有有效公告ID，尝试从公告列表获取
  let annId = validAnnouncementId
  if (!annId) {
    try {
      const listRes = await axios.get('/api/announcements', { params: { page: 1, size: 1 } })
      const records = listRes.data?.data?.records || listRes.data?.data || []
      if (records.length > 0) annId = records[0].id
    } catch (e) { /* ignore */ }
  }
  if (!annId) return { label: '公告详情', ok: false, code: null, msg: '无有效公告ID，跳过', status: null, raw: '' }
  const res = await axios.get(`/api/announcements/${annId}`, {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  return check(res, '公告详情')
})

CASE('公告详情（不存在的id）', async () => {
  const res = await axios.get('/api/announcements/999999', {
    headers: { Authorization: `Bearer ${globalToken}` },
  })
  const c = check(res, '公告详情不存在')
  return { ...c, ok: !c.ok, label: '公告详情不存在（期望失败）' }
})

CASE('Banner 配置', async () => {
  const res = await axios.get('/api/config/banners')
  return check(res, 'Banner配置')
})

// ==================== 运行测试 ====================
async function runTests() {
  console.log(`\n🚀 Swap Platform 接口测试`)
  console.log(`   目标: ${BASE_URL}`)
  console.log(`   时间: ${new Date().toLocaleString()}`)
  console.log(`   用例数: ${testCases.length}\n`)
  console.log('─'.repeat(80))

  // 先登录，再预取有效ID
  console.log('⏳ 先执行登录用例并预取有效ID...\n')
  // 单独跑登录用例
  const loginCases = testCases.filter(tc => tc.name.includes('登录'))
  const otherCases = testCases.filter(tc => !tc.name.includes('登录'))

  const results = []
  let pass = 0
  let fail = 0

  for (const tc of loginCases) {
    try {
      const result = await tc.fn()
      results.push({ name: tc.name, ...result })
      if (result.ok) { pass++ } else { fail++ }
    } catch (e) {
      results.push({
        name: tc.name,
        ok: false,
        code: null,
        msg: e.message,
        raw: e.stack?.slice(0, 200) || '',
        label: tc.name,
      })
      fail++
    }
    await sleep(150)
  }

  // 预取有效ID
  console.log('🔍 预取有效旧物ID和公告ID...')
  await fetchValidIds()
  console.log(`   有效旧物ID: ${validItemId || '未获取到'}`)
  console.log(`   有效公告ID: ${validAnnouncementId || '未获取到'}\n`)

  // 跑其余用例
  for (const tc of otherCases) {
    try {
      const result = await tc.fn()
      results.push({ name: tc.name, ...result })
      if (result.ok) {
        pass++
      } else {
        fail++
      }
    } catch (e) {
      // 网络错误等
      results.push({
        name: tc.name,
        ok: false,
        code: null,
        msg: e.message,
        raw: e.stack?.slice(0, 200) || '',
        label: tc.name,
      })
      fail++
    }

    // 每个接口之间稍作延迟，避免压垮服务
    await sleep(150)
  }

  // ==================== 输出报告 ====================
  console.log('\n')
  console.log('═'.repeat(80))
  console.log('  测试报告')
  console.log('═'.repeat(80))

  // 分类统计
  const groups = {}
  for (const r of results) {
    const prefix = r.label.split('：')[0].split('（')[0].split('(')[0]
    if (!groups[prefix]) groups[prefix] = { pass: 0, fail: 0, total: 0 }
    groups[prefix].total++
    if (r.ok) {
      groups[prefix].pass++
    } else {
      groups[prefix].fail++
    }
  }

  console.log('\n📊 分类统计：')
  for (const [g, s] of Object.entries(groups)) {
    const mark = s.fail === 0 ? '✅' : '⚠️'
    console.log(`  ${mark} ${g}: ${s.pass}/${s.total} 通过`)
  }

  console.log(`\n  总计: ${results.length} | 通过: ${pass} | 失败: ${fail}`)
  console.log('─'.repeat(80))

  // 失败详情
  const failed = results.filter(r => !r.ok)
  if (failed.length > 0) {
    console.log('\n❌ 失败用例详情：')
    for (const r of failed) {
      console.log(`\n  【${r.label}】`)
      console.log(`  HTTP状态: ${r.status || 'N/A'}`)
      console.log(`  响应code: ${r.code}`)
      console.log(`  响应msg:  ${r.msg}`)
      console.log(`  原始返回: ${r.raw}`)
    }
  }

  // 通过用例（折叠显示）
  console.log('\n✅ 通过用例：')
  const passed = results.filter(r => r.ok)
  for (const r of passed) {
    console.log(`  ✔ ${r.label}`)
  }

  console.log('\n' + '═'.repeat(80))

  // 保存 JSON 报告
  const report = {
    time: new Date().toISOString(),
    baseUrl: BASE_URL,
    summary: { total: results.length, pass, fail },
    groups: Object.fromEntries(
      Object.entries(groups).map(([k, v]) => [k, `${v.pass}/${v.total}`])
    ),
    failed: failed.map(r => ({
      name: r.label,
      status: r.status,
      code: r.code,
      msg: r.msg,
      raw: r.raw,
    })),
    passed: passed.map(r => r.label),
  }

  const reportPath = 'scripts/last-report.json'
  writeFileSync(reportPath, JSON.stringify(report, null, 2), 'utf-8')
  console.log(`\n📄 报告已保存: ${reportPath}\n`)

  // 非 --once 模式：每 30 秒自动重跑
  if (!ONCE) {
    console.log('⏳ 30 秒后自动重跑（Ctrl+C 退出）\n')
    setTimeout(runTests, 30000)
  }
}

// 启动
runTests().catch(e => {
  console.error('测试脚本异常:', e.message)
  process.exit(1)
})
