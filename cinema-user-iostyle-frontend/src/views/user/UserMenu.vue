<template>
  <div class="user-menu-container">
    <div class="header">
      <div class="title">个人中心</div>
    </div>
    
    <div class="menu-tabs">
      <div 
        v-for="item in menuList" 
        :key="item.id"
        :class="['tab-item', $route.path === item.path ? 'active' : '']"
        @click="$router.push(item.path)">
        {{ item.name }}
      </div>
    </div>
    
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserMenu",
  data() {
    return {
      url: '',
      menuList: [
        {
          id: 1,
          name: '基本信息',
          path: '/user'
        },
        {
          id: 2,
          name: '我的订单',
          path: '/bill'
        }
      ]
    }
  }
}
</script>

<style scoped>
.user-menu-container {
  max-width: 390px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  border-radius: 44px;
  overflow: hidden;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

.header {
  height: 56px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  position: sticky;
  top: 0;
  z-index: 100;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 18px;
  font-weight: 600;
}

.menu-tabs {
  display: flex;
  background-color: #fff;
  border-radius: 16px;
  margin: 12px 16px;
  padding: 4px;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 8px 0;
  font-size: 14px;
  font-weight: 500;
  color: #8e8e93;
  border-radius: 12px;
}

.tab-item.active {
  background-color: #007aff;
  color: #fff;
}

.content {
  background-color: #f2f2f7;
  padding-bottom: 83px; /* 为底部导航腾出空间 */
}
</style>