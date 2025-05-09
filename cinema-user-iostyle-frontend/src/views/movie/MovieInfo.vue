<!--
 * 电影详情
 *
 * @Author: zzz
 * @Date: 2025-03-06
-->
<template>
  <div class="ios-container">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <div class="back-button" @click="$router.go(-1)">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">电影详情</div>
      <div class="right-action">
        <i class="el-icon-share"></i>
      </div>
    </div>
    
    <!-- 电影基本信息区域 -->
    <div class="movie-header">
      <div class="poster-container">
        <img class="movie-poster" 
             :src="getMoviePoster(movieInfo)" 
             :alt="movieInfo.movieName"
             @error="handleImageError">
      </div>
      
      <div class="movie-basic-info">
        <h1 class="movie-title">{{movieInfo.movieName || '未知电影'}}</h1>
        
        <div class="movie-rating" v-if="movieInfo.movieScore">
          <span class="rating-value">{{movieInfo.movieScore}}</span>
          <div class="rating-stars">
            <i class="el-icon-star-on" v-for="n in Math.floor(movieInfo.movieScore/2)" :key="n"></i>
            <i class="el-icon-star-half" v-if="movieInfo.movieScore%2 >= 1"></i>
          </div>
        </div>
        
        <div class="movie-meta">
          <div class="meta-item" v-if="movieInfo.movieCategoryList">
            <span class="meta-label">类型：</span>
            <span class="meta-value">{{formatCategories(movieInfo.movieCategoryList)}}</span>
          </div>
          
          <div class="meta-item" v-if="movieInfo.movieArea && movieInfo.movieLength">
            <span class="meta-label">地区/时长：</span>
            <span class="meta-value">{{movieInfo.movieArea}} / {{movieInfo.movieLength}}分钟</span>
          </div>
          
          <div class="meta-item" v-if="movieInfo.releaseDate">
            <span class="meta-label">上映日期：</span>
            <span class="meta-value">{{formatDate(movieInfo.releaseDate)}}</span>
          </div>
          
          <div class="meta-item" v-if="movieInfo.movieBoxOffice">
            <span class="meta-label">累计票房：</span>
            <span class="meta-value highlight">{{formatBoxOffice(movieInfo.movieBoxOffice)}}</span>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 购票按钮 -->
    <div class="action-button-container">
      <button class="primary-button" @click="toChooseSession">
        <i class="el-icon-shopping-cart-2"></i> 立即购票
      </button>
    </div>
    
    <!-- 内容标签页 -->
    <div class="content-tabs">
      <div class="tab-item" 
           :class="{active: activeName === 'introduction'}" 
           @click="activeName = 'introduction'">
        电影介绍
      </div>
      <div class="tab-item" 
           :class="{active: activeName === 'pictures'}" 
           @click="activeName = 'pictures'">
        剧照
      </div>
    </div>
    
    <!-- 电影介绍内容 -->
    <div class="tab-content" v-if="activeName === 'introduction'">
      <div class="content-section">
        <h3 class="section-title">
          <i class="el-icon-document"></i> 剧情简介
        </h3>
        <p class="movie-synopsis">{{movieInfo.movieIntroduction || '暂无简介'}}</p>
      </div>
      
      <div class="content-section gallery-preview" v-if="movieInfo.moviePictures && movieInfo.moviePictures.length > 0">
        <div class="section-header">
          <h3 class="section-title">
            <i class="el-icon-picture"></i> 精彩剧照
          </h3>
          <span class="view-more" @click="activeName = 'pictures'">查看全部</span>
        </div>
        
        <div class="preview-gallery">
          <div class="preview-image" 
               v-for="(pic, index) in movieInfo.moviePictures.slice(0, 3)" 
               :key="index" 
               @click="previewImage(index)">
            <img :src="pic" :alt="`${movieInfo.movieName}剧照${index+1}`">
          </div>
        </div>
      </div>
    </div>
    
    <!-- 图集内容 -->
    <div class="tab-content" v-else-if="activeName === 'pictures'">
      <div class="content-section">
        <div class="photo-grid" v-if="movieInfo.moviePictures && movieInfo.moviePictures.length > 0">
          <div class="photo-item" 
               v-for="(pic, index) in movieInfo.moviePictures" 
               :key="index"
               @click="previewImage(index)">
            <img :src="pic" :alt="`${movieInfo.movieName}剧照${index+1}`">
          </div>
        </div>
        <div class="empty-state" v-else>
          <i class="el-icon-picture"></i>
          <p>暂无剧照</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import { MessageBox } from 'element-ui';

export default {
  name: "MovieInfo",
  data() {
    return {
      movieInfo: {
        moviePictures: []
      },
      movieId: this.$route.params.movieId,
      activeName: 'introduction',
      httpURL: this.global?.base || ''
    }
  },
  created() {
    this.getMovieInfo();
  },
  methods: {
    async getMovieInfo() {
      try {
        const { data: res } = await axios.get('sysMovie/find/' + this.movieId);
        
        if (res.code !== 200) {
          this.$message.error('获取电影信息失败');
          return;
        }
        
        this.movieInfo = res.data;
        
        // 处理电影海报
        if (this.movieInfo.moviePoster) {
          try {
            const posterArr = JSON.parse(this.movieInfo.moviePoster);
            if (posterArr && posterArr.length > 0) {
              this.movieInfo.moviePoster = this.httpURL + posterArr[0];
            }
          } catch (e) {
            console.error('解析电影海报出错', e);
          }
        }
        
        // 处理电影图片
        if (this.movieInfo.moviePictures) {
          try {
            const picturesArr = JSON.parse(this.movieInfo.moviePictures);
            this.movieInfo.moviePictures = picturesArr.map(pic => this.httpURL + pic);
          } catch (e) {
            console.error('解析电影图片出错', e);
            this.movieInfo.moviePictures = [];
          }
        }
        
      } catch (err) {
        console.error('获取电影信息出错', err);
        this.$message.error('获取电影信息失败');
      }
    },
    
    toChooseSession() {
      let cinemaId = 1;
      this.$router.push('/chooseSession/' + cinemaId);
    },
    
    previewImage(index) {
      if (!this.movieInfo.moviePictures || this.movieInfo.moviePictures.length === 0) {
        return;
      }
      
      // 使用Element UI的MessageBox API创建图片预览
      MessageBox.alert('', {
        dangerouslyUseHTMLString: true,
        showConfirmButton: false,
        showCancelButton: false,
        closeOnClickModal: true,
        customClass: 'image-preview-dialog',
        beforeClose: (action, instance, done) => {
          done();
        },
        message: `
          <div style="text-align: center;">
            <img 
              src="${this.movieInfo.moviePictures[index]}" 
              style="max-width: 100%; max-height: 80vh;"
              alt="${this.movieInfo.movieName}剧照"
            />
          </div>
        `
      });
    },
    
    getMoviePoster(movie) {
      if (!movie || !movie.moviePoster) {
        return 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAADeAQMAAAD9kgZ5AAAABlBMVEXd3d3///+uPjS6AAAAAnRSTlP/AOW3MEoAAACASURBVFjD7dWxDYAwDETRi5ERvP+QZAeyQUtngZSKhq/flJx0Es/ct1OffcTxoyasA1wB+Bx8LgAgwHtAtQuQYPeAu2s9iDCPQHULQYR5BFKyGgFgEfAMlgCLQPNSDwLMAc0hNQLAHKAyaAGWgD6DFmAJ6B/UIMIcMDKYAkwCvw/UF6f1qJ98AAAAAElFTkSuQmCC';
      }
      return movie.moviePoster;
    },
    
    handleImageError(e) {
      e.target.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAADeAQMAAAD9kgZ5AAAABlBMVEXd3d3///+uPjS6AAAAAnRSTlP/AOW3MEoAAACASURBVFjD7dWxDYAwDETRi5ERvP+QZAeyQUtngZSKhq/flJx0Es/ct1OffcTxoyasA1wB+Bx8LgAgwHtAtQuQYPeAu2s9iDCPQHULQYR5BFKyGgFgEfAMlgCLQPNSDwLMAc0hNQLAHKAyaAGWgD6DFmAJ6B/UIMIcMDKYAkwCvw/UF6f1qJ98AAAAAElFTkSuQmCC';
    },
    
    formatCategories(categories) {
      if (Array.isArray(categories)) {
        return categories.map(cat => cat.movieCategoryName || cat).join(' / ');
      }
      return categories;
    },
    
    formatDate(dateStr) {
      if (!dateStr) return '';
      return moment(dateStr).format('YYYY年MM月DD日');
    },
    
    formatBoxOffice(boxOffice) {
      if (!boxOffice) return '0';
      
      const num = parseFloat(boxOffice);
      if (isNaN(num)) return boxOffice;
      
      if (num >= 100000000) {
        return (num / 100000000).toFixed(1) + '亿';
      } else if (num >= 10000) {
        return (num / 10000).toFixed(1) + '万';
      } else {
        return num.toString();
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
  padding-bottom: 40px;
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

/* 电影基本信息区域 */
.movie-header {
  display: flex;
  padding: 16px;
  background-color: #fff;
  border-radius: 12px;
  margin: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.poster-container {
  width: 100px;
  height: 150px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  margin-right: 16px;
  flex-shrink: 0;
}

.movie-poster {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-basic-info {
  flex: 1;
  overflow: hidden;
}

.movie-title {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin: 0 0 8px;
  line-height: 1.3;
}

.movie-rating {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.rating-value {
  font-size: 16px;
  font-weight: 600;
  color: #ff9500;
  margin-right: 6px;
}

.rating-stars {
  color: #ff9500;
  font-size: 14px;
}

.movie-meta {
  font-size: 14px;
  color: #8e8e93;
}

.meta-item {
  margin-bottom: 4px;
}

.meta-label {
  color: #8e8e93;
}

.meta-value {
  color: #333;
}

.meta-value.highlight {
  color: #ff9500;
  font-weight: 500;
}

/* 购票按钮 */
.action-button-container {
  padding: 0 16px;
  margin-bottom: 16px;
}

.primary-button {
  width: 100%;
  height: 44px;
  border-radius: 22px;
  background-color: #ff3b30;
  color: white;
  font-size: 16px;
  font-weight: 500;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 8px rgba(255, 59, 48, 0.3);
}

.primary-button i {
  margin-right: 6px;
}

/* 内容标签页 */
.content-tabs {
  display: flex;
  background-color: #fff;
  border-radius: 12px 12px 0 0;
  margin: 0 16px;
  overflow: hidden;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 14px 0;
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
  height: 3px;
  background-color: #007aff;
  border-radius: 1.5px;
}

/* 标签页内容 */
.tab-content {
  padding: 0 16px;
}

.content-section {
  background-color: #fff;
  border-radius: 0 0 12px 12px;
  padding: 16px;
  margin-bottom: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.content-section:last-child {
  margin-bottom: 0;
}

.section-title {
  font-size: 17px;
  font-weight: 600;
  color: #000;
  margin: 0 0 12px;
  display: flex;
  align-items: center;
}

.section-title i {
  margin-right: 6px;
  color: #007aff;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.view-more {
  font-size: 14px;
  color: #007aff;
}

.movie-synopsis {
  font-size: 15px;
  line-height: 1.5;
  color: #333;
  margin: 0;
}

/* 图片预览 */
.preview-gallery {
  display: flex;
  overflow-x: auto;
  margin: 0 -16px;
  padding: 0 16px 8px;
  -webkit-overflow-scrolling: touch;
}

.preview-gallery::-webkit-scrollbar {
  display: none;
}

.preview-image {
  flex: 0 0 auto;
  width: 140px;
  height: 90px;
  border-radius: 8px;
  overflow: hidden;
  margin-right: 8px;
}

.preview-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 照片网格 */
.photo-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
}

.photo-item {
  aspect-ratio: 3/2;
  border-radius: 8px;
  overflow: hidden;
}

.photo-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 空状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 32px 0;
  color: #8e8e93;
}

.empty-state i {
  font-size: 32px;
  margin-bottom: 8px;
}

.empty-state p {
  font-size: 15px;
  margin: 0;
}

@media (max-width: 430px) {
  .ios-container {
    width: 100%;
  }
}

/* 在全局样式中添加 */
.image-preview-dialog .el-message-box {
  width: 90%;
  max-width: 600px;
  background: rgba(0,0,0,0.8);
  padding: 10px;
}

.image-preview-dialog .el-message-box__header {
  display: none;
}

.image-preview-dialog .el-message-box__content {
  padding: 10px;
}

.image-preview-dialog .el-message-box__message p {
  margin: 0;
}
</style>
