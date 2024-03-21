<template>
	<view>
	
	<!-- <uni-card :isfull="true"> -->
	<!-- 影片列表 -->
	<!-- <uni-card title="正在热映" extra="" :isFull="true"> -->
		<div class="swiper-slide" v-for="(item, index) in movieinfolist" :key="index">
		
		<uni-card class="cinema-top" :isFull="true" ref="cinemaTop" >
		  <view class="tops-item" >
		    <view class="cinema-img">
		      <image :src="item.posterUrl"></image>
		    </view>
		    <view class="cinema-info">
		      <view class="cinema-info-top">
		        <view class="top-name">{{ item.movieName }}</view>
		        <view class="top-type">3D IMAX</view>
				
		      </view>
			 
		      <view class="rate-item">
				 <uni-rate :size="18" :is-fill="false" v-model="item.grade" :readonly="true" :value="3" />
		        <!-- <u-rate :count="5" :disabled="true" v-model="filmInfo.grade" inactive-color="#ffffff" active-color="#FF6E06" :size="30"></u-rate> -->
		        <text class="rate-nun">{{ item.grade }}</text>
				<uni
				
		      </view>
		      <view class="list-type">{{ item.movieType }}|{{ item.duration }}分钟</view>
		      <view class="list-type">{{ item.publishDate }} 上映</view>
		      <view class="list-type"><text>{{ item.like }}</text>人想看</view>
				<uni-fav :checked="checked_value" class="favBtn" :circle="true" bg-color="#dd524d"
									bg-color-checked="#ff0000" fg-color="#ffffff" fg-color-checked="#ffffff" @click="check_click" />
			</view>
		  </view>
		   </uni-card>
		 
		</div>
	
	</view>
	
</template>

<script>
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.min.css';
	import city from '@/utils/city.js'
	export default {
		data() {
			return {
				checked_value:false,
				list: city.list,
				type: 'left',//城市弹窗
				cityname:'重庆',
				searchValue: '',
				movieinfolist:[
					{
					  movieName: '你想活出怎样的人生',
					  posterUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
					  grade: 4,
					  movieType: '剧情',
					  duration: 120,
					  publishDate: '2024-01-01',
					  like: 100,
					  liked: false,
					  trailerUrl: '../../static/movieforecast/life.mp4',
					  coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp'
					},
					{
						movieName: '你想活出怎样的人生',
						posterUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
						grade: 4,
						movieType: '剧情',
						duration: 120,
						publishDate: '2024-01-01',
						like: 100,
						liked: false,
						trailerUrl: '../../static/movieforecast/life.mp4',
						coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp'
					},
					{
						movieName: '你想活出怎样的人生',
						posterUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
						grade: 4,
						movieType: '剧情',
						duration: 120,
						publishDate: '2024-01-01',
						like: 100,
						liked: false,
						trailerUrl: '../../static/movieforecast/life.mp4',
						coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp'
					},
					{
						movieName: '你想活出怎样的人生',
						posterUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
						grade: 4,
						movieType: '剧情',
						duration: 120,
						publishDate: '2024-01-01',
						like: 100,
						liked: false,
						trailerUrl: '../../static/movieforecast/life.mp4',
						coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp'
					},
					{
						movieName: '你想活出怎样的人生',
						posterUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
						grade: 4,
						movieType: '剧情',
						duration: 120,
						publishDate: '2024-01-01',
						like: 100,
						liked: false,
						trailerUrl: '../../static/movieforecast/life.mp4',
						coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp'
					},
					{
						movieName: '你想活出怎样的人生',
						posterUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp',
						grade: 4,
						movieType: '剧情',
						duration: 120,
						publishDate: '2024-01-01',
						like: 100,
						liked: false,
						trailerUrl: '../../static/movieforecast/life.mp4',
						coverUrl: '//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_320x320Q75.jpg_.webp'
					},
				],
				movielist:[
					{
						'movie-url':'https://gw.alicdn.com/i1/O1CN01WBx9mv1dKtVdfuoZN_!!6000000003718-0-alipicbeacon.jpg_300x300Q30.jpg_.webp',
						'movie-name':'下次一定',
						'movie-score':'3',
					},
					{
						'movie-url':'https://gw.alicdn.com/i1/O1CN01aecCQf1MK7MyiR4mP_!!6000000001415-0-alipicbeacon.jpg_300x300Q30.jpg_.webp',
						'movie-name':'下次一定','movie-score':'3',
					},
					{
						'movie-url':'//gw.alicdn.com/i3/O1CN01JfQQxY1xDNJakaXHZ_!!6000000006409-0-alipicbeacon.jpg_300x300Q30.jpg_.webp',
						'movie-name':'下次一定','movie-score':'3',
					},
					{
						'movie-url':'//gw.alicdn.com/i4/O1CN01aT7ZNc1gJvVjTxn8k_!!6000000004122-0-alipicbeacon.jpg_300x300Q30.jpg_.webp',
						'movie-name':'下次一定','movie-score':'3',
					},
					{
						'movie-url':'//gw.alicdn.com/i2/O1CN01oBhfpu25YSToJdUnp_!!6000000007538-0-alipicbeacon.jpg_300x300Q30.jpg_.webp',
						'movie-name':'下次一定','movie-score':'3',
					},
					{
						'movie-url':'//gw.alicdn.com/i4/O1CN01Ij5Z421cBRihslor8_!!6000000003562-0-alipicbeacon.jpg_300x300Q30.jpg_.webp',
						'movie-name':'下次一定','movie-score':'3',
					},
				],
				info: [
					{
						url: '../../static/images/indexpic1.jpg',
						content: '内容 A'
					},
					{
						url: '../../static/images/indexpic2.jpg',
						content: '内容 B'
					}, 
					{
						url: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/shuijiao.jpg',
						content: '内容 C'
					},
					],
				current: 0,
				
				mode: 'default'
				,routes: [
				        {
				          to: "/A",
				          name: "首页",
				        },
				        {
				          to: "/B",
				          name: "影片",
				        },
				        {
				          to: "/pages/movie/moive-my",
				          name: "个人",
				        },
				      ],
			}
		},
		onLoad() {

		},
		mounted() {
			 this.swiper = new Swiper('.swiper-container', {
			      direction: 'horizontal',
			      slidesPerView: 'auto',
			      spaceBetween: 0,
			      grabCursor: true,
				   
			    })
		},
		methods: {
			check_click(){
				this.checked_value = !this.checked_value;
			},
			 focus() {
			        console.log('Input focused');
			    },
			    blur() {
			        console.log('Input blurred');
			    },
			    search() {
			        console.log('Searching...');
			    },
			    input() {
			        console.log('Input changed');
			    },
			    cancel() {
			        console.log('Search cancelled');
			    },
			    clear() {
			        console.log('Input cleared');
			    },
			bindClick(e) {
							console.log('点击item，返回数据' + JSON.stringify(e))
							this.cityname=e.item.name;
						},
			changes(e) {
				console.log('当前模式：' + e.type + ',状态：' + e.show);
			},
			toggle(type) {
				this.type = type
				// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
				this.$refs.popup.open(type)
						},
			change(e) {
				this.current = e.detail.current;
				
			},
			getIndex(){
				//返回电影首页
				uni.redirectTo({
					url:'/pages/index/index'
				})
			},
			getHome(){
				//返回购票首页
				uni.redirectTo({
					url:'/pages/movie/index'
				})
			},
			getMy(){
				//返回个人中心
				uni.redirectTo({
					url:'/pages/movie/movie-my'
				})
			},
		}
	}
</script>

<style lang="less">

.ui-card{
	border-radius: 25rpx;
	.header{
		// justify-content: space-between;
		display: flex;
		align-items: center;
		width: 100vw;
		color: #000000;
		.text{
			color: aqua;
			font-size: auto;
		}
		.searchBox{
			width:auto ;
		}
	}
	}
	.swiper-box{
		height: 14vh;
		
	.background-img-vague {
		
		border-radius: 20rpx;
		position: absolute;
		left: 0;
		right: 0;
		height: 14vh;
		width: 100%;
		
				}
		}
	.header-nav{
		padding-top: 15px;
		
		display: flex;
		font-size: 26rpx;
		height: var(--status-bar-height);
		width: 100vw;
		// 
		
		.uni-breadcrumb-item{
			// justify-content: space-between;
			width:100vw;
			padding-left: 0%;
			font-size: 35rpx;
			
			
		}
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
	  width: 170rpx;
	  height: 248rpx;
	  image {
		  
	    width: 180rpx;
	    height: 248rpx;
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
		    font-size: 32rpx;
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
