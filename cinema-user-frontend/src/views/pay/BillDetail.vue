<template>
  <div class="ios-container">
    <!-- 顶部导航栏 -->
    <div class="nav-bar">
      <div class="back-button" @click="$router.go(-1)">
        <i class="el-icon-arrow-left"></i>
      </div>
      <div class="title">订单详情</div>
      <div class="right-action"></div>
    </div>
    
    <!-- 步骤指示器 -->
    <div class="step-indicator">
      <div class="step completed">
        <div class="step-circle">1</div>
        <div class="step-name">选择场次</div>
      </div>
      <div class="step-line"></div>
      <div class="step completed">
        <div class="step-circle">2</div>
        <div class="step-name">选择座位</div>
      </div>
      <div class="step-line"></div>
      <div class="step active">
        <div class="step-circle">3</div>
        <div class="step-name">付款</div>
      </div>
      <div class="step-line"></div>
      <div class="step">
        <div class="step-circle">4</div>
        <div class="step-name">取票观影</div>
      </div>
    </div>
    
    <!-- 订单状态卡片 -->
    <div class="status-card" :class="getStatusClass()">
      <div class="status-icon">
        <!-- 待支付 -->
        <img v-if="payState === false && cancelState === false && (minutes > 0 || seconds > 0)" src="../../assets/money.png" class="status-img">
        
        <!-- 超时失效 -->
        <img v-if="payState === false && cancelState === true && cancelTime === null && minutes <= 0 && seconds <= 0" src="../../assets/bill-invalid.png" class="status-img">
        
        <!--
        已完成 -->
        <i v-if="payState === true" class="iconfont icon-r-yes status-icon-font success"></i>
        
        <!-- 已取消 -->
        <i v-if="cancelState === true && cancelTime !== null" class="iconfont icon-r-delete status-icon-font danger"></i>
      </div>
      
      <div class="status-content">
        <!-- 待支付 -->
        <div v-if="payState === false && cancelState === false && (minutes > 0 || seconds > 0)">
          <div class="status-title pending">待支付</div>
          <div class="status-subtitle">请在 <span class="countdown">{{minutes}}分{{seconds}}秒</span> 内完成支付</div>
        </div>
        
        <!-- 超时失效 -->
        <div v-if="payState === false && cancelState === true && cancelTime === null && minutes <= 0 && seconds <= 0">
          <div class="status-title invalid">订单已失效</div>
          <div class="status-subtitle">由于超时未支付，订单已自动取消</div>
        </div>
        
        <!-- 已完成 -->
        <div v-if="payState === true">
          <div class="status-title success">已完成</div>
          <div class="status-subtitle">感谢您的购买，祝您观影愉快</div>
        </div>
        
        <!-- 已取消 -->
        <div v-if="cancelState === true && cancelTime !== null">
          <div class="status-title danger">订单已取消</div>
          <div class="status-subtitle">您已取消该订单</div>
        </div>
      </div>
    </div>
    
    <!-- 订单信息卡片 -->
    <div class="order-card">
      <div class="order-header">
        <div class="order-number">订单编号: {{billInfo.billId || '-'}}</div>
        <div class="order-time">{{formatOrderTime(billInfo.createTime)}}</div>
      </div>
      
      <div class="divider"></div>
      
      <!-- 电影信息区域 -->
      <div class="movie-info">
        <div class="movie-title">《{{billInfo.sysSession.sysMovie.movieName || '加载中...'}}》</div>
        <div class="session-info">
          <div class="info-row">
            <div class="info-label">场次</div>
            <div class="info-value">{{formatDate(billInfo.sysSession.sessionDate)}} {{billInfo.sysSession.playTime || '-'}} ~ {{billInfo.sysSession.endTime || '-'}}</div>
          </div>
          <div class="info-row">
            <div class="info-label">影厅</div>
            <div class="info-value">{{billInfo.sysSession.sysHall.hallName || '-'}}</div>
          </div>
          <div class="info-row">
            <div class="info-label">版本</div>
            <div class="info-value">{{billInfo.sysSession.languageVersion || '-'}}</div>
          </div>
          <div class="info-row">
            <div class="info-label">座位</div>
            <div class="info-value seats-container">
              <span class="seat-tag" v-for="(seat, index) in billSeats" :key="index">{{seat}}</span>
            </div>
          </div>
        </div>
      </div>
      
      <div class="divider"></div>
      
      <!-- 影院信息区域 -->
      <div class="cinema-info">
        <div class="cinema-name">{{billInfo.sysSession.sysHall.sysCinema.cinemaName || '影院名称'}}</div>
        <div class="cinema-address">地址：{{billInfo.sysSession.sysHall.sysCinema.cinemaAddress || '-'}}</div>
        <div class="cinema-phone">电话：{{billInfo.sysSession.sysHall.sysCinema.cinemaPhone || '-'}}</div>
        <div class="cinema-hours">营业时间：{{billInfo.sysSession.sysHall.sysCinema.workStartTime || '-'}} - {{billInfo.sysSession.sysHall.sysCinema.workEndTime || '-'}}</div>
      </div>
      
      <div class="divider"></div>
      
      <!-- 价格信息区域 -->
      <div class="price-info">
        <div class="price-row">
          <div class="price-label">票价</div>
          <div class="price-value">¥{{billInfo.sysSession.sessionPrice || 0}} × {{billSeats.length || 0}}</div>
        </div>
        <div class="price-row total">
          <div class="price-label">总价</div>
          <div class="price-value">¥{{calculateTotal()}}</div>
        </div>
      </div>
    </div>
    
    <!-- 底部操作区域 -->
    <div class="action-area" v-if="payState === false && cancelState === false && (minutes > 0 || seconds > 0)">
      <button class="cancel-button" @click="cancelForBill">取消订单</button>
      <button class="pay-button" @click="payForBill">立即支付 ¥{{calculateTotal()}}</button>
    </div>
    
    <!-- 客服提示 -->
    <div class="service-hint">
      <i class="el-icon-service"></i>
      <span>如有问题可致电客服：{{billInfo.sysSession.sysHall.sysCinema.cinemaPhone || '-'}}</span>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: "BillDetail",
  data() {
    return {
      billId: this.$route.params.billId,
      billInfo: {
        sysSession: {
          sysMovie: {},
          sysHall: {
            sysCinema: {
              user: {}
            }
          }
        }
      },
      billSeats: [],
      payState: null,
      cancelState: null,
      cancelTime: null,
      //计时
      minutes: 1,
      seconds: 0,
    }
  },
  created() {
    this.getBillInfo()
  },
  mounted() {
    this.add()
  },
  methods: {
    async getBillInfo() {
      try {
        const { data : res } = await axios.get('sysBill/' + this.billId)
        if(res.code !== 200) {
          this.$message.error('获取订单信息失败')
          return
        }
        
        // 验证用户身份
        let userId = JSON.parse(window.sessionStorage.getItem('loginUser')).userId
        if (userId !== res.data.userId) {
          this.$message({
            type: 'error',
            message: '非法操作！您无权查看此订单',
            duration: 3000,
            onClose: () => {
              this.$router.push('/bill')
            }
          })
          return
        }
        
        this.billInfo = res.data
        this.cancelTime = this.billInfo.cancelTime
        
        // 处理订单座位信息
        this.billSeats = JSON.parse(this.billInfo.seats)
        this.payState = this.billInfo.payState
        this.cancelState = this.billInfo.cancelState
        this.computeLeftTime()
      } catch (err) {
        console.error('获取订单信息失败', err)
        this.$message.error('获取订单信息失败')
      }
    },
    
    async payForBill() {
      try {
        // 更新订单状态
        this.billInfo.payState = true
        
        // 更新订单信息
        axios.defaults.headers.put['Content-Type'] = 'application/json'
        const { data: res } = await axios.put('sysBill', JSON.stringify(this.billInfo))
        
        if(res.code !== 200) {
          this.$message.error('支付失败，请重试')
          return
        }
        
        this.payState = true
        this.$message({
          type: 'success',
          message: '支付成功！',
          duration: 2000,
          onClose: () => {
            this.$router.push('/bill')
          }
        })
      } catch (err) {
        console.error('支付失败', err)
        this.$message.error('支付失败，请重试')
      }
    },
    
    async cancelForBill() {
      try {
        // 确认取消
        this.$confirm('确定要取消订单吗？', '取消订单', {
          confirmButtonText: '确定',
          cancelButtonText: '返回',
          type: 'warning'
        }).then(async () => {
          // 更新订单状态
          this.billInfo.cancelState = true
          this.billInfo.cancelTime = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
          
          // 获取场次座位信息
          const { data : curSession } = await axios.get('sysSession/find/' + this.billInfo.sessionId)
          let sessionSeats = JSON.parse(curSession.data.sessionSeats)
          
          // 解析出订单选择的座位，更新座位信息
          for(let seat of this.billSeats){
            let row = seat.substring(0, seat.indexOf('排'))
            let col = Number.parseInt(seat.substring(seat.indexOf('排') + 1, seat.length - 1))
            sessionSeats[row][col - 1] = 0
          }
          
          // 更新订单信息和场次座位信息
          axios.defaults.headers.put['Content-Type'] = 'application/json'
          const { data: res } = await axios.put('sysBill/cancel',JSON.stringify({
            sysBill: this.billInfo, 
            sessionSeats: JSON.stringify(sessionSeats)
          }))
          
          if(res.code !== 200) {
            this.$message.error('取消订单失败')
            return
          }
          
          this.payState = false
          this.cancelState = true
          this.$message({
            type: 'success',
            message: '订单已取消',
            duration: 2000,
            onClose: () => {
              this.$router.push('/billDetail/' + this.billInfo.billId)
            }
          })
        }).catch(() => {
          // 用户取消操作，不做任何处理
        })
      } catch (err) {
        console.error('取消订单失败', err)
        this.$message.error('取消订单失败')
      }
    },
    
    computeLeftTime() {
      let cur = new Date()
      let createTime = new Date(this.billInfo.createTime)
      let diff = 15 * 60 - (cur - createTime) / 1000
      if (diff < 0) {
        this.minutes = 0
        this.seconds = 0
      } else {
        this.minutes = Math.floor(diff / 60)
        this.seconds = Math.floor(diff % 60)
      }
    },
    
    //计时走动
    add: function () {
      let _this = this
      let time = window.setInterval(function () {
        if (_this.seconds === 0 && _this.minutes !== 0) {
          _this.seconds = 59
          _this.minutes -= 1
        } else if (_this.minutes === 0 && _this.seconds === 0) {
          _this.seconds = 0
          window.clearInterval(time)
        } else {
          _this.seconds -= 1
        }
      }, 1000)
    },
    
    // 格式化日期
    formatDate(date) {
      if (!date) return '-';
      return moment(date).format('MM月DD日');
    },
    
    // 格式化订单时间
    formatOrderTime(time) {
      if (!time) return '';
      return moment(time).format('YYYY-MM-DD HH:mm:ss');
    },
    
    // 计算总价
    calculateTotal() {
      return ((this.billInfo.sysSession.sessionPrice || 0) * (this.billSeats.length || 0)).toFixed(1);
    },
    
    // 获取状态样式类
    getStatusClass() {
      if (this.payState === true) return 'success-status';
      if (this.cancelState === true) return 'danger-status';
      if (this.minutes <= 0 && this.seconds <= 0) return 'danger-status';
      return 'pending-status';
    },
    
    // 数字格式化
    num(n) {
      return n < 10 ? '0' + n : '' + n;
    }
  },
  computed: {
    second: function () {
      return this.num(this.seconds);
    },
    minute: function () {
      return this.num(this.minutes);
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
  padding-bottom: 100px;
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

.step.active .step-circle {
  background-color: #007aff;
}

.step.completed .step-circle {
  background-color: #34c759;
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

.step.completed .step-name {
  color: #34c759;
}

.step-line {
  height: 1px;
  background-color: #c7c7cc;
  flex-grow: 1;
  margin: 0 4px;
  margin-bottom: 20px;
}

/* 订单状态卡片 */
.status-card {
  background-color: white;
  border-radius: 12px;
  margin: 0 16px 16px;
  padding: 16px;
  display: flex;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.pending-status {
  background-color: #FFFBF2;
}

.success-status {
  background-color: #F0FFF4;
}

.danger-status {
  background-color: #FFF5F5;
}

.status-icon {
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 16px;
}

.status-img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.status-icon-font {
  font-size: 32px;
}

.status-icon-font.success {
  color: #34c759;
}

.status-icon-font.danger {
  color: #ff3b30;
}

.status-content {
  flex: 1;
}

.status-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 4px;
}

.status-title.pending {
  color: #faaf00;
}

.status-title.success {
  color: #34c759;
}

.status-title.danger, .status-title.invalid {
  color: #ff3b30;
}

.status-subtitle {
  font-size: 14px;
  color: #8e8e93;
}

.countdown {
  color: #faaf00;
  font-weight: 600;
}

/* 订单信息卡片 */
.order-card {
  background-color: white;
  border-radius: 12px;
  margin: 0 16px 16px;
  padding: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.order-number {
  font-size: 14px;
  color: #333;
}

.order-time {
  font-size: 12px;
  color: #8e8e93;
}

.divider {
  height: 1px;
  background-color: rgba(60, 60, 67, 0.1);
  margin: 12px 0;
}

/* 电影信息区域 */
.movie-info {
  margin-bottom: 8px;
}

.movie-title {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin-bottom: 12px;
}

.session-info {
  margin-top: 8px;
}

.info-row {
  display: flex;
  margin-bottom: 8px;
}

.info-label {
  width: 40px;
  font-size: 14px;
  color: #8e8e93;
}

.info-value {
  flex: 1;
  font-size: 14px;
  color: #333;
}

.seats-container {
  display: flex;
  flex-wrap: wrap;
}

.seat-tag {
  background-color: rgba(0, 122, 255, 0.1);
  color: #007aff;
  border-radius: 12px;
  padding: 2px 8px;
  font-size: 12px;
  margin-right: 8px;
  margin-bottom: 4px;
}

/* 影院信息区域 */
.cinema-info {
  margin-bottom: 8px;
}

.cinema-name {
  font-size: 16px;
  font-weight: 600;
  color: #000;
  margin-bottom: 8px;
}

.cinema-address, .cinema-phone, .cinema-hours {
  font-size: 14px;
  color: #666;
  margin-bottom: 4px;
}

/* 价格信息区域 */
.price-info {
  margin-top: 8px;
}

.price-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.price-row.total {
  margin-top: 16px;
}

.price-label {
  font-size: 14px;
  color: #8e8e93;
}

.price-value {
  font-size: 14px;
  color: #333;
}

.price-row.total .price-label {
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.price-row.total .price-value {
  font-size: 18px;
  font-weight: 600;
  color: #ff3b30;
}

/* 底部操作区域 */
.action-area {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  max-width: 430px;
  margin: 0 auto;
  display: flex;
  height: 60px;
  background-color: white;
  box-shadow: 0 -1px 5px rgba(0, 0, 0, 0.1);
  z-index: 90;
  padding: 8px 16px;
  padding-bottom: calc(8px + env(safe-area-inset-bottom));
}

.cancel-button {
  flex: 1;
  height: 44px;
  background-color: white;
  border: 1px solid #8e8e93;
  border-radius: 22px;
  color: #8e8e93;
  font-size: 16px;
  margin-right: 12px;
}

.pay-button {
  flex: 2;
  height: 44px;
  background-color: #ff3b30;
  border: none;
  border-radius: 22px;
  color: white;
  font-size: 16px;
  font-weight: 500;
}

/* 客服提示 */
.service-hint {
  text-align: center;
  font-size: 12px;
  color: #8e8e93;
  margin: 20px 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.service-hint i {
  margin-right: 4px;
}
</style>