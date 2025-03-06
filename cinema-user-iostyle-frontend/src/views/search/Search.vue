<template>
  <div class="search-container">
    <div class="search-header">
      <div class="back-button" @click="$router.push('/welcome')">
        <i class="el-icon-arrow-left"></i>
      </div>
      
      <div class="search-input-container">
        <i class="el-icon-search"></i>
        <input 
          v-model="keyword" 
          class="search-input" 
          placeholder="搜索电影名称"
          @keyup.enter="searchInfo"
          autofocus
        />
        <i class="el-icon-close" v-if="keyword" @click="keyword = ''"></i>
      </div>
      
      <button class="search-button" @click="searchInfo">搜索</button>
    </div>
    
    <div class="tab-bar">
      <div 
        v-for="item in menuList" 
        :key="item.id" 
        :class="['tab-item', $route.path === item.path ? 'active' : '']"
        @click="$router.push(item.path)">
        {{item.name}}
      </div>
    </div>
    
    <div class="search-content">
      <router-view :keyword="keyword"></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      menuList: [
        {
          id: '1',
          name: '电影',
          path: '/search/searchMovie'
        }
      ],
      keyword: this.$route.query.kw || ''
    }
  },
  methods:{
    searchInfo(){
      if (!this.keyword.trim()) return;
      this.$router.push(this.$route.path + '?kw=' + this.keyword)
    }
  },
  watch:{
    '$route'(){
      this.keyword = this.$route.query.kw || '';
    }
  }
}
</script>

<style scoped>
.search-container {
  max-width: 390px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  border-radius: 44px;
  overflow: hidden;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

.search-header {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  background-color: #fff;
  border-bottom: 1px solid rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.back-button {
  width: 36px;
  height: 36px;
  border-radius: 18px;
  background-color: rgba(0,0,0,0.05);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 12px;
  color: #007aff;
  font-size: 18px;
}

.search-input-container {
  display: flex;
  align-items: center;
  flex: 1;
  background-color: rgba(118, 118, 128, 0.12);
  border-radius: 12px;
  padding: 0 12px;
  height: 36px;
  margin-right: 12px;
}

.search-input-container i {
  color: #8e8e93;
  font-size: 16px;
}

.search-input-container .el-icon-search {
  margin-right: 8px;
}

.search-input {
  flex: 1;
  border: none;
  background: transparent;
  font-size: 16px;
  outline: none;
  color: #000;
}

.search-button {
  background: none;
  border: none;
  color: #007aff;
  font-size: 16px;
  font-weight: 500;
  padding: 0;
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

.search-content {
  padding: 0 16px 16px;
}
</style>