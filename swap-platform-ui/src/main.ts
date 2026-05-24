import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import App from './App.vue'
import './style.css'

// Views
import HomeView from './views/HomeView.vue'
import ItemDetailView from './views/ItemDetailView.vue'
import PublishView from './views/PublishView.vue'
import ProfileView from './views/ProfileView.vue'
import DebugView from './views/DebugView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: HomeView, meta: { title: '首页' } },
    { path: '/item/:id', name: 'item-detail', component: ItemDetailView, meta: { title: '物品详情' } },
    { path: '/publish', name: 'publish', component: PublishView, meta: { title: '发布旧物', auth: true } },
    { path: '/profile', name: 'profile', component: ProfileView, meta: { title: '个人中心', auth: true } },
    { path: '/debug', name: 'debug', component: DebugView, meta: { title: '接口调试' } },
  ],
})

router.beforeEach((to, _from, next) => {
  document.title = (to.meta.title as string) || '闲置交换平台'
  const token = localStorage.getItem('token')
  if (to.meta.auth && !token) {
    next({ name: 'home' })
  } else {
    next()
  }
})

const app = createApp(App)
app.use(ElementPlus, { locale: zhCn })
app.use(router)
app.mount('#app')
