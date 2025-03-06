<!--
 * 电影列表
 *
 * @Author: ShanZhu
 * @Date: 2023-12-16
-->
<template>
  <div class="ios-container">
    <!-- 顶部标题栏 -->
    <div class="nav-bar">
      <div class="back-button" @click="$router.push('/home')">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">电影</div>
      <div class="right-action">
        <i class="el-icon-search" @click="$router.push('/search/searchMovie')"></i>
      </div>
    </div>
    
    <!-- 顶部选项卡 - 使用路由而非内部状态 -->
    <div class="tab-header">
      <div class="tab-item" 
           :class="{active: $route.path === '/movie/movieOngoing'}" 
           @click="$router.push('/movie/movieOngoing')">
        正在热映
      </div>
      <div class="tab-item" 
           :class="{active: $route.path === '/movie/movieUpcoming'}" 
           @click="$router.push('/movie/movieUpcoming')">
        即将上映
      </div>
      <div class="tab-item" 
           :class="{active: $route.path === '/movie/movieClassics'}" 
           @click="$router.push('/movie/movieClassics')">
        热播影片
      </div>
    </div>
    
    <!-- 类别选择区 -->
    <div class="filter-bar">
      <div class="category-scroll">
        <div class="category-pill" 
             :class="{active: categoryRadio === 0}" 
             @click="updateCategory(0)">
          全部
        </div>
        <div class="category-pill" 
             v-for="item in categoryList" 
             :key="item.movieCategoryId"
             :class="{active: categoryRadio === item.movieCategoryId}"
             @click="updateCategory(item.movieCategoryId)">
          {{item.movieCategoryName}}
        </div>
      </div>
    </div>
    
    <!-- 路由视图 -->
    <router-view
      :movieCategoryId="categoryRadio"
      :orderByColumn="orderByColumn">
    </router-view>
  </div>
</template>

<script>
export default {
  name: "Movie",
  data() {
    return {
      categoryRadio: 0,
      categoryList: [],
      orderByColumn: "",  // 让子组件根据当前路由自行决定排序方式
    };
  },
  created() {
    this.getCategoryList();
  },
  methods: {
    async getCategoryList() {
      try {
        const { data: res } = await axios.get("sysMovieCategory/find");
        if (res.code === 200) {
          this.categoryList = res.data;
        } else {
          this.$message.error("获取分类列表失败");
        }
      } catch (err) {
        console.error("获取分类列表错误:", err);
        this.$message.error("获取分类列表失败");
      }
    },
    updateCategory(id) {
      this.categoryRadio = id;
    }
  },
  watch: {
    $route() {
      //页面路由变化时，重置分类选择
      this.categoryRadio = 0;
    }
  }
};
</script>

<style scoped>
.ios-container {
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  max-width: 430px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  padding-bottom: 40px;
}

/* 顶部导航栏 */
.nav-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 44px;
  padding: 0 16px;
  background-color: #f2f2f7;
  position: sticky;
  top: 0;
  z-index: 101;
}

.back-button {
  font-size: 18px;
  color: #007aff;
  padding: 8px;
  margin-left: -8px;
}

.title {
  font-size: 17px;
  font-weight: 600;
  color: #000;
}

.right-action {
  font-size: 18px;
  color: #007aff;
  padding: 8px;
  margin-right: -8px;
}

/* 顶部选项卡 */
.tab-header {
  display: flex;
  background-color: #fff;
  border-bottom: 1px solid rgba(60, 60, 67, 0.1);
  position: sticky;
  top: 44px;
  z-index: 100;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 12px 0;
  font-size: 15px;
  color: #8e8e93;
  position: relative;
}

.tab-item.active {
  color: #007aff;
  font-weight: 500;
}

.tab-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 25%;
  width: 50%;
  height: 2px;
  background-color: #007aff;
  border-radius: 1px;
}

/* 分类筛选条 */
.filter-bar {
  padding: 12px 0;
  background-color: #fff;
  margin-bottom: 8px;
}

.category-scroll {
  display: flex;
  overflow-x: auto;
  padding: 0 12px;
  -webkit-overflow-scrolling: touch;
}

.category-scroll::-webkit-scrollbar {
  display: none;
}

.category-pill {
  flex: 0 0 auto;
  padding: 6px 16px;
  margin-right: 8px;
  border-radius: 16px;
  font-size: 14px;
  background-color: rgba(142, 142, 147, 0.12);
  color: #000;
  white-space: nowrap;
}

.category-pill.active {
  background-color: #007aff;
  color: #fff;
}
</style>
