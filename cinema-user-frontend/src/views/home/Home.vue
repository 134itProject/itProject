<!--
 * 首页
 *
 * @Author: ShanZhu
 * @Date: 2023-12-16
-->
<template>
  <div class="ios-container">
    <!-- 状态栏 -->
    <div class="status-bar"></div>
    
    <!-- 标题栏 -->
    <div class="nav-bar">
      <div class="title">电影院</div>
      <div class="right-action">
        <i class="el-icon-search" @click="$router.push('/search/searchMovie')"></i>
      </div>
    </div>
    
    <!-- 搜索栏 -->
    <div class="search-bar" @click="$router.push('/search/searchMovie')">
      <i class="el-icon-search"></i>
      <span class="placeholder">搜索电影</span>
    </div>
    
    <!-- 内容区域 - 使用滚动容器 -->
    <div class="content-container">
      <!-- 热映中电影 -->
      <div class="section">
        <div class="section-header">
          <span class="section-title">
            <i class="el-icon-video-play"></i> 热映中
          </span>
          <span class="section-more" @click="$router.push('/movie/movieOngoing')">查看全部</span>
        </div>
        
        <div class="movie-scroll">
          <div class="movie-card" v-for="(item, index) in ongoingMovieList" :key="index" @click="goToMovieDetail(item.movieId)">
            <div class="movie-poster">
              <img :src="getMoviePoster(item)" alt="电影海报">
            </div>
            <div class="movie-name">{{ item.movieName }}</div>
            <div class="movie-score" v-if="item.movieScore">
              <span>{{ item.movieScore }}</span> 分
            </div>
          </div>
        </div>
      </div>
      
      <!-- 即将上映 -->
      <div class="section">
        <div class="section-header">
          <span class="section-title">
            <i class="el-icon-date"></i> 即将上映
          </span>
          <span class="section-more" @click="$router.push('/movie/movieUpcoming')">查看全部</span>
        </div>
        
        <div class="movie-scroll">
          <div class="movie-card" v-for="(item, index) in upcomingMovieList" :key="index" @click="goToMovieDetail(item.movieId)">
            <div class="movie-poster">
              <img :src="getMoviePoster(item)" alt="电影海报">
            </div>
            <div class="movie-name">{{ item.movieName }}</div>
            <div class="release-date" v-if="item.releaseDate">
              {{ formatDate(item.releaseDate) }} 上映
            </div>
          </div>
        </div>
      </div>
      
      <!-- 热播影片 -->
      <div class="section">
        <div class="section-header">
          <span class="section-title">
            <i class="el-icon-star-on"></i> 热播影片
          </span>
          <span class="section-more" @click="$router.push('/movie/movieClassics')">查看全部</span>
        </div>
        
        <div class="movie-scroll">
          <div class="movie-card" v-for="(item, index) in classicMovieList" :key="index" @click="goToMovieDetail(item.movieId)">
            <div class="movie-poster">
              <img :src="getMoviePoster(item)" alt="电影海报">
            </div>
            <div class="movie-name">{{ item.movieName }}</div>
            <div class="movie-score" v-if="item.movieScore">
              <span>{{ item.movieScore }}</span> 分
            </div>
          </div>
        </div>
      </div>
      
      <!-- 票房榜 -->
      <div class="section ranking-section">
        <div class="section-header">
          <span class="section-title">
            <i class="el-icon-trophy"></i> 票房榜
          </span>
          <span class="section-more" @click="$router.push('/rankingList/totalBoxOfficeList')">完整榜单</span>
        </div>
        
        <div class="ranking-list">
          <div class="ranking-item" v-for="(item, index) in totalBoxOfficeList.slice(0, 5)" :key="index" @click="goToMovieDetail(item.movieId)">
            <div class="ranking-index" :class="{'top-three': index < 3}">{{ index + 1 }}</div>
            <div class="ranking-info">
              <div class="ranking-name">{{ item.movieName }}</div>
              <div class="ranking-box-office" v-if="item.movieBoxOffice">
                {{ formatBoxOffice(item.movieBoxOffice) }}
              </div>
            </div>
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 底部导航栏 -->
    <div class="tab-bar">
      <div class="tab-item active" @click="refreshHome">
        <i class="el-icon-s-home"></i>
        <span>首页</span>
      </div>
      <div class="tab-item" @click="$router.push('/movie')">
        <i class="el-icon-film"></i>
        <span>影片</span>
      </div>
      <div class="tab-item" @click="$router.push('/rankingList')">
        <i class="el-icon-trophy"></i>
        <span>榜单</span>
      </div>
      <div class="tab-item" @click="$router.push('/user')">
        <i class="el-icon-user"></i>
        <span>我的</span>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: "Home",
  data() {
    return {
      queryInfo1: {
        total: 0,
        pageSize: 6,
        pageNum: 1,
        startDate: moment().subtract(365, "days").format("YYYY-MM-DD"),
        endDate: moment().format('YYYY-MM-DD'),
      },
      queryInfo2: {
        total: 0,
        pageSize: 6,
        pageNum: 1,
        startDate: moment().format('YYYY-MM-DD')
      },
      queryInfo3: {
        total: 0,
        pageSize: 6,
        pageNum: 1
      },
      queryInfo4:{
        pageSize: 10,
        pageNum: 1
      },
      ongoingMovieList: [],
      upcomingMovieList: [],
      classicMovieList: [],
      totalBoxOfficeList: [],
      baseUrl: '',
      defaultPosterImg: 'data:image/jpeg;base64,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'
    }
  },
  created() {
    // 设置基础URL
    this.baseUrl = this.global?.base || '';
    
    // 获取数据
    this.getOngoingMovieList()
    this.getUpcomingMovieList()
    this.getClassicMovieList()
    this.getTotalBoxOfficeList()
    
    // 记录当前页面访问
    console.log('Home页面已加载')
  },
  methods:{
    async getOngoingMovieList() {
      try {
        const { data : res } = await axios.get('sysMovie/find', {params: this.queryInfo1})
        if(res.code === 200) {
          this.ongoingMovieList = res.data
          console.log('热映电影加载成功:', this.ongoingMovieList.length)
        } else {
          this.$message.error('获取热映电影失败')
        }
      } catch(err) {
        console.error('获取热映电影异常', err)
        this.$message.error('获取热映电影失败')
      }
    },
    
    async getUpcomingMovieList() {
      try {
        const {data: res} = await axios.get('sysMovie/find', {params: this.queryInfo2})
        if(res.code === 200) {
          this.upcomingMovieList = res.data
          console.log('即将上映电影加载成功:', this.upcomingMovieList.length)
        } else {
          this.$message.error('获取即将上映电影失败')
        }
      } catch(err) {
        console.error('获取即将上映电影异常', err)
        this.$message.error('获取即将上映电影失败')
      }
    },
    
    async getClassicMovieList() {
      try {
        const { data : res } = await axios.get('sysMovie/find', {params: this.queryInfo3})
        if(res.code === 200) {
          this.classicMovieList = res.data
          console.log('热播电影加载成功:', this.classicMovieList.length)
        } else {
          this.$message.error('获取热播电影失败')
        }
      } catch(err) {
        console.error('获取热播电影异常', err)
        this.$message.error('获取热播电影失败')
      }
    },
    
    async getTotalBoxOfficeList(){
      try {
        const {data: resp} = await axios.get('sysMovie/find/rankingList/1', {params: this.queryInfo4})
        if(resp.code === 200) {
          this.totalBoxOfficeList = resp.data
          console.log('票房榜加载成功:', this.totalBoxOfficeList.length)
        } else {
          this.$message.error(resp.msg || '获取票房榜失败')
        }
      } catch(err) {
        console.error('获取票房榜异常', err)
        this.$message.error('获取票房榜失败')
      }
    },
    
    // 获取电影海报
    getMoviePoster(movie) {
      if(!movie.moviePoster) return this.defaultPosterImg;
      try {
        const posterArr = JSON.parse(movie.moviePoster)
        return this.baseUrl + posterArr[0]
      } catch(e) {
        return this.defaultPosterImg;
      }
    },
    
    // 格式化日期
    formatDate(dateStr) {
      return moment(dateStr).format('MM月DD日')
    },
    
    // 格式化票房（将数字转为更易读的形式，例如：1.2亿）
    formatBoxOffice(boxOffice) {
      const num = parseFloat(boxOffice)
      if (isNaN(num)) return boxOffice
      
      if (num >= 100000000) {
        return (num / 100000000).toFixed(1) + '亿'
      } else if (num >= 10000) {
        return (num / 10000).toFixed(1) + '万'
      } else {
        return num.toString()
      }
    },
    
    // 刷新首页
    refreshHome() {
      this.getOngoingMovieList()
      this.getUpcomingMovieList()
      this.getClassicMovieList()
      this.getTotalBoxOfficeList()
    },
    
    // 跳转到电影详情
    goToMovieDetail(id) {
      console.log('跳转到电影详情页，ID:', id)
      if (id) {
        this.$router.push('/movieInfo/' + id)
      }
    }
  }
}
</script>

<style scoped>
.ios-container {
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
  max-width: 430px;
  margin: 0 auto;
  min-height: 100vh;
  background-color: #f2f2f7;
  position: relative;
  padding-bottom: 83px; /* 为底部导航腾出空间 */
}

.status-bar {
  height: 20px;
  background-color: #f2f2f7;
}

.nav-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 44px;
  padding: 0 16px;
  background-color: #f2f2f7;
}

.title {
  font-size: 17px;
  font-weight: 600;
  color: #000;
}

.right-action {
  font-size: 22px;
  color: #007aff;
}

.search-bar {
  margin: 8px 16px;
  padding: 8px 16px;
  background-color: rgba(142, 142, 147, 0.12);
  border-radius: 10px;
  display: flex;
  align-items: center;
  color: #8e8e93;
}

.search-bar i {
  margin-right: 8px;
}

.placeholder {
  font-size: 17px;
}

.content-container {
  padding: 16px;
  overflow-y: auto;
  padding-bottom: 20px;
}

.section {
  margin-bottom: 24px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  color: #000;
  display: flex;
  align-items: center;
}

.section-title i {
  margin-right: 4px;
  color: #007aff;
}

.section-more {
  font-size: 14px;
  color: #007aff;
}

.movie-scroll {
  display: flex;
  overflow-x: auto;
  padding-bottom: 10px;
  margin: 0 -8px;
  -webkit-overflow-scrolling: touch; /* 使iOS滚动更流畅 */
}

.movie-card {
  flex: 0 0 auto;
  width: 100px;
  margin: 0 8px;
}

.movie-poster {
  width: 100px;
  height: 150px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 8px;
  background-color: #d1d1d6;
  display: flex;
  justify-content: center;
  align-items: center;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-poster:empty::after {
  content: "暂无图片";
  color: #8e8e93;
  font-size: 12px;
}

.movie-name {
  font-size: 14px;
  font-weight: 500;
  color: #000;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.movie-score {
  font-size: 12px;
  color: #ff9500;
}

.movie-score span {
  font-weight: 600;
}

.release-date {
  font-size: 12px;
  color: #8e8e93;
}

.ranking-section {
  background-color: white;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.ranking-list {
  display: flex;
  flex-direction: column;
}

.ranking-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid rgba(60, 60, 67, 0.1);
}

.ranking-item:last-child {
  border-bottom: none;
}

.ranking-index {
  width: 24px;
  height: 24px;
  border-radius: 12px;
  background-color: rgba(60, 60, 67, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 14px;
  font-weight: 600;
  margin-right: 12px;
}

.ranking-index.top-three {
  background-color: #ff9500;
  color: white;
}

.ranking-info {
  flex: 1;
}

.ranking-name {
  font-size: 16px;
  font-weight: 500;
  color: #000;
  margin-bottom: 4px;
}

.ranking-box-office {
  font-size: 14px;
  color: #8e8e93;
}

.tab-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  max-width: 430px;
  margin: 0 auto;
  height: 83px;
  background-color: rgba(255, 255, 255, 0.95);
  display: flex;
  justify-content: space-around;
  border-top: 1px solid rgba(60, 60, 67, 0.1);
  padding-bottom: env(safe-area-inset-bottom);
}

.tab-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px 0;
  color: #8e8e93;
  font-size: 10px;
}

.tab-item i {
  font-size: 22px;
  margin-bottom: 4px;
}

.tab-item.active {
  color: #007aff;
}

/* 滚动条样式优化 */
.movie-scroll::-webkit-scrollbar {
  display: none;
}
</style>
