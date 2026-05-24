<script setup lang="ts">
import { ref } from 'vue'
import { login, register, getProfile, getItemList } from '../api'

const activeTab = ref('login')
const loginForm = ref({ username: '', password: '', userType: 'user' })
const registerForm = ref({
  username: '',
  password: '',
  realName: '',
  gender: '',
  phone: '',
  email: '',
})
const items = ref<any[]>([])
const itemParams = ref({ page: 1, size: 5 })
const responseLog = ref('')

const log = (label: string, data: any) => {
  responseLog.value += `\n===== ${label} =====\n${JSON.stringify(data, null, 2)}\n`
}

const handleLogin = async () => {
  try {
    const res = await login(loginForm.value)
    log('登录结果', res)
  } catch (e) {
    log('登录失败', e)
  }
}

const handleRegister = async () => {
  try {
    const res = await register(registerForm.value)
    log('注册结果', res)
  } catch (e) {
    log('注册失败', e)
  }
}

const handleGetItems = async () => {
  try {
    const res = await getItemList(itemParams.value)
    log('物品列表', res)
  } catch (e) {
    log('请求失败', e)
  }
}

const handleProfile = async () => {
  try {
    const res = await getProfile()
    log('个人信息', res)
  } catch (e) {
    log('获取失败', e)
  }
}
</script>

<template>
  <div class="debug-page">
    <el-page-header @back="$router.back()" content="接口调试" style="margin-bottom: 20px" />
    <el-tabs v-model="activeTab">
      <el-tab-pane label="登录" name="login">
        <el-form :model="loginForm" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="loginForm.username" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="loginForm.password" type="password" />
          </el-form-item>
          <el-form-item label="用户类型">
            <el-radio-group v-model="loginForm.userType">
              <el-radio label="user">普通用户</el-radio>
              <el-radio label="seller">卖家</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="注册" name="register">
        <el-form :model="registerForm" label-width="80px">
          <el-form-item label="用户名"><el-input v-model="registerForm.username" /></el-form-item>
          <el-form-item label="密码"><el-input v-model="registerForm.password" type="password" /></el-form-item>
          <el-form-item label="真实姓名"><el-input v-model="registerForm.realName" /></el-form-item>
          <el-form-item label="性别"><el-input v-model="registerForm.gender" /></el-form-item>
          <el-form-item label="手机号"><el-input v-model="registerForm.phone" /></el-form-item>
          <el-form-item label="邮箱"><el-input v-model="registerForm.email" /></el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="查询" name="query">
        <el-form :model="itemParams" label-width="80px">
          <el-form-item label="页码"><el-input v-model.number="itemParams.page" type="number" /></el-form-item>
          <el-form-item label="每页条数"><el-input v-model.number="itemParams.size" type="number" /></el-form-item>
          <el-button type="primary" @click="handleGetItems">查询物品列表</el-button>
          <el-button @click="handleProfile">获取个人信息</el-button>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <el-card shadow="never" style="margin-top: 20px">
      <template #header>响应日志</template>
      <pre class="response-log">{{ responseLog || '暂无请求' }}</pre>
    </el-card>
  </div>
</template>

<style scoped>
.response-log {
  white-space: pre-wrap;
  word-break: break-all;
  max-height: 500px;
  overflow-y: auto;
  background: #f5f7fa;
  padding: 12px;
  border-radius: 4px;
  font-size: 13px;
}
</style>
