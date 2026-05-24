<script setup lang="ts">
import { RouterView, RouterLink } from 'vue-router'
</script>

<template>
  <div class="app-layout">
    <header class="app-header">
      <div class="logo">
        <RouterLink to="/">♻️ 闲置交换平台</RouterLink>
      </div>
      <nav class="nav">
        <RouterLink to="/" exact-active-class="active">首页</RouterLink>
        <RouterLink to="/publish" active-class="active">发布</RouterLink>
        <RouterLink to="/profile" active-class="active">我的</RouterLink>
        <RouterLink to="/debug" active-class="active">调试</RouterLink>
      </nav>
    </header>
    <main class="app-main">
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.app-layout {
  min-height: 100vh;
  background: #f5f7fa;
}
.app-header {
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  position: sticky;
  top: 0;
  z-index: 100;
}
.logo a {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  text-decoration: none;
}
.logo a:hover {
  color: #409eff;
}
.nav {
  display: flex;
  gap: 24px;
}
.nav a {
  text-decoration: none;
  color: #606266;
  font-size: 14px;
  padding: 6px 0;
  border-bottom: 2px solid transparent;
  transition: all 0.2s;
}
.nav a:hover {
  color: #409eff;
}
.nav a.active {
  color: #409eff;
  border-bottom-color: #409eff;
}
.app-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}
</style>
