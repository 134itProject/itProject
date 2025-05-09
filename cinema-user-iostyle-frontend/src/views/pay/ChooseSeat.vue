<template>
  <div class="ios-container">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <div class="back-button" @click="$router.go(-1)">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">选择座位</div>
      <div class="right-action"></div>
    </div>
    
    <!-- 步骤指示器 -->
    <div class="step-indicator">
      <div class="step completed">
        <div class="step-circle">1</div>
        <div class="step-name">选择场次</div>
      </div>
      <div class="step-line"></div>
      <div class="step active">
        <div class="step-circle">2</div>
        <div class="step-name">选择座位</div>
      </div>
      <div class="step-line"></div>
      <div class="step">
        <div class="step-circle">3</div>
        <div class="step-name">付款</div>
      </div>
      <div class="step-line"></div>
      <div class="step">
        <div class="step-circle">4</div>
        <div class="step-name">取票观影</div>
      </div>
    </div>
    
    <!-- 电影信息卡片 -->
    <div class="movie-card">
      <div class="movie-info">
        <div class="movie-poster">
          <img :src="session.sysMovie.moviePoster" @error="handleImageError">
        </div>
        <div class="movie-details">
          <h2 class="movie-title">{{ session.sysMovie.movieName || '电影名称' }}</h2>
          <div class="movie-meta">
            <div class="meta-item">{{ formatCategories(session.sysMovie.movieCategoryList) || '类型未知' }}</div>
            <div class="meta-item">{{ session.sysMovie.movieLength || '0' }}分钟</div>
          </div>
        </div>
      </div>
      
      <div class="session-info">
        <div class="info-row">
          <div class="info-label">影院</div>
          <div class="info-value">{{ session.sysHall?.sysCinema?.cinemaName || '影院名称' }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">影厅</div>
          <div class="info-value">{{ session.sysHall?.hallName || '影厅名称' }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">场次</div>
          <div class="info-value">{{ formatDate(session.sessionDate) }} {{ session.playTime || '时间未知' }}</div>
        </div>
      </div>
    </div>
    
    <!-- 座位图例 -->
    <div class="seat-legend">
      <div class="legend-item">
        <span class="seat" :class="isSelected[0]"></span>
        <span class="legend-text">可选座位</span>
      </div>
      <div class="legend-item">
        <span class="seat" :class="isSelected[3]"></span>
        <span class="legend-text">已售座位</span>
      </div>
      <div class="legend-item">
        <span class="seat" :class="isSelected[2]"></span>
        <span class="legend-text">已选座位</span>
      </div>
    </div>
    
    <!-- 银幕 -->
    <div class="screen-container">
      <div class="screen">
        <div class="screen-text">银幕</div>
      </div>
    </div>
    
    <!-- 座位选择区 -->
    <div class="seat-container">
      <div class="seat-scroll">
        <div class="seat-map">
          <div class="row" v-for="(value, key) in seats" :key="key">
            <div class="row-label">{{ key }}</div>
            <div class="row-seats">
              <div 
                v-for="(item, index) in value" 
                :key="index"
                class="seat" 
                :class="isSelected[seats[key][index]]"
                @click="pressSeat(key, index)">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 已选座位和结算栏 -->
    <div class="selected-seats-card" :class="{ 'has-seats': pickedSeats.length > 0 }">
      <div v-if="pickedSeats.length > 0">
        <div class="selected-seats-header">已选座位</div>
        <div class="selected-seats">
          <div class="seat-tag" v-for="(seat, index) in pickedSeats" :key="index">
            {{ seat }}
          </div>
        </div>
        <div class="price-info">
          <div class="price-label">总价</div>
          <div class="price-value">¥{{ totalPrice }}</div>
        </div>
        <button class="submit-button" @click="submitBill">提交订单</button>
      </div>
      <div v-else class="no-seats-selected">
        <i class="el-icon-info-circle"></i>
        <div class="no-seats-text">请选择座位</div>
      </div>
    </div>
    
    <!-- 优化的底部结算栏 -->
    <!-- <div class="bottom-panel">
      <div class="order-info">
        <div class="selected-info">
          <span v-if="pickedSeats.length > 0">已选：{{ formatSelectedSeats }}</span>
          <span v-else>请选择座位</span>
        </div>
        <div class="price-info">
          总价：<span class="price">¥{{ totalPrice }}</span>
        </div>
      </div>
      <button class="pay-button" :disabled="pickedSeats.length === 0" @click="submitBill">
        确认选座
      </button>
    </div> -->
    
    <!-- 固定在底部的导航栏，确保不被遮挡 -->
    <div class="tab-bar">
      <div class="tab-item" @click="$router.push('/')">
        <i class="tab-icon home-icon"></i>
        <div class="tab-label">首页</div>
      </div>
      <div class="tab-item" @click="$router.push('/movies')">
        <i class="tab-icon movie-icon"></i>
        <div class="tab-label">电影</div>
      </div>
      <div class="tab-item" @click="$router.push('/ranking')">
        <i class="tab-icon ranking-icon"></i>
        <div class="tab-label">榜单</div>
      </div>
      <div class="tab-item" @click="$router.push('/search')">
        <i class="tab-icon search-icon"></i>
        <div class="tab-label">搜索</div>
      </div>
      <div class="tab-item" @click="$router.push('/user')">
        <i class="tab-icon user-icon"></i>
        <div class="tab-label">我的</div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { Message } from "element-ui";

export default {
  name: "ChooseSeat",
  data() {
    return {
      isSelected: {
        0: "seat-default",
        1: "seat-no",
        2: "seat-pick",
        3: "seat-sold",
      },
      seats: {},
      sessionId: this.$route.params.sessionId,
      session: {
        sysMovie: {
          movieCategoryList: [],
        },
        sysCinema: {},
        sysHall: {
          sysCinema: {}
        },
      },
      pickedSeats: [],
      addForm: {
        userId: 0,
        sessionId: 0,
        seats: "",
      },
    };
  },
  created() {
    this.getSession();
  },
  methods: {
    // 现有方法保持不变，但添加一些辅助方法
    async getSession() {
      try {
        const { data: resp } = await axios.get("sysSession/find/" + this.sessionId);
        
        if (resp.code !== 200) {
          this.$message.error(resp.msg || '获取场次信息失败');
          return;
        }
        
        this.session = resp.data;
        
        // 处理电影海报
        if (this.session.sysMovie.moviePoster) {
          try {
            this.session.sysMovie.moviePoster = this.global.base + JSON.parse(this.session.sysMovie.moviePoster)[0];
          } catch (e) {
            console.error('解析电影海报出错', e);
          }
        }
        
        // 处理座位数据
        this.seats = JSON.parse(resp.data.sessionSeats);
        
        // 处理电影分类
        if (this.session.sysMovie.movieCategoryList) {
          this.session.sysMovie.movieCategoryList = this.session.sysMovie.movieCategoryList.map(obj => {
            return obj.movieCategoryName;
          });
        }
      } catch (err) {
        console.error('获取场次信息出错', err);
        this.$message.error('获取场次信息失败');
      }
    },
    
    pressSeat(key, idx) {
      let seat_str = key + "排" + (idx + 1) + "座";
      
      // 选座
      if (this.seats[key][idx] === 0) {
        // 判断选座是否超过5个，超过5个则不能选
        if (this.pickedSeats.length === 5) {
          this.$message({
            message: '您最多选择五个座位',
            type: 'warning'
          });
          return;
        }
        this.$set(this.seats[key], idx, 2);
        this.pickedSeats.push(seat_str);
      } else if (this.seats[key][idx] === 2) {
        // 取消选座
        this.$set(this.seats[key], idx, 0);
        this.pickedSeats.splice(this.pickedSeats.indexOf(seat_str), 1);
      }
    },
    
    async submitBill() {
      // 获取token，校验登录
      const token = window.sessionStorage.getItem("token");
      if (!token) {
        window.sessionStorage.setItem("sessionId", this.session.sessionId);
        this.$message({
          message: '请先登录后再提交订单',
          type: 'warning',
          onClose: () => {
            this.$router.push("/login");
          }
        });
        return;
      }
      
      // 校验是否选座，未选座则警告
      if (this.pickedSeats.length === 0) {
        this.$message({
          message: '请先选择座位',
          type: 'warning'
        });
        return;
      }
      
      try {
        // 获取场次座位信息
        const { data: curSession } = await axios.get("sysSession/find/" + this.sessionId);
        
        if (curSession.code !== 200) {
          this.$message.error('获取最新座位信息失败');
          return;
        }
        
        let sessionSeats = JSON.parse(curSession.data.sessionSeats);
        
        // 解析出订单选择的座位，更新座位信息
        for (let seat of this.pickedSeats) {
          let row = seat.substring(0, seat.indexOf("排"));
          let col = Number.parseInt(seat.substring(seat.indexOf("排") + 1, seat.length - 1));
          
          if (sessionSeats[row][col - 1] === 3) {
            // 更新座位信息
            this.seats = JSON.parse(curSession.data.sessionSeats);
            this.$message({
              message: '抱歉！您所选的座位' + seat + '已被占用，请重新选择',
              type: 'error'
            });
            return;
          } else {
            sessionSeats[row][col - 1] = 3;
          }
        }
        
        // 准备提交订单数据
        this.addForm.userId = JSON.parse(window.sessionStorage.getItem("loginUser")).userId;
        this.addForm.sessionId = this.sessionId;
        this.addForm.seats = JSON.stringify(this.pickedSeats);
        
        // 提交订单
        axios.defaults.headers.post["Content-Type"] = "application/json";
        const { data: res } = await axios.post(
          "sysBill",
          JSON.stringify({
            sysBill: this.addForm,
            sessionSeats: JSON.stringify(sessionSeats),
          })
        );
        
        if (res.code !== 200) {
          this.$message.error('提交订单失败');
          return;
        }
        
        // 订单提交成功，跳转到订单详情页
        this.$message({
          message: '订单提交成功',
          type: 'success',
          onClose: () => {
            this.$router.push("/billDetail/" + res.data.billId);
          }
        });
      } catch (err) {
        console.error('提交订单出错', err);
        this.$message.error('提交订单失败');
      }
    },
    
    // 新增方法
    formatCategories(categories) {
      if (!categories) return '';
      return Array.isArray(categories) ? categories.join(' / ') : categories;
    },
    
    formatDate(date) {
      if (!date) return '';
      return moment(date).format('MM月DD日');
    },
    
    handleImageError(e) {
      e.target.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACAAQMAAAD58POIAAAABlBMVEXd3d3///+uPjS6AAAAAnRSTlP/AOW3MEoAAACASURBVFjD7dWxDYAwDETRi5ERvP+QZAeyQUtngZSKhq/flJx0Es/ct1OffcTxoyasA1wB+Bx8LgAgwHtAtQuQYPeAu2s9iDCPQHULQYR5BFKyGgFgEfAMlgCLQPNSDwLMAc0hNQLAHKAyaAGWgD6DFmAJ6B/UIMIcMDKYAkwCvw/UF6f1qJ98AAAAAElFTkSuQmCC';
    }
  },
  computed: {
    totalPrice() {
      return this.session.sessionPrice * this.pickedSeats.length || 0;
    },
    formatSelectedSeats() {
      if (this.pickedSeats.length === 0) return '';
      return this.pickedSeats.length > 1 
        ? `${this.pickedSeats[0]} 等${this.pickedSeats.length}个座位` 
        : this.pickedSeats[0];
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
  padding-bottom: 100px; /* 为底部选座结算区域留出空间 */
  position: relative;
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
  z-index: 100;
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
  width: 24px;
  height: 24px;
}

/* 步骤指示器 */
.step-indicator {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px;
  margin-bottom: 16px;
}

.step {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
}

.step-circle {
  width: 24px;
  height: 24px;
  border-radius: 12px;
  background-color: #c7c7cc;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 4px;
}

.step.active .step-circle,
.step.completed .step-circle {
  background-color: #007aff;
}

.step-name {
  font-size: 10px;
  color: #8e8e93;
  text-align: center;
}

.step.active .step-name {
  color: #007aff;
  font-weight: 500;
}

.step-line {
  height: 1px;
  background-color: #c7c7cc;
  flex-grow: 1;
  margin: 0 4px;
  margin-bottom: 20px;
}

/* 电影信息卡片 */
.movie-card {
  background-color: white;
  border-radius: 12px;
  margin: 0 16px 16px;
  padding: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.movie-info {
  display: flex;
  margin-bottom: 16px;
}

.movie-poster {
  width: 80px;
  height: 120px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-right: 12px;
  flex-shrink: 0;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-details {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.movie-title {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin: 0 0 8px;
}

.movie-meta {
  font-size: 14px;
  color: #8e8e93;
}

.meta-item {
  margin-bottom: 4px;
}

.session-info {
  border-top: 1px solid rgba(60, 60, 67, 0.1);
  padding-top: 16px;
}

.info-row {
  display: flex;
  margin-bottom: 8px;
}

.info-label {
  width: 60px;
  font-size: 14px;
  color: #8e8e93;
}

.info-value {
  flex: 1;
  font-size: 14px;
  color: #333;
}

/* 座位图例 */
.seat-legend {
  display: flex;
  justify-content: center;
  margin-bottom: 16px;
}

.legend-item {
  display: flex;
  align-items: center;
  margin: 0 8px;
}

.legend-text {
  font-size: 12px;
  color: #8e8e93;
  margin-left: 4px;
}

/* 银幕 */
.screen-container {
  padding: 0 16px;
  margin-bottom: 16px;
}

.screen {
  height: 30px;
  background: linear-gradient(to bottom, #d1d1d6, #f2f2f7);
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.screen-text {
  font-size: 12px;
  color: #8e8e93;
}

/* 座位选择区 */
.seat-container {
  padding: 0 16px;
  margin-bottom: 16px;
}

.seat-scroll {
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
}

.seat-map {
  padding: 16px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.row {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.row:last-child {
  margin-bottom: 0;
}

.row-label {
  width: 20px;
  font-size: 12px;
  color: #8e8e93;
  text-align: center;
  margin-right: 8px;
}

.row-seats {
  display: flex;
}

.seat {
  width: 24px;
  height: 24px;
  margin: 0 2px;
  background-position: center;
  background-size: contain;
  background-repeat: no-repeat;
}

.seat-default {
  background-image: url("../../assets/sofa-unpick.png");
}

.seat-empty {
  background-image: url("../../assets/sofa-empty.png");
}

.seat-sold {
  background-image: url("../../assets/sofa-forbidden.png");
}

.seat-pick {
  background-image: url("../../assets/sofa-pick.png");
}

/* 已选座位和结算栏 */
.selected-seats-card {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  max-width: 430px;
  margin: 0 auto;
  background-color: white;
  border-top-left-radius: 16px;
  border-top-right-radius: 16px;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
  padding: 20px 16px;
  transition: transform 0.3s ease;
  transform: translateY(70%);
  z-index: 90;
}

.selected-seats-card.has-seats {
  transform: translateY(0);
}

.selected-seats-header {
  font-size: 16px;
  font-weight: 600;
  color: #000;
  margin-bottom: 12px;
}

.selected-seats {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 16px;
}

.seat-tag {
  background-color: rgba(0, 122, 255, 0.1);
  color: #007aff;
  border-radius: 16px;
  padding: 6px 12px;
  font-size: 13px;
  margin-right: 8px;
  margin-bottom: 8px;
}

.price-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.price-label {
  font-size: 16px;
  color: #333;
}

.price-value {
  font-size: 24px;
  font-weight: 600;
  color: #ff3b30;
}

.submit-button {
  width: 100%;
  height: 44px;
  border-radius: 22px;
  background-color: #ff3b30;
  color: white;
  font-size: 16px;
  font-weight: 500;
  border: none;
  box-shadow: 0 2px 8px rgba(255, 59, 48, 0.3);
}

.no-seats-selected {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px 0;
}

.no-seats-selected i {
  font-size: 24px;
  color: #8e8e93;
  margin-bottom: 8px;
}

.no-seats-text {
  font-size: 15px;
  color: #8e8e93;
}

/* 底部固定的结算面板 */
.bottom-panel {
  position: fixed;
  bottom: 49px; /* 留出底部导航栏的空间 */
  left: 0;
  right: 0;
  max-width: 430px;
  margin: 0 auto;
  display: flex;
  height: 50px;
  background-color: white;
  border-top: 1px solid #e5e5e5;
  z-index: 90;
}

.order-info {
  flex: 1;
  padding: 0 12px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.selected-info {
  font-size: 12px;
  color: #666;
}

.price-info {
  font-size: 14px;
  color: #333;
}

.price {
  color: #ff3b30;
  font-weight: bold;
  font-size: 16px;
}

.pay-button {
  width: 120px;
  background-color: #ff3b30;
  color: white;
  border: none;
  font-size: 16px;
  font-weight: 500;
  border-radius: 0;
}

.pay-button:disabled {
  background-color: #cccccc;
}

/* 底部导航栏 */
.tab-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 49px;
  background-color: white;
  display: flex;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  z-index: 80; /* 确保导航栏在上层 */
  max-width: 430px;
  margin: 0 auto;
  padding-bottom: env(safe-area-inset-bottom); /* 适配iPhone X及以上机型 */
}

.tab-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 6px;
}

.tab-icon {
  width: 24px;
  height: 24px;
  margin-bottom: 2px;
  /* 这里可以添加图标的背景图片 */
}

.tab-label {
  font-size: 10px;
  color: #8e8e93;
}

.tab-item.active .tab-label {
  color: #007aff;
}

/* 增加主容器的底部内边距，确保所有内容都可见 */
.ios-container {
  padding-bottom: 100px; /* 为底部导航栏和结算区预留空间 */
}
</style>