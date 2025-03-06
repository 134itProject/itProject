<template>
  <div class="bottom-nav">
    <div class="nav-item" :class="{ active: currentPath === '/home' }" @click="navigateTo('/home')">
      <i class="nav-icon el-icon-s-home"></i>
      <span class="nav-text">首页</span>
    </div>
    
    <div class="nav-item" :class="{ active: currentPath === '/movie' }" @click="navigateTo('/movie')">
      <i class="nav-icon el-icon-film"></i>
      <span class="nav-text">电影</span>
    </div>
    
    <div class="nav-item" :class="{ active: currentPath === '/rankingList' }" @click="navigateTo('/rankingList')">
      <i class="nav-icon el-icon-trophy"></i>
      <span class="nav-text">榜单</span>
    </div>
    
    <div class="nav-item" :class="{ active: currentPath === '/search' }" @click="navigateTo('/search')">
      <i class="nav-icon el-icon-search"></i>
      <span class="nav-text">搜索</span>
    </div>
    
    <div class="nav-item" :class="{ active: currentPath === '/user' }" @click="navigateTo('/user')">
      <i class="nav-icon el-icon-user"></i>
      <span class="nav-text">我的</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BottomNavigation',
  computed: {
    currentPath() {
      return this.$route.path;
    }
  },
  methods: {
    navigateTo(path) {
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    }
  }
}
</script>

<style scoped>
.bottom-nav {
  position: fixed;
  bottom: 32px;
  left: 50%;
  transform: translateX(-50%);
  width: 360px;
  height: 72px;
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-radius: 36px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
  z-index: 1000;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 8px 16px;
  border-radius: 20px;
}

.nav-item.active {
  background-color: rgba(0, 122, 255, 0.1);
}

.nav-item.active .nav-icon,
.nav-item.active .nav-text {
  color: #007aff;
}

.nav-icon {
  font-size: 22px;
  margin-bottom: 4px;
  color: #8e8e93;
}

.nav-text {
  font-size: 10px;
  color: #8e8e93;
}
</style> 