<template>
  <div>
    <!-- 电影网格 -->
    <div class="movie-grid">
      <div class="movie-item" 
           v-for="(item, index) in movieList" 
           :key="item.movieId"
           @click="goToMovieDetail(item.movieId)"
           :style="{'grid-column': getGridPosition(index)}">
        <div class="poster-wrap">
          <img class="poster" 
               :src="getMoviePoster(item)" 
               :alt="item.movieName"
               @error="handleImageError">
          <div class="score" v-if="item.movieScore">{{item.movieScore}}</div>
        </div>
        <div class="title">{{item.movieName || '未知电影'}}</div>
      </div>
    </div>
    
    <!-- 无结果提示 -->
    <div class="empty-state" v-if="sorry">
      <i class="el-icon-warning-outline"></i>
      <p>没有找到相关电影</p>
    </div>
    
    <!-- 加载更多 -->
    <div class="load-more" v-if="total > pageSize * pageNum && !sorry" @click="loadMore">
      <span>加载更多</span>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: "MovieOngoing",
  props: {
    movieCategoryId: {
      type: Number,
      default: 0
    },
    orderByColumn: {
      type: String,
      default: 'release_date'
    }
  },
  data() {
    return {
      total: 0,
      pageSize: 12,
      pageNum: 1,
      movieList: [],
      sorry: false,
      baseUrl: ''
    }
  },
  created() {
    this.baseUrl = this.global?.base || '';
    this.getMovieList()
  },
  watch: {
    movieCategoryId() {
      this.resetList();
      this.getMovieList();
    }
  },
  methods: {
    resetList() {
      this.pageNum = 1;
      this.movieList = [];
    },
    
    async getMovieList() {
      try {
        // 正在热映页面默认设置
        let startDate = moment().subtract(365, "days").format("YYYY-MM-DD");
        let endDate = moment().format('YYYY-MM-DD');
        
        let queryInfo = {
          movieCategoryId: this.movieCategoryId || 0,
          orderByColumn: this.orderByColumn,
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          startDate: startDate,
          endDate: endDate,
          isAsc: 'desc'
        }
        
        const { data : res } = await axios.get('sysMovie/find', {params: queryInfo})
        
        if (res.code === 200) {
          if (this.pageNum === 1) {
            this.movieList = res.data;
          } else {
            this.movieList = [...this.movieList, ...res.data];
          }
          
          this.total = res.total;
          this.sorry = this.movieList.length === 0;
        } else {
          this.$message.error('获取电影列表失败');
          this.sorry = true;
        }
      } catch (err) {
        console.error('获取电影列表出错:', err);
        this.$message.error('获取电影列表失败');
        this.sorry = true;
      }
    },
    
    loadMore() {
      this.pageNum++;
      this.getMovieList();
    },
    
    handleImageError(e) {
      e.target.src = require('@/assets/default-poster.png');
    },
    
    getMoviePoster(movie) {
      if(!movie || !movie.moviePoster) {
        return require('@/assets/default-poster.png');
      }
      
      try {
        const posterArr = JSON.parse(movie.moviePoster);
        if (posterArr && posterArr.length > 0 && posterArr[0]) {
          return this.baseUrl + posterArr[0] + '?t=' + new Date().getTime();
        }
        return require('@/assets/default-poster.png');
      } catch(e) {
        console.error('海报解析错误:', e);
        return require('@/assets/default-poster.png');
      }
    },
    
    goToMovieDetail(id) {
      if (id) {
        this.$router.push('/movieInfo/' + id);
      }
    },
    
    getGridPosition(index) {
      const column = (index % 3) + 1;
      return column;
    }
  }
}
</script>

<style scoped>
/* 电影网格 */
.movie-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 12px;
  padding: 12px;
}

.movie-item {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.poster-wrap {
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  width: 100%;
  height: 0;
  padding-top: 150%; /* 维持2:3的宽高比 */
  background-color: #e5e5ea;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.poster {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.score {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: #ff9500;
  color: #fff;
  padding: 3px 6px;
  font-size: 12px;
  font-weight: 600;
  border-top-left-radius: 6px;
}

.title {
  font-size: 13px;
  margin-top: 6px;
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
}

/* 无结果状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 16px;
  color: #8e8e93;
}

.empty-state i {
  font-size: 42px;
  margin-bottom: 12px;
}

.empty-state p {
  font-size: 15px;
  margin: 0;
}

/* 加载更多按钮 */
.load-more {
  text-align: center;
  padding: 16px;
  margin: 12px 16px;
  background-color: #fff;
  border-radius: 10px;
  color: #007aff;
  font-size: 15px;
  font-weight: 500;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}

/* 为底部导航添加样式 */
.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  max-width: 430px;
  margin: 0 auto;
  background-color: #fff;
  display: flex;
  height: 55px;
  border-top: 1px solid rgba(0,0,0,0.1);
  z-index: 100;
}

.nav-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #8e8e93;
  font-size: 10px;
}

.nav-item i {
  font-size: 22px;
  margin-bottom: 2px;
}

.nav-item.active {
  color: #007aff;
}
</style>