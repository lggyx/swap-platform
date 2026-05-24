<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategories, addItem } from '../api'
import type { CategoryVO } from '../api/types'
import { ElMessage } from 'element-plus'

const router = useRouter()
const categories = ref<CategoryVO[]>([])
const form = ref({
  itemName: '',
  itemCategory: '',
  itemImage: '',
  exchangeRequest: '',
  itemDetail: '',
  contactName: '',
  contactPhone: '',
})

onMounted(async () => {
  try {
    categories.value = await getCategories()
  } catch (e) {
    console.error(e)
  }
})

const handleSubmit = async () => {
  if (!form.value.itemName || !form.value.itemCategory) {
    ElMessage.warning('请填写必填项')
    return
  }
  try {
    await addItem(form.value)
    ElMessage.success('发布成功')
    router.push('/')
  } catch (e) {
    ElMessage.error('发布失败')
  }
}
</script>

<template>
  <div class="publish-page">
    <el-page-header @back="router.back()" content="发布旧物" style="margin-bottom: 20px" />
    <el-card>
      <el-form :model="form" label-width="100px">
        <el-form-item label="物品名称" required>
          <el-input v-model="form.itemName" placeholder="请输入物品名称" />
        </el-form-item>
        <el-form-item label="分类" required>
          <el-select v-model="form.itemCategory" placeholder="请选择分类" style="width: 100%">
            <el-option
              v-for="cat in categories"
              :key="cat.id"
              :label="cat.categoryName"
              :value="cat.categoryName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="图片链接">
          <el-input v-model="form.itemImage" placeholder="请输入图片 URL" />
        </el-form-item>
        <el-form-item label="期望置换">
          <el-input v-model="form.exchangeRequest" placeholder="你希望换到什么？" />
        </el-form-item>
        <el-form-item label="详细描述">
          <el-input v-model="form.itemDetail" type="textarea" :rows="4" placeholder="描述一下物品的新旧程度、使用情况等" />
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="form.contactName" placeholder="你的称呼" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.contactPhone" placeholder="方便联系的手机号" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">立即发布</el-button>
          <el-button @click="router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
