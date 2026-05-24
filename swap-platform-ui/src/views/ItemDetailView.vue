<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getItemDetail, addComment } from '../api'
import type { ItemVO } from '../api/types'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const item = ref<ItemVO | null>(null)
const loading = ref(false)
const commentText = ref('')

onMounted(async () => {
  const id = Number(route.params.id)
  if (!id) {
    router.push('/')
    return
  }
  await fetchItem(id)
})

const fetchItem = async (id: number) => {
  loading.value = true
  try {
    item.value = await getItemDetail(id)
  } catch (e) {
    ElMessage.error('加载失败')
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handleComment = async () => {
  if (!commentText.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  try {
    await addComment({ itemId: item.value?.itemId, content: commentText.value })
    ElMessage.success('评论成功')
    commentText.value = ''
  } catch (e) {
    ElMessage.error('评论失败')
  }
}
</script>

<template>
  <div class="detail-page" v-if="item">
    <el-page-header @back="router.back()" content="物品详情" style="margin-bottom: 20px" />
    <el-card>
      <el-row :gutter="24">
        <el-col :xs="24" :md="10">
          <el-image
            v-if="item.itemImage"
            :src="item.itemImage"
            fit="cover"
            style="width: 100%; border-radius: 8px"
          />
          <div v-else class="no-image">暂无图片</div>
        </el-col>
        <el-col :xs="24" :md="14">
          <h2>{{ item.itemName }}</h2>
          <el-tag type="success" style="margin-bottom: 12px">{{ item.itemCategory }}</el-tag>
          <p style="margin: 12px 0; color: #606266">{{ item.itemDetail }}</p>
          <p><strong>发布人：</strong>{{ item.contactName || '匿名' }}</p>
          <p><strong>联系方式：</strong>{{ item.contactPhone || '-' }}</p>
          <p><strong>期望置换：</strong>{{ item.exchangeRequest }}</p>
          <el-button type="primary" style="margin-top: 16px">发起置换</el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card shadow="never" style="margin-top: 20px">
      <template #header>💬 评论</template>
      <div class="comment-list">
        <el-empty v-if="false" description="暂无评论" />
      </div>
      <div class="comment-form">
        <el-input
          v-model="commentText"
          type="textarea"
          :rows="3"
          placeholder="写下你的评论..."
        />
        <el-button type="primary" style="margin-top: 8px" @click="handleComment">发表评论</el-button>
      </div>
    </el-card>
  </div>
  <el-skeleton v-else :loading="loading" :rows="5" animated />
</template>

<style scoped>
.no-image {
  width: 100%;
  height: 300px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  border-radius: 8px;
}
.comment-form {
  margin-top: 16px;
}
</style>
