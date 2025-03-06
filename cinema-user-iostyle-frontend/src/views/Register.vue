<template>
  <div class="register-container">
    <div class="header">
      <div class="back-button" @click="$router.push('/welcome')">
        <i class="el-icon-arrow-left"></i>
      </div>
      <h1 class="title">注册新用户</h1>
    </div>
    
    <div class="form-section">
      <div class="form-item">
        <label>用户名</label>
        <input type="text" v-model="registerForm.userName" placeholder="请输入用户名">
        <div class="error-message" v-if="errors.userName">{{errors.userName}}</div>
      </div>
      
      <div class="form-item">
        <label>用户密码</label>
        <input type="password" v-model="registerForm.password" placeholder="请输入密码">
        <div class="error-message" v-if="errors.password">{{errors.password}}</div>
      </div>
      
      <div class="form-item">
        <label>确认密码</label>
        <input type="password" v-model="registerForm.confirmPassword" placeholder="请再次输入密码">
        <div class="error-message" v-if="errors.confirmPassword">{{errors.confirmPassword}}</div>
      </div>
      
      <div class="form-item">
        <label>手机号码</label>
        <input type="tel" v-model="registerForm.phoneNumber" placeholder="请输入手机号码">
        <div class="error-message" v-if="errors.phoneNumber">{{errors.phoneNumber}}</div>
      </div>
      
      <div class="form-item">
        <label>性别</label>
        <div class="radio-group">
          <div class="radio-item" :class="{'active': gender === '1'}" @click="gender = '1'">
            <div class="radio-dot"></div>
            <span>男</span>
          </div>
          <div class="radio-item" :class="{'active': gender === '0'}" @click="gender = '0'">
            <div class="radio-dot"></div>
            <span>女</span>
          </div>
        </div>
      </div>
    </div>
    
    <div class="agreement-section">
      <button class="register-button" @click="register">同意以下协议并注册</button>
      <div class="agreement-link">
        <a href="javascript:void(0);">《影院注册协议》</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      gender: '1',
      registerForm: {
        userName: '',
        password: '',
        confirmPassword: '',
        phoneNumber: '',
        sex: ''
      },
      errors: {
        userName: '',
        password: '',
        confirmPassword: '',
        phoneNumber: ''
      }
    }
  },
  methods: {
    validateForm() {
      let isValid = true;
      this.errors = {
        userName: '',
        password: '',
        confirmPassword: '',
        phoneNumber: ''
      };
      
      if (!this.registerForm.userName) {
        this.errors.userName = '请输入用户名';
        isValid = false;
      } else if (this.registerForm.userName.length < 2 || this.registerForm.userName.length > 20) {
        this.errors.userName = '用户名长度在2到20个字符之间';
        isValid = false;
      }
      
      if (!this.registerForm.password) {
        this.errors.password = '请输入密码';
        isValid = false;
      } else if (this.registerForm.password.length < 6 || this.registerForm.password.length > 16) {
        this.errors.password = '密码长度在6到16个字符之间';
        isValid = false;
      }
      
      if (!this.registerForm.confirmPassword) {
        this.errors.confirmPassword = '请再次输入密码';
        isValid = false;
      } else if (this.registerForm.confirmPassword !== this.registerForm.password) {
        this.errors.confirmPassword = '两次输入密码不一致';
        isValid = false;
      }
      
      if (!this.registerForm.phoneNumber) {
        this.errors.phoneNumber = '请输入手机号码';
        isValid = false;
      } else {
        const regMobile = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
        if (!regMobile.test(this.registerForm.phoneNumber)) {
          this.errors.phoneNumber = '请输入有效的手机号码';
          isValid = false;
        }
      }
      
      return isValid;
    },
    register() {
      if (!this.validateForm()) return;
      
      this.registerForm.sex = this.gender === '1';
      axios.defaults.headers.post['Content-Type'] = 'application/json';
      
      axios.post('sysUser/register', JSON.stringify(this.registerForm))
        .then(res => {
          if (res.data.code !== 200) {
            this.$message.error(res.data.msg);
            return;
          }
          
          this.$message.success("注册成功，请登录");
          this.$router.push('/login');
        })
        .catch(err => {
          console.error('注册失败', err);
          this.$message.error('注册失败，请稍后重试');
        });
    }
  }
}
</script>

<style scoped>
.register-container {
  max-width: 390px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  padding: 16px;
  border-radius: 44px;
  overflow: hidden;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

.header {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 56px;
  margin-top: 24px;
}

.back-button {
  position: absolute;
  left: 0;
  width: 36px;
  height: 36px;
  border-radius: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0,0,0,0.05);
  color: #007aff;
  font-size: 18px;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin: 0;
}

.form-section {
  background-color: white;
  border-radius: 16px;
  padding: 24px;
  margin: 20px 0 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}

.form-item {
  margin-bottom: 24px;
}

.form-item:last-child {
  margin-bottom: 0;
}

.form-item label {
  display: block;
  font-size: 14px;
  color: #8e8e93;
  margin-bottom: 8px;
  font-weight: 500;
}

.form-item input {
  width: 100%;
  padding: 12px 6px;
  border-radius: 12px;
  border: 1px solid rgba(0,0,0,0.08);
  font-size: 16px;
  outline: none;
  background-color: #f9f9f9;
}

.radio-group {
  display: flex;
}

.radio-item {
  display: flex;
  align-items: center;
  margin-right: 24px;
  cursor: pointer;
}

.radio-dot {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  border: 1.5px solid #d1d1d6;
  margin-right: 8px;
  position: relative;
}

.radio-item.active .radio-dot {
  border-color: #007aff;
}

.radio-item.active .radio-dot:after {
  content: '';
  position: absolute;
  width: 14px;
  height: 14px;
  border-radius: 50%;
  background-color: #007aff;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.error-message {
  color: #ff3b30;
  font-size: 13px;
  margin-top: 6px;
}

.agreement-section {
  text-align: center;
  margin-bottom: 40px;
}

.register-button {
  width: 100%;
  height: 50px;
  background-color: #007aff;
  color: white;
  border: none;
  border-radius: 25px;
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 16px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.3);
}

.agreement-link {
  font-size: 13px;
}

.agreement-link a {
  color: #007aff;
  text-decoration: none;
}
</style>