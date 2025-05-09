<!--
 * 兜底页面
 *
 * @Author: zzz
 * @Date: 2025-03-06
-->
<template>
  <div>
    <!-- 电影网格 -->
    <div class="movie-grid">
      <div class="movie-item" 
           v-for="item in movieList" 
           :key="item.movieId"
           @click="goToMovieDetail(item.movieId)">
        <div class="poster-wrap">
          <img class="poster" :src="getMoviePoster(item)" :alt="item.movieName">
          <div class="release-date" v-if="item.releaseDate">{{formatDate(item.releaseDate)}}</div>
        </div>
        <div class="title">{{item.movieName}}</div>
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
  name: "MovieUpcoming",
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
        // 即将上映页面默认设置
        let startDate = moment().format('YYYY-MM-DD');
        let endDate = moment().add(365, "days").format('YYYY-MM-DD');
        
        let queryInfo = {
          movieCategoryId: this.movieCategoryId || 0,
          orderByColumn: this.orderByColumn,
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          startDate: startDate,
          endDate: endDate,
          isAsc: 'asc'  // 按上映日期正序排列
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
    
    getMoviePoster(movie) {
      if(!movie.moviePoster) return '';
      try {
        const posterArr = JSON.parse(movie.moviePoster);
        return this.baseUrl + posterArr[0];
      } catch(e) {
        return '';
      }
    },
    
    formatDate(dateStr) {
      return moment(dateStr).format('MM月DD日上映');
    },
    
    goToMovieDetail(id) {
      if (id) {
        this.$router.push('/movieInfo/' + id);
      }
    }
  }
}
</script>

<style scoped>
/* 电影网格 */
.movie-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  padding: 12px;
}

.movie-item {
  display: flex;
  flex-direction: column;
}

.poster-wrap {
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  aspect-ratio: 2/3;
  background-color: #e5e5ea;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.poster {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.release-date {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.7);
  color: #fff;
  padding: 4px 6px;
  font-size: 11px;
  text-align: center;
}

.title {
  font-size: 13px;
  margin-top: 6px;
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
</style>
