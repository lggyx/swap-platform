<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getItemList, getCategories } from '../api'
import type { ItemVO, CategoryVO } from '../api/types'
import { Loading } from '@element-plus/icons-vue'

const router = useRouter()
const items = ref<ItemVO[]>([])
const categories = ref<CategoryVO[]>([])
const loading = ref(false)
const keywords = ref('')
const selectedCategory = ref('')

onMounted(async () => {
  await Promise.all([fetchCategories(), fetchItems()])
})

const fetchCategories = async () => {
  try {
    categories.value = await getCategories()
  } catch (e) {
    console.error('Failed to fetch categories', e)
  }
}

const fetchItems = async () => {
  loading.value = true
  try {
    const res = await getItemList({
      page: 1,
      size: 20,
      category: selectedCategory.value || undefined,
      keywords: keywords.value || undefined,
    })
    items.value = res.records || []
  } catch (e) {
    console.error('Failed to fetch items', e)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  fetchItems()
}

const handleCategoryChange = (val: string) => {
  selectedCategory.value = val
  fetchItems()
}

const goToDetail = (id: number) => {
  router.push(`/item/${id}`)
}
</script>

<template>
  <div class="home-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>🔍 浏览旧物</span>
        </div>
      </template>
      <div class="filters">
        <el-input
          v-model="keywords"
          placeholder="搜索物品..."
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
        />
        <el-select
          v-model="selectedCategory"
          placeholder="选择分类"
          clearable
          style="width: 160px"
          @change="handleCategoryChange"
        >
          <el-option
            v-for="cat in categories"
            :key="cat.id"
            :label="cat.categoryName"
            :value="cat.categoryName"
          />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
    </el-card>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col
        v-for="item in items"
        :key="item.id"
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
      >
        <el-card shadow="hover" style="margin-bottom: 20px; cursor: pointer" @click="goToDetail(item.id)">
          <div class="item-card">
            <div class="item-image">
              <el-image
                v-if="item.itemImage"
                :src="item.itemImage"
                fit="cover"
                style="width: 100%; height: 160px"
              />
              <div v-else class="item-image-placeholder">暂无图片</div>
            </div>
            <div class="item-info">
              <div class="item-name">{{ item.itemName }}</div>
              <div class="item-category">
                <el-tag size="small">{{ item.itemCategory }}</el-tag>
              </div>
              <div class="item-detail">{{ item.itemDetail }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="!loading && items.length === 0" description="暂无物品" />
    <div v-if="loading" class="loading-wrap">
      <el-icon class="is-loading"><Loading /></el-icon>
    </div>
  </div>
</template>

<style scoped>
.filters {
  display: flex;
  gap: 12px;
  align-items: center;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.item-card {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.item-image-placeholder {
  width: 100%;
  height: 160px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  font-size: 14px;
}
.item-name {
  font-weight: 600;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.item-category {
  margin: 4px 0;
}
.item-detail {
  font-size: 13px;
  color: #606266;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.loading-wrap {
  text-align: center;
  padding: 40px 0;
  font-size: 24px;
  color: #409eff;
}
</style>
