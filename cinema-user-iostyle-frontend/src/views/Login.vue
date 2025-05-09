<template>
  <div class="login-container">
    <div class="back-button" @click="$router.push('/welcome')">
      <i class="el-icon-arrow-left"></i>
    </div>
    
    <div class="login-header">
      <div class="login-logo">
        <i class="iconfont icon-login-title"></i>
      </div>
      <h1 class="login-title">登录影院用户端</h1>
      <p class="login-subtitle">欢迎回来，请登录您的账号</p>
    </div>
    
    <div class="login-form">
      <div class="form-item">
        <div class="input-wrapper">
          <i class="el-icon-user"></i>
          <input 
            v-model="loginForm.userName"
            placeholder="请输入用户名"
            type="text"
          />
        </div>
        <div class="error-message" v-if="formErrors.userName">{{formErrors.userName}}</div>
      </div>
      
      <div class="form-item">
        <div class="input-wrapper">
          <i class="el-icon-lock"></i>
          <input 
            v-model="loginForm.password"
            placeholder="请输入密码"
            type="password"
          />
        </div>
        <div class="error-message" v-if="formErrors.password">{{formErrors.password}}</div>
      </div>
      
      <div class="button-group">
        <button class="login-button" @click="login">登录</button>
        <button class="register-button" @click="registerAccount">注册新用户</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        userName: "",
        password: "",
      },
      formErrors: {
        userName: '',
        password: ''
      },
      sessionId: 0,
    };
  },
  created() {
    this.sessionId = window.sessionStorage.getItem("sessionId");
    console.log("this sessionId is : " + this.sessionId);
    window.sessionStorage.setItem("sessionId", 0);
  },
  methods: {
    success(params) {
      this.login();
    },
    validateForm() {
      let isValid = true;
      this.formErrors = {
        userName: '',
        password: ''
      };
      
      if (!this.loginForm.userName) {
        this.formErrors.userName = '请输入用户名';
        isValid = false;
      } else if (this.loginForm.userName.length < 2 || this.loginForm.userName.length > 20) {
        this.formErrors.userName = '用户名长度在2到20个字符之间';
        isValid = false;
      }
      
      if (!this.loginForm.password) {
        this.formErrors.password = '请输入密码';
        isValid = false;
      } else if (this.loginForm.password.length < 6 || this.loginForm.password.length > 16) {
        this.formErrors.password = '密码长度在6到16个字符之间';
        isValid = false;
      }
      
      return isValid;
    },
    login() {
      if (!this.validateForm()) return;
      
      axios.defaults.headers.post["Content-Type"] = "application/json";
      axios.post("sysUser/login", JSON.stringify(this.loginForm))
        .then(res => {
          if (res.data.code !== 200) {
            this.$message.error(res.data.msg);
            return;
          }
          
          this.$message.success({ message: "登录成功", duration: 1000 });
          
          // 保存token
          window.sessionStorage.setItem("token", res.data.data.token);
          res.data.data.sysUser.sysRole = null;
          window.sessionStorage.setItem("loginUser", JSON.stringify(res.data.data.sysUser));
          
          if (this.sessionId !== 0 && this.sessionId !== "0" && this.sessionId !== null) {
            this.$router.push("/chooseSeat/" + this.sessionId);
            return;
          }
          
          // 导航跳转到首页
          this.$router.push("/welcome");
        })
        .catch(err => {
          console.error('登录失败', err);
          this.$message.error('登录失败，请检查网络连接');
        });
    },
    registerAccount() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped>
.login-container {
  max-width: 390px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  border-radius: 44px;
  overflow: hidden;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

.back-button {
  position: absolute;
  top: 16px;
  left: 16px;
  width: 36px;
  height: 36px;
  border-radius: 18px;
  background-color: rgba(0,0,0,0.05);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
  color: #007aff;
}

.login-header {
  margin-top: 80px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 0;
}

.login-logo {
  width: 90px;
  height: 90px;
  border-radius: 26px;
  background-color: #f2f2f7;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 24px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.login-logo i {
  font-size: 46px;
  color: #007aff;
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #000;
  margin-bottom: 8px;
}

.login-subtitle {
  font-size: 16px;
  color: #8e8e93;
}

.login-form {
  margin: 20px 24px 0;
}

.form-item {
  margin-bottom: 24px;
}

.input-wrapper {
  display: flex;
  align-items: center;
  height: 50px;
  background-color: #fff;
  border-radius: 12px;
  padding: 0 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}

.input-wrapper i {
  font-size: 20px;
  color: #8e8e93;
  margin-right: 12px;
}

.input-wrapper input {
  flex: 1;
  border: none;
  font-size: 16px;
  outline: none;
  background: transparent;
}

.error-message {
  font-size: 13px;
  color: #ff3b30;
  margin-top: 8px;
  padding-left: 16px;
}

.button-group {
  margin-top: 40px;
}

.login-button {
  width: 100%;
  height: 50px;
  border-radius: 25px;
  background-color: #007aff;
  color: white;
  font-size: 16px;
  font-weight: 600;
  border: none;
  margin-bottom: 16px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.3);
}

.register-button {
  width: 100%;
  height: 50px;
  border-radius: 25px;
  background-color: rgba(0, 122, 255, 0.1);
  color: #007aff;
  font-size: 16px;
  font-weight: 500;
  border: none;
}
</style>