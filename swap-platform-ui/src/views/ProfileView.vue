<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProfile, updateProfile, updatePassword, logout } from '../api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const profile = ref({
  username: '',
  realName: '',
  gender: '',
  phone: '',
  email: '',
  photo: '',
})
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
})

onMounted(async () => {
  try {
    profile.value = await getProfile()
  } catch (e) {
    ElMessage.error('请先登录')
    router.push('/')
  }
})

const handleUpdateProfile = async () => {
  try {
    await updateProfile(profile.value)
    ElMessage.success('更新成功')
  } catch (e) {
    ElMessage.error('更新失败')
  }
}

const handleUpdatePassword = async () => {
  if (!passwordForm.value.oldPassword || !passwordForm.value.newPassword) {
    ElMessage.warning('请填写完整密码信息')
    return
  }
  try {
    await updatePassword(passwordForm.value)
    ElMessage.success('密码修改成功')
    passwordForm.value.oldPassword = ''
    passwordForm.value.newPassword = ''
  } catch (e) {
    ElMessage.error('密码修改失败')
  }
}

const handleLogout = async () => {
  try {
    await logout()
  } catch (e) {
    // ignore
  } finally {
    localStorage.removeItem('token')
    ElMessage.success('已退出登录')
    router.push('/')
  }
}
</script>

<template>
  <div class="profile-page">
    <el-page-header @back="router.back()" content="个人中心" style="margin-bottom: 20px" />
    <el-row :gutter="20">
      <el-col :xs="24" :md="10">
        <el-card shadow="never">
          <template #header>👤 个人信息</template>
          <el-form :model="profile" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="profile.username" disabled />
            </el-form-item>
            <el-form-item label="真实姓名">
              <el-input v-model="profile.realName" placeholder="请输入真实姓名" />
            </el-form-item>
            <el-form-item label="性别">
              <el-input v-model="profile.gender" placeholder="男/女" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="profile.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="profile.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdateProfile">保存修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="14">
        <el-card shadow="never" style="margin-bottom: 20px">
          <template #header>🔒 修改密码</template>
          <el-form :model="passwordForm" label-width="80px">
            <el-form-item label="旧密码">
              <el-input v-model="passwordForm.oldPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="passwordForm.newPassword" type="password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdatePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card shadow="never">
          <template #header>⚙️ 其他操作</template>
          <el-button type="danger" @click="handleLogout">退出登录</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
