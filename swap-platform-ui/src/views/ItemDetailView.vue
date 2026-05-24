<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getItemDetail, addComment, getCommentList } from '../api'
import type { ItemInfoVO, CommentVO } from '../api/types'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const item = ref<ItemInfoVO | null>(null)
const loading = ref(false)
const commentText = ref('')
const comments = ref<CommentVO[]>([])
const commentLoading = ref(false)

onMounted(async () => {
  const id = Number(route.params.id)
  if (!id) {
    router.push('/')
    return
  }
  await fetchItem(id)
  await fetchComments(id)
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

const fetchComments = async (id: number) => {
  commentLoading.value = true
  try {
    const res = await getCommentList({ itemId: id, page: 1, size: 10 })
    comments.value = res.records
  } catch (e) {
    console.error(e)
  } finally {
    commentLoading.value = false
  }
}

const handleComment = async () => {
  if (!commentText.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  try {
    await addComment({ itemId: item.value?.id, content: commentText.value })
    ElMessage.success('评论成功')
    commentText.value = ''
    if (item.value?.id) {
      await fetchComments(item.value.id)
    }
  } catch (e) {
    ElMessage.error('评论失败')
  }
}

const handleExchange = () => {
  ElMessage.info('请先登录并发布你的物品，再发起置换申请')
  router.push('/publish')
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
          <div v-html="item.itemDetail" style="margin: 12px 0; color: #606266"></div>
          <p><strong>发布人：</strong>{{ item.contactName || '匿名' }}</p>
          <p><strong>联系方式：</strong>{{ item.contactPhone || '-' }}</p>
          <p><strong>期望置换：</strong>{{ item.exchangeRequest }}</p>
          <el-button type="primary" style="margin-top: 16px" @click="handleExchange">发起置换</el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card shadow="never" style="margin-top: 20px">
      <template #header>💬 评论 ({{ comments.length }})</template>
      <div class="comment-list">
        <el-skeleton v-if="commentLoading" :rows="3" animated />
        <el-empty v-else-if="comments.length === 0" description="暂无评论" />
        <el-timeline v-else>
          <el-timeline-item
            v-for="c in comments"
            :key="c.id"
            :timestamp="c.addTime"
            placement="top"
          >
            <div class="comment-item">
              <strong>{{ c.username || '匿名用户' }}：</strong>
              <span>{{ c.content }}</span>
              <div v-if="c.reply" class="comment-reply">
                <el-tag type="info" size="small">回复</el-tag> {{ c.reply }}
              </div>
            </div>
          </el-timeline-item>
        </el-timeline>
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
