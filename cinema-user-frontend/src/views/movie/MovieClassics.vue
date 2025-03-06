<!--
 * 电影搜索兜底页面
 *
 * @Author: zzz
 * @Date: 2025-03-06
-->
<template>
  <div>
    <!-- 电影网格 - 修改为flex布局而非grid -->
    <div class="movie-container">
      <div class="movie-item" 
           v-for="(item, index) in movieList" 
           :key="item.movieId"
           @click="goToMovieDetail(item.movieId)">
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
import movieItem from "../../components/movie/movie-item";
import moment from "moment";

export default {
  name: "MovieClassics",
  components:{
    movieItem
  },
  props: {
    movieCategoryId: {
      type: Number,
      default: 0
    },
    orderByColumn: {
      type: String,
      default: 'movie_score'  // 热播电影默认按评分排序
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
  computed: {
    listenChange () {
      const {movieCategoryId, orderByColumn} = this
      return {movieCategoryId, orderByColumn}
    }
  },
  created() {
    this.baseUrl = this.global?.base || '';
    this.getMovieList()
  },
  watch:{
    listenChange(){
      this.getMovieList()
    },
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
        let queryInfo = {
          movieCategoryId: this.movieCategoryId || 0,
          orderByColumn: this.orderByColumn,
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          isAsc: 'desc'
        }
        
        console.log('获取电影列表，参数:', queryInfo);
        const { data : res } = await axios.get('sysMovie/find', {params: queryInfo})
        
        if (res.code === 200) {
          // 处理数据确保所有项都有完整的属性
          const processedData = res.data.map(item => {
            // 确保每个电影对象至少有基本属性
            return {
              ...item,
              movieName: item.movieName || '未知电影',
              movieScore: item.movieScore || null,
              moviePoster: item.moviePoster || null
            };
          });
          
          if (this.pageNum === 1) {
            this.movieList = processedData;
          } else {
            this.movieList = [...this.movieList, ...processedData];
          }
          
          this.total = res.total;
          this.sorry = this.movieList.length === 0;
          console.log('获取到电影数据:', this.movieList.length);
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
    
    goToMovieDetail(id) {
      if (id) {
        this.$router.push('/movieInfo/' + id);
      }
    },
    
    // 改进handleImageError，添加完整的base64图片
    handleImageError(e) {
      e.target.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAADeAQMAAAD9kgZ5AAAABlBMVEXd3d3///+uPjS6AAAAAnRSTlP/AOW3MEoAAACASURBVFjD7dWxDYAwDETRi5ERvP+QZAeyQUtngZSKhq/flJx0Es/ct1OffcTxoyasA1wB+Bx8LgAgwHtAtQuQYPeAu2s9iDCPQHULQYR5BFKyGgFgEfAMlgCLQPNSDwLMAc0hNQLAHKAyaAGWgD6DFmAJ6B/UIMIcMDKYAkwCvw/UF6f1qJ98AAAAAElFTkSuQmCC';
    }
  }
}
</script>

<style scoped>
/* 修改为flex布局而非grid */
.movie-container {
  display: flex;
  flex-wrap: wrap;
  padding: 12px;
  margin: 0 -6px; /* 负边距补偿item的margin */
}

.movie-item {
  width: calc(33.33% - 12px);
  margin: 0 6px 16px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.poster-wrap {
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding-bottom: 150%; /* 2:3比例 */
  background-color: #e5e5ea;
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
  height: 20px;
  margin-top: 8px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  width: 100%;
}

/* 为较小的屏幕优化 */
@media screen and (max-width: 400px) {
  .movie-item {
    width: calc(50% - 12px); /* 更小屏幕上每行2个 */
  }
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
