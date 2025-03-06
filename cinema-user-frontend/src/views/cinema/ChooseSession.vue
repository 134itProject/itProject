<template>
  <div class="ios-container">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <div class="back-button" @click="$router.go(-1)">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">选择场次</div>
      <div class="right-action">
        <i class="el-icon-share"></i>
      </div>
    </div>
    
    <!-- 影院信息卡片 -->
    <div class="cinema-card">
      <div class="cinema-header">
        <div class="cinema-image">
          <img 
            :src="cinemaInfo.cinemaPicture && cinemaInfo.cinemaPicture.length ? cinemaInfo.cinemaPicture[0] : ''" 
            @error="handleImageError"
            alt="影院图片">
        </div>
        <div class="cinema-basic-info">
          <h1 class="cinema-name">{{ cinemaInfo.cinemaName || '未知影院' }}</h1>
          <div class="cinema-address">
            <i class="el-icon-location-outline"></i>
            <span>{{ cinemaInfo.cinemaAddress || '地址未知' }}</span>
          </div>
          <div class="cinema-phone" @click="callCinema(cinemaInfo.cinemaPhone)">
            <i class="el-icon-phone-outline"></i>
            <span>{{ cinemaInfo.cinemaPhone || '电话未知' }}</span>
          </div>
        </div>
      </div>
      
      <!-- 影院服务标签 -->
      <div class="cinema-services">
        <div class="service-tag">
          <span class="tag-icon">P</span>
          <span class="tag-text">免费停车</span>
        </div>
        <div class="service-tag">
          <span class="tag-icon">饮</span>
          <span class="tag-text">免费饮料</span>
        </div>
      </div>
    </div>
    
    <!-- 电影选择滚动栏 -->
    <div class="movie-selector" v-if="cinemaInfo.sysMovieList && cinemaInfo.sysMovieList.length">
      <div class="scroll-container">
        <div class="movie-items">
          <div 
            v-for="movie in cinemaInfo.sysMovieList" 
            :key="movie.movieId"
            class="movie-item"
            :class="{ active: movie.movieId === activeMovie }"
            @click="activeMovie = movie.movieId">
            <div class="movie-poster">
              <img :src="getMoviePoster(movie)" @error="handleImageError">
            </div>
            <div class="movie-name">{{ movie.movieName }}</div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 当前电影信息 -->
    <div class="current-movie-info" v-if="movieDict[activeMovie]">
      <h2 class="movie-title">{{ movieDict[activeMovie].movieName }}</h2>
      <div class="movie-meta">
        <span class="movie-length">{{ movieDict[activeMovie].movieLength }}分钟</span>
        <span class="divider">|</span>
        <span class="movie-category">{{ formatCategories(movieDict[activeMovie].movieCategoryList) }}</span>
      </div>
    </div>
    
    <!-- 日期选择器 -->
    <div class="date-selector" v-if="Object.keys(sessionDict).length">
      <div class="date-label">观影日期</div>
      <div class="date-scroll">
        <div 
          v-for="(sessions, date) in sessionDict" 
          :key="date"
          class="date-item"
          :class="{ active: activeDate === date }"
          @click="activeDate = date">
          <div class="date-day">{{ formatDay(date) }}</div>
          <div class="date-date">{{ formatDate(date) }}</div>
        </div>
      </div>
    </div>
    
    <!-- 场次列表 -->
    <div class="session-list" v-if="sessionDict[activeDate] && sessionDict[activeDate].length">
      <div 
        v-for="session in sessionDict[activeDate]" 
        :key="session.sessionId"
        class="session-item"
        @click="toChooseSeat(session.sessionId)">
        <div class="session-time">
          <div class="start-time">{{ session.playTime }}</div>
          <div class="end-time">{{ session.endTime }} 散场</div>
        </div>
        <div class="session-info">
          <div class="hall-name">{{ session.sysHall.hallName }}</div>
          <div class="language">{{ session.languageVersion }}</div>
        </div>
        <div class="session-price">
          <span class="price-value">¥{{ session.sessionPrice }}</span>
        </div>
        <div class="choose-button">
          <span>选座</span>
        </div>
      </div>
    </div>
    
    <!-- 无场次提示 -->
    <div class="empty-state" v-else-if="activeDate">
      <i class="el-icon-warning-outline"></i>
      <p>当天暂无场次</p>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "ChooseSession",
  data() {
    return {
      cinemaId: this.$route.params.cinemaId,
      cinemaInfo: {
        user: {},
        cinemaPicture: [],
        sysMovieList: [],
      },
      movieDict: {
        0: {
          movieBoxOffice: 0,
          movieCategoryList: [],
          movieId: 0,
          movieIntroduction: "",
          movieLength: 0,
          movieName: "",
          moviePictures: "",
          moviePoster: "",
          releaseDate: "",
        },
      },
      sessionDict: {},
      sessions: [],
      activeMovie: 0,
      activeDate: ""
    };
  },
  watch: {
    activeMovie() {
      this.getNewSessionList();
    },
  },
  created() {
    this.getCinemaMovieList();
  },
  methods: {
    async getCinemaMovieList() {
      try {
        const { data: res } = await axios.get("sysCinema/find/" + this.cinemaId);
        
        if (res.code !== 200) {
          this.$message.error('获取影院信息失败');
          return;
        }
        
        this.cinemaInfo = res.data.cinema;
        this.sessions = res.data.sessions || [];
        
        // 解析图片
        if (this.cinemaInfo.cinemaPicture) {
          try {
            this.cinemaInfo.cinemaPicture = JSON.parse(this.cinemaInfo.cinemaPicture).map(path => {
              return this.global.base + path;
            });
          } catch (e) {
            console.error('解析影院图片出错', e);
            this.cinemaInfo.cinemaPicture = [];
          }
        }
        
        // 设置默认选中电影
        if (this.sessions.length > 0) {
          this.activeMovie = this.sessions[0].movieId;
        }

        // 处理电影字典
        if (this.cinemaInfo.sysMovieList) {
          for (let movie of this.cinemaInfo.sysMovieList) {
            if (movie.movieCategoryList) {
              movie.movieCategoryList = movie.movieCategoryList.map(cat => {
                return cat.movieCategoryName;
              });
            }
            this.movieDict[movie.movieId] = movie;
          }
        }

        // 处理场次数据，按日期分组
        this.sessionDict = {};
        for (let session of this.sessions) {
          if (this.sessionDict[session.sessionDate] === undefined) {
            this.sessionDict[session.sessionDate] = [];
          }
          this.sessionDict[session.sessionDate].push(session);
        }
        
        // 设置默认日期
        if (this.sessions.length > 0) {
          this.activeDate = this.sessions[0].sessionDate;
        }
      } catch (err) {
        console.error('获取影院信息出错', err);
        this.$message.error('获取影院信息失败');
      }
    },
    
    async getNewSessionList() {
      try {
        const { data: res } = await axios.get(
          "sysCinema/find/" + this.cinemaId + "/" + this.activeMovie
        );
        
        if (res.code !== 200) {
          this.$message.error("获取场次信息失败");
          return;
        }
        
        this.sessions = res.data.sessions || [];
        
        // 处理场次数据，按日期分组
        this.sessionDict = {};
        for (let session of this.sessions) {
          if (this.sessionDict[session.sessionDate] === undefined) {
            this.sessionDict[session.sessionDate] = [];
          }
          this.sessionDict[session.sessionDate].push(session);
        }
        
        // 设置默认日期
        if (this.sessions.length > 0) {
          this.activeDate = this.sessions[0].sessionDate;
        } else {
          this.activeDate = "";
        }
      } catch (err) {
        console.error('获取场次信息出错', err);
        this.$message.error('获取场次信息失败');
      }
    },
    
    toChooseSeat(sessionId) {
      this.$router.push("/chooseSeat/" + sessionId);
    },
    
    formatCategories(categories) {
      if (!categories) return '';
      return Array.isArray(categories) ? categories.join(' / ') : categories;
    },
    
    formatDay(dateStr) {
      if (!dateStr) return '';
      const date = moment(dateStr);
      const today = moment().startOf('day');
      const tomorrow = moment().add(1, 'days').startOf('day');
      
      if (date.isSame(today, 'day')) {
        return '今天';
      } else if (date.isSame(tomorrow, 'day')) {
        return '明天';
      } else {
        return date.format('ddd');
      }
    },
    
    formatDate(dateStr) {
      if (!dateStr) return '';
      return moment(dateStr).format('MM/DD');
    },
    
    getMoviePoster(movie) {
      if (!movie || !movie.moviePoster) return '';
      try {
        const posterArr = JSON.parse(movie.moviePoster);
        if (posterArr && posterArr.length > 0) {
          return this.global.base + posterArr[0];
        }
        return '';
      } catch (e) {
        return '';
      }
    },
    
    handleImageError(e) {
      e.target.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAABACAYAAADS1n9/AAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAJKSURBVHgB7dxNbtNQFMXxcz+atFQ0UomgbIAJYllMWA1LQIhVMGVDwJA9lBlDqJKm+fq5Pi9RMT7G9XOx/x8pVJU65N7jF7/raIoAAAAAAAAAAADgFFvdgm/v9nf3XvstlvZeauvJNZ37VY/jpd2eaW+dWrd+nG+6jcMcAGm7/3dXdjj83fuH2/v5ptvoFe5VXQC2rYc3rftbfXvUfkdoI+lxpPPuY7VePZbLAZ5KUkJ5e9P3d83mAC+fdTZLNB1/E8BoCWA0AhgtAYxGAKMlgNEIYLQEMBoBjJYARiOA0RLAaAQwWgIYjQBGSwCjEcBoCWA0AhgtAYxGAKMlgNEIYLQEMBoBjJYARiOA0RLAaAQwWgIYjQBGSwCjEcBoCWA0AhgtAYxGAKMlgNF6BXCluwA17h+RVhHA7Xy+fdR9rGWpF9rK2dV6dfu5XA7w9uXZLJb10C3KrD2Nv5VhngHCYftdH+bfa19LqbLSfDWfnK4q6+5qvXzoPxZ/JN6bZv/j4lnndaR2rT+3XjTRXbGcj39+13mHmRGS4lV3YE7/F9CHX+d9yLvf6aLTnHYAT+7Dp/3e06bcBQfQXXLxIkXO+eXnbdfrfPhnA+L1l/3n7qb9KMWliDhPR/5VYS43I8fF6UfCbvOBMLR7qfEqN2XGXr+d1cOhRnz9cntbtP1lmiJFdJcprroPFmnjF5mnS+l8udNEJOL36bWLbgMAAAAAAAAAAAAA8B/8BoXvD2XKhH/0AAAAAElFTkSuQmCC';
    },
    
    callCinema(phone) {
      if (phone) {
        window.location.href = `tel:${phone}`;
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
  padding-bottom: 20px;
}

/* 导航栏 */
.nav-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 44px;
  padding: 0 16px;
  background-color: #f2f2f7;
  position: sticky;
  top: 0;
  z-index: 100;
}

.back-button, .right-action {
  font-size: 18px;
  color: #007aff;
  padding: 8px;
  margin: -8px;
}

.title {
  font-size: 17px;
  font-weight: 600;
  color: #000;
}

/* 影院信息卡片 */
.cinema-card {
  background-color: #fff;
  border-radius: 12px;
  margin: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.cinema-header {
  display: flex;
  padding: 16px;
}

.cinema-image {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  overflow: hidden;
  margin-right: 12px;
  flex-shrink: 0;
}

.cinema-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cinema-basic-info {
  flex: 1;
}

.cinema-name {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin: 0 0 8px;
}

.cinema-address, .cinema-phone {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #8e8e93;
  margin-bottom: 4px;
}

.cinema-address i, .cinema-phone i {
  margin-right: 4px;
  font-size: 16px;
}

.cinema-phone {
  color: #007aff;
}

.cinema-services {
  display: flex;
  padding: 0 16px 16px;
  flex-wrap: wrap;
}

.service-tag {
  display: flex;
  align-items: center;
  background-color: rgba(0, 122, 255, 0.1);
  border-radius: 4px;
  padding: 4px 8px;
  margin-right: 8px;
  margin-bottom: 8px;
}

.tag-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 16px;
  height: 16px;
  border-radius: 8px;
  background-color: #007aff;
  color: white;
  font-size: 10px;
  font-weight: 600;
  margin-right: 4px;
}

.tag-text {
  font-size: 12px;
  color: #007aff;
}

/* 电影选择器 */
.movie-selector {
  margin: 16px 0;
}

.scroll-container {
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
}

.scroll-container::-webkit-scrollbar {
  display: none;
}

.movie-items {
  display: flex;
  padding: 0 16px;
}

.movie-item {
  flex: 0 0 auto;
  width: 80px;
  margin-right: 12px;
  transition: all 0.2s ease;
}

.movie-item.active {
  transform: scale(1.1);
}

.movie-poster {
  width: 80px;
  height: 120px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  border: 2px solid transparent;
  margin-bottom: 8px;
}

.movie-item.active .movie-poster {
  border-color: #ff3b30;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-name {
  font-size: 12px;
  color: #333;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.movie-item.active .movie-name {
  color: #ff3b30;
  font-weight: 500;
}

/* 当前电影信息 */
.current-movie-info {
  padding: 0 16px;
  margin-bottom: 16px;
}

.movie-title {
  font-size: 20px;
  font-weight: 600;
  color: #000;
  margin: 0 0 8px;
}

.movie-meta {
  font-size: 14px;
  color: #8e8e93;
}

.divider {
  margin: 0 6px;
}

/* 日期选择器 */
.date-selector {
  margin: 16px 0;
}

.date-label {
  font-size: 15px;
  font-weight: 600;
  color: #000;
  margin: 0 0 8px 16px;
}

.date-scroll {
  display: flex;
  overflow-x: auto;
  padding: 0 16px;
  -webkit-overflow-scrolling: touch;
}

.date-scroll::-webkit-scrollbar {
  display: none;
}

.date-item {
  flex: 0 0 auto;
  width: 60px;
  height: 60px;
  border-radius: 12px;
  margin-right: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.date-item.active {
  background-color: #007aff;
}

.date-day {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
  color: #333;
}

.date-date {
  font-size: 12px;
  color: #8e8e93;
}

.date-item.active .date-day,
.date-item.active .date-date {
  color: #fff;
}

/* 场次列表 */
.session-list {
  padding: 0 16px;
}

.session-item {
  display: flex;
  align-items: center;
  background-color: #fff;
  border-radius: 12px;
  margin-bottom: 12px;
  padding: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.session-time {
  width: 80px;
  margin-right: 16px;
}

.start-time {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin-bottom: 4px;
}

.end-time {
  font-size: 12px;
  color: #8e8e93;
}

.session-info {
  flex: 1;
  margin-right: 16px;
}

.hall-name {
  font-size: 15px;
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
}

.language {
  font-size: 13px;
  color: #8e8e93;
}

.session-price {
  margin-right: 16px;
}

.price-value {
  font-size: 18px;
  font-weight: 600;
  color: #ff3b30;
}

.choose-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60px;
  height: 32px;
  background-color: #ff3b30;
  color: white;
  font-size: 14px;
  font-weight: 500;
  border-radius: 16px;
}

/* 空状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 16px;
  color: #8e8e93;
}

.empty-state i {
  font-size: 36px;
  margin-bottom: 12px;
}

.empty-state p {
  font-size: 15px;
  margin: 0;
}
</style>