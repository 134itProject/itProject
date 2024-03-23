<template>
  <view>
  
    <view class="cinema-trailer">
      <!-- 预告片视频或封面图等展示区域 -->
	 <!-- <uv-swiper   :list="filmInfo.mp4list" previousMargin="30" nextMargin="30" circular
	  			radius="5" height='30vh'  showTitle :title-style="{color:'white',textAlign: 'left',:'0rpx'}" 
	  bgColor="#ffffff" keyName="url" :autoplay="false"></uv-swiper> -->
      <video class="_video" :direction="90" :src="filmInfo.trailerUrl" controls></video>
    </view>
	<view class="msgBox" style="height: 100vh;width: 100vw;">
	<!-- 电影卡片 -->
	<view class="movie-box">
    <uni-card class="cinema-top" :isFull="true" ref="cinemaTop" >
      <view class="tops-item" >
        <view class="cinema-img">
          <image :src="filmInfo.coverUrl"></image>
        </view>
        <view class="cinema-info">
          <view class="cinema-info-top">
            <view class="top-name">{{ filmInfo.movieName }}</view>
            <view class="top-type">3D IMAX</view>
			
          </view>
		 
          <view class="rate-item">
			 <uni-rate :size="18" :is-fill="false" v-model="filmInfo.grade" :readonly="true" :value="3" />
            <!-- <u-rate :count="5" :disabled="true" v-model="filmInfo.grade" inactive-color="#ffffff" active-color="#FF6E06" :size="30"></u-rate> -->
            <text class="rate-nun">{{ filmInfo.grade }}</text>
			<uni
			
          </view>
          <view class="list-type">{{ filmInfo.movieType }}|{{ filmInfo.duration }}分钟</view>
          <view class="list-type">{{ filmInfo.publishDate }} 上映</view>
          <view class="list-type"><text>{{ filmInfo.like }}</text>人想看</view>
	  <view class="loveMovie">
		  <uni-fav :checked="!checkList[0]" class="favBtn" :circle="false" bg-color="#e8e8e8"
								bg-color-checked="#e7dbba" fg-color="#000000" fg-color-checked="#ce8900" @click="favClick(0)" />
		 <uni-fav :checked="!checkList[1]" :content-text="contentText" class="uni-fav1" :circle="false" bg-color="#000000"
		 								bg-color-checked="#ff0000" fg-color="#ffffff" fg-color-checked="#ffffff" @click="favClick(1)" />
		 
    </view>
		</view>
      </view>
	   </uni-card>
	  </view>
	  
	  <!-- 导航 -->
	  <u-sticky>
			<u-tabs style="width: 100vw;" :list="list1" :is-scroll="false" :current="current" @change="handleTabChange"></u-tabs>
			<!-- 简介	 -->
			<u-read-more v-if="current===0">
					<rich-text :nodes="filmInfo.content"></rich-text>
			</u-read-more>
			<!-- 演职人员 -->
			<u-read-more v-else-if="current===1">
					<rich-text :nodes="filmInfo.player"></rich-text>
			</u-read-more>
			<!-- 剧照 瀑布流-->
			<custom-waterfalls-flow v-else-if="current===3" ref="waterfallsFlowRef" :value="filmInfo.posterUrl"></custom-waterfalls-flow>
			<!-- 视频 -->
			<uv-swiper  v-else-if="current===4" :list="filmInfo.mp4list" previousMargin="30" nextMargin="30" circular  
						radius="5" height='30vh'  showTitle :title-style="{color:'white',textAlign: 'left' }" 
			bgColor="#ffffff" keyName="url" :autoplay="false"></uv-swiper>
	</u-sticky>
  </view>
  </view></view>
</template>

<script>
export default {
  data() {
    return {
		options: {
			effect: 'coverflow',
			centeredSlides: true,
			slidesPerView: 'auto',
			coverflowEffect: {
				rotate: 50,
				stretch: 0,
				depth: 100,
				modifier: 1,
				slideShadows: true,
			},
		},
		 // lineBg: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAOCAYAAABdC15GAAAACXBIWXMAABYlAAAWJQFJUiTwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAFxSURBVHgBzZNRTsJAEIb/WTW+lpiY+FZPIDew3ABP4GJ8hxsI9zBpOYHeQDwBPQI+mRiRvpLojtPdYhCorQqF/6GdbGd2vvwzBXZcNAt4oj1ANeUoAT5iqkUjbEFLHNmhD1YPEvpZ3ghkGlVDCkc94/BmHMq998I5ONiY1ZBfpKAyuOtgAc5yOEDmYEWNh32BHF91sGHZHmwW4azciN9aQwnz3SJEgOmte+R2tdLprTYoa50mvuomlLpD4Y3oQZnov6D2RzCqI93bWOHaEmAGqQUyRBlZR1WfarcD/EJ2z8DtzDGvsMCwpm8XOCfDUsVOCYhiqRxI/CTQo4UOvjzO7Pow18vfywneuUHHUUxLn55lLw5JFpZ8bEUcY8oXdOLWiHLTxvoGpLqoUmy6dBT15o/ox3znpoycAmxUsiJTbs1cmxeVKp+0zmFIS7bGWiVghC7Vwse8jFKAX9eljh4ggKLLv7uaQvG9/F59Oo2SouxPu7OTCxN/s8wAAAAASUVORK5CYII=",
	 current: 0, // 初始化 底部导航选项 current 值
	  list1: [{
						name: '简介',
					}, {
						name: '演职人员',
					},{
						name:'影评'
					},{
						name:'剧照'
					} ,
					{
						name:'视频'
					}
					],
	checkList: [false, false],//按钮选中
	rateValue: 0,//评分	
      contentText:{
		  contentDefault: '看过',
		contentFav: '已看过'
	  },
      filmInfo: {
        movieName: '你想活出怎样的人生',
        posterUrl:  [
					 { image: '//gw.alicdn.com/i3/O1CN01XlPSp21zOU5esR42g_!!6000000006704-0-alipicbeacon.jpg_600x600Q30.jpg_.webp'}, 
					 { image: '//gw.alicdn.com/i4/O1CN01BPIs4x1DZqMJykSRh_!!6000000000231-0-alipicbeacon.jpg_600x600Q30.jpg_.webp' },
					 {image: 'https://gw.alicdn.com/i3/O1CN01GTJU2v1bY3fFocDtV_!!6000000003476-0-alipicbeacon.jpg_600x600Q30.jpg_.webp'},
					{ image: '//gw.alicdn.com/i1/O1CN01Iv8xGU1D8ozUdqnpZ_!!6000000000172-0-alipicbeacon.jpg_600x600Q30.jpg_.webp' },
					{ image: '//gw.alicdn.com/i1/O1CN01AXZij61eLBJ1VCVbR_!!6000000003854-0-alipicbeacon.jpg_600x600Q30.jpg_.webp' },
					{image: 'https://gw.alicdn.com/i3/O1CN01GTJU2v1bY3fFocDtV_!!6000000003476-0-alipicbeacon.jpg_600x600Q30.jpg_.webp'}	 
						 
						 ],
        grade: 4,
        movieType: '剧情',
        duration: 120,
        publishDate: '2024-01-01',
        like: 100,
        liked: false,
        trailerUrl: '../../static/movieforecast/life.mp4',
        coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
		content:'影片简介',
		player:'影片演职人员',
		mp4list:[ 
					{
						url: '../../static/movieforecast/life.mp4',
						title: '奥斯卡最佳动画 宫崎骏《你想活出怎样的人生》定档4.3',
						poster: '//gw.alicdn.com/i4/O1CN018w6LJl1nb3duYpSMX_!!6000000005107-0-alipicbeacon.jpg_400x400Q30.jpg_.webp',
						type: 'video'
					},{
					url: 'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					title: '身无彩凤双飞翼，心有灵犀一点通',
					type: 'image'
				},
					],
      }
    }
  },
  onLoad() {
  	
  },
   mounted() {
      
	  
    },
    methods: {
	handleTabChange(index) {
				console.log(index);
		      this.current = index; // 在切换事件回调中更新 current 值
		    },
      favClick(index) {
      				this.checkList[index] = !this.checkList[index]
      				console.log(this.checkList[index]);
      				this.$forceUpdate()
      			}
    }
  
}
</script>


<style lang="less">
.sticky {
		width: 750rpx;
		height: 120rpx;
		background-color: #2979ff;
		color: #fff;
		padding: 24rpx;
	}
.movie-box{
	// position: absolute;
	
	background-color:  #eeeeee; 
	border-radius: 20rpx;
	top: 200rpx;
	left: 0;
	right: 0;
	// padding: 0rpx 10rpx;
}	

.loveMovie{
	display: flex;
	padding: 10px 15px;
	padding-left: 0;
	justify-content: space-between;
	.uni-fav{
		width: 25vw;
	}
	.uni-fav1 {
		margin-left: 30rpx;
	}
		
}	
.cinema-trailer {
border-radius: 6rpx;
  width: 100vw;
  height: 30vh; /* 修改为自适应高度 */
  ._video {
    width: 100%;
    height: 100%; /* 修改为占满父元素的高度 */
  }
}
.movie-box[data-v-34afa719]{
  	background-color: #ffffff;
	
  }
.cinema-top {
  // background-color: #636775;
 
  padding: 32rpx;
  position: relative;
  
	.tops-item{
		display: flex;
		align-items: center;
		z-index: 9999;
	}
	.btins{
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		width: 100%;
		height: 100%;
		// z-index: 99;
		filter: blur(15px);
		-webkit-filter: blur(15px);
	}
	.cinema-img {
		 border-radius: 6rpx;
		 // background-color: #FF6E06;
	  width: 250rpx;
	  height: 248rpx;
	 
	  image {
		  
	    width: 200rpx;
	    height: 250rpx;
	    border-radius: 20rpx;
	  }
	}
	.cinema-info{
		margin-left: 30rpx;
		.cinema-info-top {
		  display: flex;
		  align-items: center;
		  .top-name {
		    color: #000000;
		    font-size: 30rpx;
		    font-weight: 600;
		  }
		  .top-type {
		    background-color: #eaeaea;
		    border-radius: 6rpx;
		    padding: 2rpx 8rpx;
		    color: #000000;
		    font-size: 25rpx;
		    margin-left: 30rpx;
		  }
		}
		
		.rate-item {
			display: flex;
			align-items: center;
			margin-top: 16rpx;
			.rate-nun {
				color: #FF6E06;
				font-size: 28rpx;
				font-weight: 550;
				margin-left: 16rpx;
			}
		
		}
	}
}
</style>