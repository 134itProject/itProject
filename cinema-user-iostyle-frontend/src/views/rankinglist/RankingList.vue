<template>
  <div class="ranking-container">
    <div class="header">
      <div class="back-button" @click="$router.push('/welcome')">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">票房榜单</div>
    </div>
    
    <div class="tab-bar">
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
  data() {
    return {
      menuList: [
        {
          id: 1,
          name: "总票房榜",
          path: "/rankingList/totalBoxOfficeList",
          icon: "el-icon-trophy"
        },
        {
          id: 2,
          name: "国内票房榜",
          path: "/rankingList/domesticBoxOfficeList",
          icon: "el-icon-position"
        },
        {
          id: 3,
          name: "国外票房榜",
          path: "/rankingList/foreignBoxOfficeList",
          icon: "el-icon-location"
        },
      ],
    };
  }
};
</script>

<style scoped>
.ranking-container {
  max-width: 390px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  padding-bottom: 83px; /* 为底部导航腾出空间 */
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
  background-color: #fff;
  position: sticky;
  top: 0;
  z-index: 100;
  border-bottom: 1px solid rgba(0,0,0,0.1);
}

.back-button {
  position: absolute;
  left: 16px;
  width: 36px;
  height: 36px;
  border-radius: 18px;
  background-color: rgba(0,0,0,0.05);
  display: flex;
  justify-content: center;
  align-items: center;
  color: #007aff;
  font-size: 18px;
}

.title {
  font-size: 18px;
  font-weight: 600;
}

.tab-bar {
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
  padding: 0 16px;
}
</style>