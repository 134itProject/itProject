<template>
  <div class="search-results">
    <div class="no-results" v-if="movieList.length === 0 && keyword">
      <i class="el-icon-search"></i>
      <p>未找到与"{{ keyword }}"相关的电影</p>
      <p>请尝试其他关键词</p>
    </div>
    
    <div class="initial-state" v-if="movieList.length === 0 && !keyword">
      <i class="el-icon-film"></i>
      <p>输入电影名称进行搜索</p>
    </div>
    
    <div class="movie-item" v-for="item in movieList" :key="item.movieId" @click="goToMovieDetail(item.movieId)">
      <div class="movie-poster">
        <img :src="item.moviePoster" :alt="item.movieName">
      </div>
      
      <div class="movie-info">
        <div class="movie-name">{{ item.movieName }}</div>
        <div class="movie-category">{{ item.movieCategoryList.join(' / ') }}</div>
        <div class="movie-release">{{ item.releaseDate }}上映</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchMovie",
  props:{
    keyword: {
      type: String,
      default: ''
    }
  },
  data() {
    return{
      movieList: []
    }
  },
  created() {
    if (this.keyword) {
      this.getSearchMovieList();
    }
  },
  methods:{
    async getSearchMovieList(){
      if (!this.keyword.trim()) {
        this.movieList = [];
        return;
      }
      
      try {
        let queryInfo = {
          movieName: this.keyword
        };
        const { data : res } = await axios.get('sysMovie/find', {params : queryInfo});
        
        if(res.code !== 200) {
          this.$message.error('获取信息失败');
          return;
        }
        
        this.movieList = res.data.map(movie => {
          return {
            ...movie,
            moviePoster: this.global.base + JSON.parse(movie.moviePoster)[0],
            movieCategoryList: movie.movieCategoryList.map(obj => obj.movieCategoryName)
          };
        });
      } catch(err) {
        console.error('搜索电影失败', err);
        this.$message.error('搜索电影失败');
      }
    },
    goToMovieDetail(id) {
      this.$router.push('/movieInfo/' + id);
    }
  },
  watch:{
    'keyword'() {
      this.getSearchMovieList();
    }
  }
}
</script>

<style scoped>
.search-results {
  min-height: 400px;
}

.no-results, .initial-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 0;
  color: #8e8e93;
  text-align: center;
}

.no-results i, .initial-state i {
  font-size: 48px;
  margin-bottom: 16px;
  color: #c7c7cc;
}

.no-results p, .initial-state p {
  margin: 4px 0;
  font-size: 15px;
}

.movie-item {
  display: flex;
  background-color: #fff;
  border-radius: 12px;
  padding: 12px;
  margin-bottom: 16px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.movie-poster {
  width: 80px;
  height: 120px;
  border-radius: 6px;
  overflow: hidden;
  margin-right: 16px;
  flex-shrink: 0;
}

.movie-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.movie-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.movie-name {
  font-size: 18px;
  font-weight: 600;
  color: #000;
  margin-bottom: 8px;
}

.movie-category {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.movie-release {
  font-size: 14px;
  color: #8e8e93;
}
</style>