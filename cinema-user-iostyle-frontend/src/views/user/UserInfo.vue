<template>
  <div class="ios-container">
    <!-- 顶部导航栏 -->
    
    
    <!-- 用户信息卡片 -->
    <div class="profile-card">
      <div class="avatar-area">
        <div class="avatar">
          <el-avatar :size="80" :src="avatar">{{ userName ? userName.substring(0, 1).toUpperCase() : 'U' }}</el-avatar>
        </div>
        <div class="user-basic-info">
          <div class="user-name">{{ userName || '未登录' }}</div>
          <div class="user-phone">{{ userInfo.phoneNumber || '请先登录账号' }}</div>
        </div>
      </div>
    </div>
    
    <!-- 个人资料卡片 -->
    <div class="info-section">
      <div class="section-title">个人资料</div>
      <div class="section-card">
        <div class="info-item">
          <div class="info-label">用户名</div>
          <div class="info-value">{{ userName }}</div>
        </div>
        <div class="info-item">
          <div class="info-label">手机号码</div>
          <div class="info-value">{{ formatPhone(userInfo.phoneNumber) }}</div>
        </div>
        <div class="info-item">
          <div class="info-label">性别</div>
          <div class="info-value">{{ userInfo.sex ? '男' : '女' }}</div>
        </div>
        <div class="info-item edit" @click="showEditInfoDialog">
          <div class="info-label">编辑个人资料</div>
          <i class="el-icon-arrow-right"></i>
        </div>
      </div>
    </div>
    
    <!-- 账户安全卡片 -->
    <div class="info-section">
      <div class="section-title">账户安全</div>
      <div class="section-card">
        <div class="info-item edit" @click="dialogFormVisible = true">
          <div class="info-label">修改密码</div>
          <i class="el-icon-arrow-right"></i>
        </div>
      </div>
    </div>
    
    <!-- 我的订单卡片 -->
    <div class="info-section">
      <div class="section-title">我的订单</div>
      <div class="section-card">
        <div class="info-item edit" @click="$router.push('/bill')">
          <div class="info-label">查看所有订单</div>
          <i class="el-icon-arrow-right"></i>
        </div>
      </div>
    </div>
    
    <!-- 退出登录按钮 -->
    <div class="logout-button" @click="logout">
      退出登录
    </div>
    
   <el-dialog 
  title="修改密码" 
  :visible.sync="dialogFormVisible" 
  width="85%" 
  custom-class="ios-dialog"
  :close-on-click-modal="false">
      <el-form 
        :model="passwordForm" 
        status-icon 
        :rules="passwordRules" 
        ref="passwordForm"
        label-position="top"
        class="ios-form">
        <el-form-item prop="oldPassword" label="原密码">
          <el-input 
            type="password" 
            v-model="passwordForm.oldPassword" 
            placeholder="请输入原密码"
            class="ios-input">
            <i slot="prefix" class="el-icon-lock"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="newPassword" label="新密码">
          <el-input 
            type="password" 
            v-model="passwordForm.newPassword" 
            placeholder="6-16位字符"
            class="ios-input">
            <i slot="prefix" class="el-icon-key"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="checkPass" label="确认密码">
          <el-input 
            type="password" 
            v-model="passwordForm.checkPass" 
            placeholder="请再次输入密码"
            class="ios-input">
            <i slot="prefix" class="el-icon-check"></i>
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cancel-btn" @click="dialogFormVisible = false">取消</el-button>
        <el-button class="confirm-btn" type="primary" @click="submitPasswordForm('passwordForm')">确认</el-button>
      </div>
    </el-dialog>
    
    <el-dialog 
  title="编辑个人信息" 
  :visible.sync="dialogInfoVisible" 
  width="85%" 
  custom-class="ios-dialog"
  :close-on-click-modal="false">
      <el-form 
        :model="infoForm" 
        :rules="infoRules" 
        ref="infoForm" 
        label-position="top"
        class="ios-form">
        <el-form-item prop="userName" label="用户名">
          <el-input 
            v-model="infoForm.userName" 
            placeholder="请输入用户名" 
            class="ios-input"
            clearable>
            <i slot="prefix" class="el-icon-user"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="phoneNumber" label="手机号码">
          <el-input 
            v-model="infoForm.phoneNumber" 
            placeholder="请输入手机号码" 
            maxlength="11"
            class="ios-input"
            clearable>
            <i slot="prefix" class="el-icon-mobile-phone"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <div class="gender-selector">
            <div 
              class="gender-option" 
              :class="{ active: infoForm.sex === true }"
              @click="infoForm.sex = true">
              <i class="el-icon-male"></i>
              <span>男</span>
            </div>
            <div 
              class="gender-option" 
              :class="{ active: infoForm.sex === false }"
              @click="infoForm.sex = false">
              <i class="el-icon-female"></i>
              <span>女</span>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cancel-btn" @click="dialogInfoVisible = false">取消</el-button>
        <el-button class="confirm-btn" type="primary" @click="submitInfoForm('infoForm')">确认</el-button>
      </div>
    </el-dialog>
    
    <!-- 底部导航栏占位空间 -->
    <div class="bottom-space"></div>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.passwordForm.checkPass !== '') {
          this.$refs.passwordForm.validateField('checkPass');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    
    return {
      userInfo: {},
      avatar: '',
      userName: '',
      dialogFormVisible: false,
      dialogInfoVisible: false,
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        checkPass: ''
      },
      infoForm: {
        userName: '',
        phoneNumber: '',
        sex: true
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      },
      infoRules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        phoneNumber: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      try {
        const loginUser = JSON.parse(window.sessionStorage.getItem('loginUser'));
        if (loginUser) {
          this.userInfo = loginUser;
          this.userName = loginUser.userName;
          
          if (loginUser.userPicture) {
            try {
              const userPicture = JSON.parse(loginUser.userPicture);
              if (userPicture.length > 0) {
                this.avatar = this.global.base + userPicture[0];
              }
            } catch(e) {
              console.error('解析用户头像失败', e);
            }
          }
        } else {
          this.$message({
            message: '请先登录',
            type: 'warning',
            duration: 2000,
            onClose: () => {
              this.$router.push('/login');
            }
          });
        }
      } catch(err) {
        console.error('获取用户信息失败', err);
        this.$message.error('获取用户信息失败');
      }
    },
    
    formatPhone(phone) {
      if (!phone) return '';
      return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
    },
    
    showMessage(message, type = 'info') {
      this.$message({
        message,
        type,
        duration: 2000,
        customClass: 'ios-message',
        center: true,
        offset: 60
      });
    },
    
    submitPasswordForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = {
            oldPassword: this.passwordForm.oldPassword,
            newPassword: this.passwordForm.newPassword,
            userId: this.userInfo.userId
          };
          
          this.showMessage('正在修改密码...', 'info');
          
          axios.put('sysUser/password', params)
            .then(res => {
              if (res.data.code !== 200) {
                this.showMessage(res.data.msg || '修改密码失败', 'error');
                return;
              }
              this.showMessage('密码修改成功', 'success');
              this.dialogFormVisible = false;
              this.resetForm('passwordForm');
            })
            .catch(err => {
              console.error('修改密码失败', err);
              this.showMessage('修改密码失败，请稍后重试', 'error');
            });
        }
      });
    },
    
    showEditInfoDialog() {
      this.infoForm.userName = this.userInfo.userName;
      this.infoForm.phoneNumber = this.userInfo.phoneNumber;
      this.infoForm.sex = this.userInfo.sex;
      this.dialogInfoVisible = true;
    },
    
    submitInfoForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = {
            ...this.infoForm,
            userId: this.userInfo.userId,
            password: this.userInfo.password,
            salt: this.userInfo.salt
          };
          
          this.showMessage('正在更新信息...', 'info');
          
          axios.put('sysUser', params)
            .then(res => {
              if (res.data.code !== 200) {
                this.showMessage(res.data.msg || '修改用户信息失败', 'error');
                return;
              }
              
              const loginUser = JSON.parse(window.sessionStorage.getItem('loginUser'));
              loginUser.userName = this.infoForm.userName;
              loginUser.phoneNumber = this.infoForm.phoneNumber;
              loginUser.sex = this.infoForm.sex;
              window.sessionStorage.setItem('loginUser', JSON.stringify(loginUser));
              
              this.userInfo = loginUser;
              this.userName = loginUser.userName;
              
              this.showMessage('个人信息更新成功', 'success');
              this.dialogInfoVisible = false;
            })
            .catch(err => {
              console.error('修改用户信息失败', err);
              this.showMessage('修改用户信息失败，请稍后重试', 'error');
            });
        }
      });
    },
    
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    
    logout() {
      this.$confirm('确定要退出登录吗?', '提示', {
        confirmButtonText: '退出登录',
        cancelButtonText: '取消',
        customClass: 'ios-action-sheet',
        distinguishCancelAndClose: true,
        center: true,
        showClose: false
      }).then(() => {
        window.sessionStorage.clear();
        this.$message({
          message: '已成功退出登录',
          type: 'success',
          duration: 2000,
          onClose: () => {
            this.$router.push('/login');
          }
        });
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
.ios-container {
  max-width: 430px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  padding-bottom: 20px;
}

.nav-bar {
  height: 44px;
  background-color: #f2f2f7;
  display: flex;
  justify-content: center;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 100;
}

.title {
  font-size: 17px;
  font-weight: 600;
  color: #000;
}
/* 优化性别选择器样式 */
.gender-selector {
  display: flex;
  gap: 10px;
  padding: 4px 0;
}

.gender-option {
  flex: 1;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
  border-radius: 10px;
  font-size: 16px;
  color: #666;
  cursor: pointer;
  transition: all 0.2s ease;
  border: 1px solid transparent;
}

.gender-option:active {
  opacity: 0.7;
}

.gender-option i {
  margin-right: 6px;
  font-size: 18px;
}

.gender-option span {
  font-weight: 500;
}

.gender-option.active {
  background: rgba(0, 122, 255, 0.1);
  color: #007aff;
  border-color: #007aff;
}

/* 优化对话框底部按钮样式 */
:deep(.ios-dialog .el-dialog__footer) {
  padding: 16px;
  border-top: 1px solid rgba(60, 60, 67, 0.1);
}

.dialog-footer {
  display: flex;
  gap: 12px;
  width: 100%;
}

:deep(.dialog-footer .el-button) {
  flex: 1;
  margin: 0;
  height: 48px;
  font-size: 17px;
  font-weight: 500;
  border-radius: 12px;
  padding: 0;
  transition: all 0.2s ease;
}

:deep(.dialog-footer .cancel-btn) {
  background: #f2f2f7;
  border: none;
  color: #007aff;
}

:deep(.dialog-footer .cancel-btn:hover) {
  background: #e5e5ea;
}

:deep(.dialog-footer .cancel-btn:active) {
  background: #d1d1d6;
  transform: scale(0.98);
}

:deep(.dialog-footer .confirm-btn) {
  background: #007aff;
  text-align: center;
  border: none;
  color: white;
}

:deep(.dialog-footer .confirm-btn:hover) {
  background: #0071e3;
}

:deep(.dialog-footer .confirm-btn:active) {
  background: #0062c4;
  transform: scale(0.98);
}

/* 修改按钮加载状态样式 */
:deep(.dialog-footer .el-button.is-loading) {
  background: #f2f2f7;
  color: #8e8e93;
  opacity: 0.8;
  pointer-events: none;
}

:deep(.dialog-footer .el-button .el-icon-loading) {
  margin-right: 4px;
}

/* 修改按钮禁用状态样式 */
:deep(.dialog-footer .el-button.is-disabled) {
  background: #f2f2f7;
  color: #8e8e93;
  opacity: 0.5;
  cursor: not-allowed;
}

/* 用户资料卡片 */
.profile-card {
  background-color: #fff;
  margin: 12px 16px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.avatar-area {
  padding: 20px;
  display: flex;
  align-items: center;
}

.avatar {
  margin-right: 16px;
}

.user-basic-info {
  flex: 1;
}

.user-name {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 4px;
  color: #000;
}

.user-phone {
  font-size: 14px;
  color: #8e8e93;
}

/* 信息部分样式 */
.info-section {
  margin: 24px 16px 12px;
}

.section-title {
  font-size: 13px;
  font-weight: 600;
  color: #8e8e93;
  margin-bottom: 8px;
  margin-left: 4px;
  text-transform: uppercase;
}

.section-card {
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid rgba(60, 60, 67, 0.1);
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  font-size: 16px;
  color: #000;
}

.info-value {
  font-size: 16px;
  color: #8e8e93;
}

.info-item.edit {
  cursor: pointer;
}

.info-item.edit .info-label {
  color: #007aff;
}

.info-item.edit i {
  color: #c7c7cc;
  font-size: 14px;
}

/* 退出登录按钮 */
.logout-button {
  background-color: #fff;
  color: #ff3b30;
  border-radius: 12px;
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  padding: 14px 0;
  margin: 24px 16px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  cursor: pointer;
}

/* 对话框样式 */
:deep(.ios-dialog) {
  border-radius: 14px;
  overflow: hidden;
  max-width: 360px !important;
  margin: 15vh auto 0 !important;
}

:deep(.ios-dialog .el-dialog__header) {
  padding: 14px 16px;
  text-align: center;
  border-bottom: 1px solid rgba(60, 60, 67, 0.1);
}

:deep(.ios-dialog .el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
  color: #000;
}

:deep(.ios-dialog .el-dialog__body) {
  padding: 16px;
  max-height: 60vh;
  overflow-y: auto;
}

:deep(.ios-dialog .el-dialog__footer) {
  padding: 12px 16px;
  border-top: 1px solid rgba(60, 60, 67, 0.1);
}

:deep(.ios-dialog .el-button) {
  border-radius: 10px;
  font-size: 16px;
  padding: 10px 0;
  flex: 1;
  margin: 0 8px;
}

:deep(.ios-dialog .cancel-btn) {
  border: 1px solid rgba(60, 60, 67, 0.1);
}

:deep(.ios-dialog .confirm-btn) {
  background-color: #007aff;
  color: white;
}

/* 优化表单样式 */
.ios-form {
  padding: 0 4px;
}

:deep(.ios-form .el-form-item__label) {
  padding-bottom: 8px;
  font-size: 14px;
  color: #3c3c43;
  font-weight: 500;
}

:deep(.ios-input .el-input__inner) {
  height: 44px;
  border-radius: 10px;
  padding-left: 40px;
  border-color: #e4e4e4;
  font-size: 15px;
}

:deep(.ios-input .el-input__prefix) {
  left: 12px;
  color: #8e8e93;
}

:deep(.ios-input .el-input__icon) {
  font-size: 18px;
  line-height: 44px;
}

/* 优化性别选择器 */
.gender-selector {
  display: flex;
  gap: 12px;
  padding: 4px 0;
}

.gender-option {
  flex: 1;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
  border-radius: 10px;
  font-size: 16px;
  color: #666;
  cursor: pointer;
  transition: all 0.2s ease;
  border: 1px solid transparent;
}

.gender-option i {
  margin-right: 6px;
  font-size: 18px;
}

.gender-option.active {
  background: rgba(0, 122, 255, 0.1);
  color: #007aff;
  border-color: #007aff;
}

/* 优化对话框底部按钮 */
.dialog-footer {
  display: flex;
  gap: 12px;
  padding: 0 4px;
}

:deep(.dialog-footer .el-button) {
  flex: 1;
  margin: 0;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 10px;
}

:deep(.dialog-footer .cancel-btn) {
  border-color: #e4e4e4;
  color: #666;
}

:deep(.dialog-footer .confirm-btn) {
  background: #007aff;
  border-color: #007aff;
}

/* 添加全局样式 */
.ios-message-box {
  border-radius: 14px !important;
  overflow: hidden !important;
  width: 270px !important;
  padding: 0 !important;
}

.ios-message-box .el-message-box__header {
  padding: 16px !important;
  text-align: center !important;
  border-bottom: 1px solid #eee !important;
}

.ios-message-box .el-message-box__title {
  font-size: 17px !important;
  font-weight: 600 !important;
  color: #000 !important;
}

.ios-message-box .el-message-box__content {
  padding: 16px !important;
  text-align: center !important;
  font-size: 13px !important;
  color: #666 !important;
}

.ios-message-box .el-message-box__btns {
  padding: 0 !important;
  display: flex !important;
  border-top: 1px solid #eee !important;
}

.ios-message-box .el-message-box__btns button {
  flex: 1 !important;
  margin: 0 !important;
  border: none !important;
  height: 44px !important;
  line-height: 44px !important;
  padding: 0 !important;
  font-size: 16px !important;
  background: transparent !important;
  border-radius: 0 !important;
}

.ios-message-box .el-message-box__btns button:first-child {
  border-right: 1px solid #eee !important;
  color: #666 !important;
}

.ios-message-box .el-message-box__btns button:last-child {
  color: #ff3b30 !important;
  font-weight: 500 !important;
}

.ios-message-box .el-message-box__btns button:active {
  background-color: #f5f5f5 !important;
}

/* 修改确认框样式为底部弹出 */
.ios-action-sheet {
  position: fixed !important;
  bottom: 0 !important;
  left: 50% !important;
  transform: translateX(-50%) !important;
  width: 100% !important;
  max-width: 430px !important;
  margin: 0 auto 10px !important;
  border-radius: 14px !important;
  overflow: hidden !important;
  padding: 0 !important;
  animation: slide-up 0.3s ease-out !important;
}

@keyframes slide-up {
  from {
    transform: translate(-50%, 100%);
  }
  to {
    transform: translate(-50%, 0);
  }
}

.ios-action-sheet .el-message-box__header {
  display: none !important;
}

.ios-action-sheet .el-message-box__content {
  padding: 16px !important;
  text-align: center !important;
  font-size: 13px !important;
  color: #666 !important;
  border-radius: 14px 14px 0 0 !important;
  background: rgba(255, 255, 255, 0.95) !important;
  backdrop-filter: blur(10px) !important;
  margin-bottom: 8px !important;
}

.ios-action-sheet .el-message-box__btns {
  display: flex !important;
  flex-direction: column !important;
  padding: 0 !important;
  gap: 8px !important;
}

.ios-action-sheet .el-message-box__btns button {
  width: 100% !important;
  margin: 0 !important;
  border: none !important;
  height: 56px !important;
  font-size: 20px !important;
  font-weight: 400 !important;
  background: rgba(255, 255, 255, 0.95) !important;
  backdrop-filter: blur(10px) !important;
  border-radius: 14px !important;
}

.ios-action-sheet .el-message-box__btns button:first-child {
  order: 2 !important;
  color: #007aff !important;
}

.ios-action-sheet .el-message-box__btns button:last-child {
  order: 1 !important;
  color: #ff3b30 !important;
  font-weight: 500 !important;
}

.ios-action-sheet .el-message-box__btns button:active {
  background: rgba(255, 255, 255, 0.8) !important;
}

/* 添加遮罩层样式 */
.v-modal {
  background-color: rgba(0, 0, 0, 0.4) !important;
  backdrop-filter: blur(4px) !important;
  opacity: 1 !important;
}

/* 添加全局样式 */
.ios-message {
  min-width: 140px !important;
  padding: 10px 16px !important;
  border-radius: 10px !important;
  background: rgba(0, 0, 0, 0.8) !important;
  backdrop-filter: blur(10px) !important;
}

.ios-message .el-message__content {
  color: white !important;
  font-size: 15px !important;
  line-height: 1.4 !important;
}

.ios-message.el-message--success {
  background: rgba(52, 199, 89, 0.9) !important;
}

.ios-message.el-message--error {
  background: rgba(255, 59, 48, 0.9) !important;
}

.ios-message.el-message--info {
  background: rgba(0, 0, 0, 0.8) !important;
}

.ios-message .el-message__icon {
  display: none !important;
}
</style>
