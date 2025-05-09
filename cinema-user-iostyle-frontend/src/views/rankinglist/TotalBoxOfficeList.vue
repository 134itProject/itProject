<template>
  <div class="box-office-list">
    <div class="movie-item" v-for="(item, index) in totalBoxOfficeList" :key="item.movieId">
      <div class="rank-number" :class="{'top-three': index < 3}">{{ index + 1 }}</div>
      
      <div class="movie-poster" @click="goToMovieInfo(item.movieId)">
        <img :src="global.base + JSON.parse(item.moviePoster)[0]" :alt="item.movieName">
      </div>
      
      <div class="movie-info">
        <div class="movie-title" @click="goToMovieInfo(item.movieId)">{{ item.movieName }}</div>
        <div class="movie-date">上映时间：{{ formatDate(item.releaseDate) }}</div>
      </div>
    </div>
    
    <div class="empty-state" v-if="totalBoxOfficeList.length === 0">
      <i class="el-icon-data-line"></i>
      <p>暂无榜单数据</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "TotalBoxOfficeList",
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 10
      },
      totalBoxOfficeList: []
    }
  },
  created() {
    this.getTotalBoxOfficeList()
  },
  methods: {
    async getTotalBoxOfficeList() {
      try {
        const { data: resp } = await axios.get('sysMovie/find/rankingList/1', { params: this.queryInfo })
        if (resp.code !== 200) {
          this.$message.error(resp.msg)
          return
        }
        this.totalBoxOfficeList = resp.data
      } catch(err) {
        console.error('获取票房榜单失败', err)
        this.$message.error('获取票房榜单失败')
      }
    },
    goToMovieInfo(id) {
      this.$router.push('/movieInfo/' + id)
    },
    formatDate(dateString) {
      if (!dateString) return '';
      return dateString.split(" ")[0];
    }
  }
}
</script>

<style scoped>
.box-office-list {
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.movie-item {
  display: flex;
  padding: 15px;
  align-items: center;
  border-bottom: 1px solid #f2f2f7;
}

.movie-item:last-child {
  border-bottom: none;
}

.rank-number {
  width: 28px;
  height: 28px;
  background-color: #f2f2f7;
  border-radius: 14px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 14px;
  font-weight: 600;
  color: #8e8e93;
  margin-right: 12px;
  flex-shrink: 0;
}

.rank-number.top-three {
  background-color: #ffb400;
  color: white;
}

.movie-poster {
  width: 60px;
  height: 90px;
  margin-right: 12px;
  border-radius: 6px;
  overflow: hidden;
  flex-shrink: 0;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-info {
  flex: 1;
}

.movie-title {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.movie-date {
  font-size: 13px;
  color: #8e8e93;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 50px 0;
  color: #8e8e93;
}

.empty-state i {
  font-size: 48px;
  margin-bottom: 10px;
  color: #c7c7cc;
}

.empty-state p {
  font-size: 14px;
}
</style>