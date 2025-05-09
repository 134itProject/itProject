<template>
  <div class="bill-list-container">
    <div class="empty-state" v-if="billList.length === 0">
      <i class="el-icon-tickets"></i>
      <p>您还没有订单</p>
      <button class="browse-movies-btn" @click="$router.push('/movie')">浏览影片</button>
    </div>
    
    <div class="order-list" v-else>
      <div class="order-card" v-for="item in billList" :key="item.billId">
        <div class="order-header">
          <div class="order-status" :class="{ 'completed': item.payState }">
            {{ item.payState ? '已完成' : '待支付' }}
          </div>
          <div class="order-date">{{ formatDate(item.createTime) }}</div>
        </div>
        
        <div class="order-content" @click="toBillDetail(item.billId)">
          <div class="movie-poster">
            <img :src="item.sysSession.sysMovie.moviePoster" :alt="item.sysSession.sysMovie.movieName">
          </div>
          
          <div class="order-info">
            <div class="movie-name">{{ item.sysSession.sysMovie.movieName }}</div>
            <div class="cinema-name">{{ item.sysSession.sysHall.sysCinema.cinemaName }}</div>
            <div class="hall-info">
              {{ item.sysSession.sysHall.hallName }}({{ item.sysSession.sysHall.hallCategory }})
            </div>
            <div class="show-time">
              {{ formatDate(item.sysSession.sessionDate) }} {{ item.sysSession.playTime }}
            </div>
            <div class="price-info">
              <span class="price">¥{{ item.sysSession.sessionPrice * JSON.parse(item.seats).length }}</span>
              <span class="price-detail">({{ JSON.parse(item.seats).length }}张 x ¥{{ item.sysSession.sessionPrice }})</span>
            </div>
          </div>
        </div>
        
        <div class="order-actions">
          <button class="action-button detail" @click="toBillDetail(item.billId)">
            查看详情
          </button>
          <button class="action-button delete" @click="deleteBill(item.billId)">
            删除订单
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: "BillInfo",
  data() {
    return {
      queryInfo: {
        userId: ''
      },
      billList: []
    }
  },
  created() {
    this.getUser()
    this.getBillList()
  },
  methods: {
    getUser() {
      try {
        const loginUser = JSON.parse(window.sessionStorage.getItem('loginUser'))
        if (loginUser && loginUser.userId) {
          this.queryInfo.userId = loginUser.userId
        } else {
          this.$message.error('获取用户信息失败')
        }
      } catch(err) {
        console.error('获取用户信息失败', err)
        this.$message.error('获取用户信息失败')
      }
    },
    async getBillList() {
      try {
        const { data: res } = await axios.get('sysBill', { params: this.queryInfo })
        if (res.code !== 200) {
          this.$message.error('获取订单列表失败')
          return
        }
        
        this.billList = res.data.map(bill => {
          // 处理电影海报
          if (bill.sysSession.sysMovie.moviePoster) {
            bill.sysSession.sysMovie.moviePoster = this.global.base + JSON.parse(bill.sysSession.sysMovie.moviePoster)[0]
          }
          
          return bill
        })
      } catch(err) {
        console.error('获取订单列表失败', err)
        this.$message.error('获取订单列表失败')
      }
    },
    formatDate(dateString) {
      if (!dateString) return '';
      return moment(dateString).format('YYYY-MM-DD');
    },
    toBillDetail(id) {
      this.$router.push('/billDetail/' + id)
    },
    async deleteBill(id) {
      try {
        await this.$confirm('此操作将永久删除订单信息，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        const { data: res } = await axios.delete('sysBill/' + id)
        if (res.code !== 200) {
          this.$message.error('删除失败！')
          return
        }
        
        this.$message.success('删除成功！')
        await this.getBillList()
      } catch(err) {
        console.error('删除订单失败', err)
        this.$message.info('已取消删除')
      }
    }
  }
}
</script>

<style scoped>
.bill-list-container {
  background-color: #f2f2f7;
  min-height: 100vh;
  padding: 12px;
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #8e8e93;
}

.empty-state i {
  font-size: 48px;
  margin-bottom: 16px;
  color: #c7c7cc;
}

.empty-state p {
  margin: 8px 0 24px;
  font-size: 15px;
}

.browse-movies-btn {
  background-color: #007aff;
  color: white;
  border: none;
  border-radius: 22px;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 500;
}

.order-list {
  padding-bottom: 20px;
}

.order-card {
  background-color: #fff;
  border-radius: 12px;
  margin-bottom: 16px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.order-header {
  display: flex;
  justify-content: space-between;
  padding: 12px 15px;
  border-bottom: 1px solid #f2f2f7;
}

.order-status {
  font-size: 14px;
  font-weight: 500;
  color: #f03d37;
}

.order-status.completed {
  color: #34c759;
}

.order-date {
  font-size: 13px;
  color: #8e8e93;
}

.order-content {
  display: flex;
  padding: 15px;
  border-bottom: 1px solid #f2f2f7;
}

.movie-poster {
  width: 60px;
  height: 90px;
  margin-right: 15px;
  border-radius: 6px;
  overflow: hidden;
  flex-shrink: 0;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.order-info {
  flex: 1;
}

.movie-name {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
}

.cinema-name {
  font-size: 14px;
  color: #333;
  margin-bottom: 4px;
}

.hall-info, .show-time {
  font-size: 13px;
  color: #8e8e93;
  margin-bottom: 4px;
}

.price-info {
  margin-top: 8px;
}

.price {
  font-size: 16px;
  font-weight: 600;
  color: #f03d37;
}

.price-detail {
  font-size: 12px;
  color: #8e8e93;
  margin-left: 5px;
}

.order-actions {
  display: flex;
  padding: 10px 15px;
  justify-content: flex-end;
}

.action-button {
  padding: 6px 12px;
  border-radius: 15px;
  font-size: 13px;
  border: none;
  margin-left: 10px;
}

.action-button.detail {
  background-color: #fff;
  color: #007aff;
  border: 1px solid #007aff;
}

.action-button.delete {
  background-color: #fff;
  color: #f03d37;
  border: 1px solid #f03d37;
}
</style>