<script setup lang="ts">
import { ref } from 'vue'
import { login, getItemList, getAnnouncementsList, getCategories, getProfile } from './api'

const results = ref<Record<string, any>>({})
const loading = ref<Record<string, boolean>>({})

const test = async (name: string, fn: () => Promise<any>) => {
  loading.value[name] = true
  try {
    results.value[name] = await fn()
  } catch (e: any) {
    results.value[name] = { error: e.message || String(e) }
  } finally {
    loading.value[name] = false
  }
}

const loginForm = ref({ username: 'user01', password: '123456', userType: 'user' })
const itemParams = ref({ page: 1, size: 5 })
const announcementParams = ref({ page: 1, size: 5 })
</script>

<template>
  <div class="test-panel">
    <h1>接口联调测试面板</h1>

    <!-- 认证模块 -->
    <section class="module">
      <h2>认证模块</h2>
      <div class="form-row">
        <input v-model="loginForm.username" placeholder="用户名" />
        <input v-model="loginForm.password" placeholder="密码" type="password" />
        <input v-model="loginForm.userType" placeholder="user/seller/admin" />
        <button
          :disabled="loading['login']"
          @click="test('login', () => login(loginForm))"
        >
          登录
        </button>
      </div>
    </section>

    <!-- 物品模块 -->
    <section class="module">
      <h2>旧物模块</h2>
      <div class="form-row">
        <input v-model.number="itemParams.page" type="number" placeholder="页码" />
        <input v-model.number="itemParams.size" type="number" placeholder="每页数量" />
        <button
          :disabled="loading['itemList']"
          @click="test('itemList', () => getItemList(itemParams))"
        >
          获取旧物列表
        </button>
        <button
          :disabled="loading['categories']"
          @click="test('categories', () => getCategories())"
        >
          获取分类
        </button>
      </div>
    </section>

    <!-- 公告模块 -->
    <section class="module">
      <h2>公告模块</h2>
      <div class="form-row">
        <input v-model.number="announcementParams.page" type="number" placeholder="页码" />
        <input v-model.number="announcementParams.size" type="number" placeholder="每页数量" />
        <button
          :disabled="loading['announcements']"
          @click="test('announcements', () => getAnnouncementsList(announcementParams))"
        >
          获取公告列表
        </button>
      </div>
    </section>

    <!-- 个人信息（需要先登录） -->
    <section class="module">
      <h2>个人信息（需登录）</h2>
      <button
        :disabled="loading['profile']"
        @click="test('profile', () => getProfile())"
      >
        获取个人信息
      </button>
    </section>

    <!-- 结果展示 -->
    <section class="results">
      <h2>请求结果</h2>
      <pre v-for="(val, key) in results" :key="key" class="result-item">
<strong>{{ key }}</strong>
{{ JSON.stringify(val, null, 2) }}
      </pre>
    </section>
  </div>
</template>

<style scoped>
.test-panel {
  max-width: 900px;
  margin: 40px auto;
  padding: 20px;
  font-family: monospace;
}
.module {
  border: 1px solid #ddd;
  padding: 16px;
  margin-bottom: 16px;
  border-radius: 6px;
}
.form-row {
  display: flex;
  gap: 8px;
  margin-top: 8px;
  flex-wrap: wrap;
}
input {
  padding: 6px 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  padding: 6px 14px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:disabled {
  background: #aaa;
  cursor: not-allowed;
}
.results {
  margin-top: 24px;
}
.result-item {
  background: #f5f5f5;
  padding: 12px;
  border-radius: 4px;
  margin-bottom: 12px;
  white-space: pre-wrap;
  word-break: break-all;
}
</style>
