<template>
	<view>
		<view class="movie-top">
			<view class="top-region" @click="getShow">
				<text>{{cityInfo.city}}</text>
				<image src="../../static/images/movie-xiala.png"></image>
			</view>
			<view class="top-tab-list">
				<view class="tab-list-items" @click="gettab(value.type,index)" :class="{'sel-item':value.type==tanIndex}" v-for="(value,index) in tabLists"
					:key="index">
					{{value.text}}
					<view class="cut-img" v-if="tanIndex==value.type"></view>
				</view>
			</view>
			<image src="../../static/images/movie-serchs.png" class="sechut"></image>
		</view>
		
		<!-- 轮播盒子 start -->
		<view class="swiper_main">
			<image class="background-img-vague" :src="chooseMovice.posterUrl" mode=""></image>
			<view class="wrap">
				<swipers :list="movieList" height="260" mode="none" name="posterUrl" :autoplay="false" img-mode="scaleToFill" class="swiper_bg"
				 :effect3d="true" bg-color="none" :circular="false" effect3d-previous-margin="280" @change="setBackground" @click="setBackground" :current="swiperCurrent"></swipers>
			</view>
			<div class="box">
				
			</div>
		</view>
		<!-- 轮播盒子 end -->

		<view class="flow-moive-name">{{chooseMovice.movieName}}</view>
		<view class="flow-moive-info">{{chooseMovice.duration}}分钟{{chooseMovice.movieType}}{{chooseMovice.cast}}</view>
		
		<view class="movie-main">
			<view class="movie-list" v-for="(value,index) in dataList" :key="index" @click="getUrl(value)">
				<view class="movie-img">
					<image :src="value.posterUrl"></image>
				</view>
				<view class="movie-info">
					<view class="mo-info-top">
						<view class="tiel">{{value.movieName}}</view>
						<view class="movie-d"><text class="zimu">IMAX</text><text class="shuzi">2D</text></view>
					</view>
					<view class="rate-item">
						<u-rate :count="5" v-model="value.grade" active-color="#FF6E06" :size="30"></u-rate>
						<text class="rate-nun">{{value.grade}}</text>
					</view>
					<view class="director">导演：{{value.director}}</view>
					<view class="to-star">主演：{{value.cast}}</view>
					
					<view class="calss-item">
						<view class="classify-item">{{value.duration}}分钟</view>
					</view>
				</view>
				<view class="movie-btns" v-if="tanIndex==1">购票</view>
			</view>
		</view>
		
		
		<view class="movie-boom">
			<view class="movie-boom-item">
				<image src="https://hijinka.oss-cn-shanghai.aliyuncs.com/uploads/mall1/20220308/a55f9bd3766cde52ec9dba8ade8c83d3.png"></image>
				<view class="boom-item-text" style="color: #FE602B;">电影</view>
			</view>
			<view class="movie-boom-item" @click="getMy">
				<image src="https://hijinka.oss-cn-shanghai.aliyuncs.com/uploads/mall1/20220308/51ac5b0cd61fddc59d10c0a10791961d.png"></image>
				<view class="boom-item-text">我的</view>
			</view>
		</view>
	</view>
</template>

<script>
	// import {filmList,getCity} from "@/api/film.js"
	import swipers from '@/components/u-swiper.vue'
	import config from "@/utils/config.js"
	export default {
		components:{swipers},
		data() {
			return {
				movieList: [
					{
						movieId:12343,
						duration:149,
						movieType:'剧情|历史|战争',
						cast:'吴京 易烊千玺 段奕宏 张涵予 朱亚文',
						movieName:'长津湖之水门桥',
						posterUrl: "https://gw.alicdn.com/i1/O1CN01sSmj2b1daSm6IAUcs_!!6000000003752-0-alipicbeacon.jpg_480x480Q30s150.jpg"
					},
					{
						cast: "易烊千玺 田雨 陈哈琳 齐溪 公磊 许君聪 王宁 黄尧 巩金国",
						duration: 106,
						movieId: 147885,
						movieName: "奇迹·笨小孩",
						movieType: "剧情",
						posterUrl: "https://gw.alicdn.com/i1/O1CN013Ggc2s1Z8HwrwxAfn_!!6000000003149-0-alipicbeacon.jpg_480x480Q30s150.jpg"
					}
					
				],
				//swiper默认选中
				swiperCurrent: 0,
				//选中的电影
				chooseMovice: {
					movieId:'',
					duration:149,
					movieType:'剧情|历史|战争',
					cast:'吴京 易烊千玺 段奕宏 张涵予 朱亚文'
				}, 
				tabLists: [{
						text: '正在热映',
						type:1,
						isLoad:false,
						list:[]
					},
					{
						text: '即将上映',
						type:2,
						isLoad:false,
						list:[]
					}
				],
				tanIndex: 1,
				rateValue: 3,
				dataList:[
					{
						posterUrl: "https://gw.alicdn.com/i1/O1CN01sSmj2b1daSm6IAUcs_!!6000000003752-0-alipicbeacon.jpg_480x480Q30s150.jpg",
						movieName: "长津湖之水门桥",
						grade: "9.6",
						cast: "吴京 易烊千玺 段奕宏 张涵予 朱亚文 李晨 韩东君 耿乐 杜淳",
						director: "徐克",
						duration: 149,
						publishDate: "2022-02-01 08:00:00",
						like: 1155844,
						movieType: "剧情|历史|战争"
					},
					{
						posterUrl: "https://gw.alicdn.com/i1/O1CN013Ggc2s1Z8HwrwxAfn_!!6000000003149-0-alipicbeacon.jpg_480x480Q30s150.jpg",
						movieName: "奇迹·笨小孩",
						grade: "9.6",
						cast: "易烊千玺 田雨 陈哈琳 齐溪 公磊 许君聪 王宁 黄尧 巩金国",
						director: "文牧野",
						duration: 106,
						publishDate: "2022-02-01 08:00:00",
						like: 686060,
						movieType: "剧情"
					},
				],
				index:0,
				show:false,
				cityList:[],
				keyWord:'',
				cityShowList:[],
				isLoad:false,
				cityInfo:{
					city:'大连民族大学'
				},
				wellList:[],//所有热映数据
				soonList:[],//所有即将上映数据
				page:1
			}
		},
		onLoad() {
			this.setBackground(0);
			// this.getList()
			// this.getCityList()
		},
		onReachBottom() {
			// this.page++
			// if(this.tanIndex==1){
			// 	this.dataList = this.dataList.concat(config.pageDataFn(this.page,8,this.wellList))
			// }else{
			// 	this.dataList = this.dataList.concat(config.pageDataFn(this.page,8,this.soonList))
			// }
		},
		methods: {
			// 切换轮播改变背景
			setBackground(index) {
				console.log(index)
				this.swiperCurrent = index
				this.chooseMovice = this.movieList[index]
				// this.getScheduling()
			},
			//跳转到电影院列表
			getUrl(data){
				if(this.cityInfo.city=='定位中'){
					this.$toast("请选择位置信息")
					return false
				}
				let info = {
					position:this.cityInfo,
					info:data
				}
				console.log(info)
				uni.navigateTo({
					url:'/pages/movie/cinema-list',
					success: function(res) {
					    // 通过eventChannel向被打开页面传送数据
						res.eventChannel.emit('filmDataFromOpene', { data: info})
					}
				})
			},
			gettab(type,index){
				this.index = index
				if(this.tanIndex!=type){
					this.tanIndex=type
				}
			},
			getMy(){
				uni.redirectTo({
					url:'/pages/movie/moive-my'
				})
			},
		}
	}
</script>

<style lang="less">
	page {
		background-color: #F4F5F7;
		padding-bottom: 120rpx;
	}
	.flow-moive-name{
			text-align: center;
			margin-top: 30rpx;
			color: #2D3039;
			font-size: 36rpx;
			font-weight: 600;
		}
		.flow-moive-info{
			text-align: center;
			margin-top: 14rpx;
			color: #636775;
			font-size: 24rpx;
		}
	.movie-top {
		display: flex;
		align-items: center;
		position: fixed;
		// #ifdef  H5
		top: 88rpx;
		// #endif
		// #ifdef  MP-WEIXIN
		top: 0;
		// #endif
		left: 0;
		right: 0;
		background-color: #FFFFFF;
		padding: 30rpx 32rpx;
		z-index: 9999;
	}

	.top-region {
		display: flex;
		align-items: center;

		text {
			color: #636775;
			font-size: 28rpx;
		}

		image {
			width: 24rpx;
			height: 24rpx;
			margin-left: 8rpx;
		}
	}

	.top-tab-list {
		margin-left: 110rpx;
		display: flex;
		align-items: center;

		.tab-list-items {
			color: #636775;
			font-size: 28rpx;
			padding: 0rpx 25rpx;
			position: relative;
		}

		.sel-item {
			color: #2D3039 !important;
			font-size: 32rpx !important;
			font-weight: 600;
		}

		.cut-img {
			width: 52rpx;
			height: 8rpx;
			position: absolute;
			bottom: -30rpx;
			right: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			background-color: #FE602B;
		}
	}

	.sechut {
		margin-left: auto;
		width: 44rpx;
		height: 44rpx;
	}

	.movie-main {
		// margin-top: 130rpx;
	}

	.movie-list {
		margin: 20rpx 25rpx 0rpx 25rpx;
		background-color: #FFFFFF;
		border-radius: 20rpx;
		padding: 28rpx 32rpx;
		display: flex;
		align-items: center;

		.movie-img {
			width: 180rpx;
			height: 248rpx;

			image {
				width: 180rpx;
				height: 248rpx;
				border-radius: 20rpx;
			}

		}
		

		.movie-info {
			margin-left: 24rpx;
			width: 50%;
			.mo-info-top {
				display: flex;
				align-items: center;

				.tiel {
					color: #2D3039;
					font-size: 32rpx;
					font-weight: 600;
				}

				.movie-d {
					margin-left: 20rpx;
					border-radius: 6rpx;
					border: 1rpx solid #C4C4C4;
					color: #A6A8AE;
					font-size: 17rpx;

					.zimu {
						padding: 2rpx 6rpx;
					}

					.shuzi {
						background-color: #C4C4C4;
						padding: 0rpx 6rpx;
						color: #FFFFFF;
					}
				}
			}
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

	.director {
		color: #636775;
		font-size: 24rpx;
		margin-top: 16rpx;
	}
	.to-star{
		color: #787A82;
		font-size: 24rpx;
		margin-top: 10rpx;
		overflow:hidden;
		text-overflow:ellipsis;
		white-space:nowrap;
	}
	.calss-item{
		display: flex;
		align-items: center;
		margin-top: 12rpx;
		.classify-item{
			padding: 6rpx 12rpx;
			border-radius: 6rpx;
			border: 2rpx solid #DADCE4;
			color: #A6A8AE;
			font-size: 20rpx;
		}
	}
	.movie-btns{
		margin-left: auto;
		width: 110rpx;
		height: 58rpx;
		background: linear-gradient(207deg, #FF9272 0%, #FC5421 100%);
		border-radius: 29rpx;
		text-align: center;
		line-height: 58rpx;
		color: #FFFFFF;
		font-size: 24rpx;
		font-weight: 550;
	}
	.region-top{
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 30rpx 20rpx;
		.region-left{
			width: 75%;
			border-radius: 40rpx;
			height: 68rpx;
			background-color: #F5F6F8;
			display: flex;
			align-items: center;
			padding-left: 30rpx;
		}
		.region-val{
			width: 90%;
			font-size: 30rpx;
		}
		.region-btn{
			background: linear-gradient(207deg, #FF9272 0%, #FC5421 100%);
			border-radius: 50rpx;
			height: 68rpx;
			text-align: center;
			width: 140rpx;
			line-height: 68rpx;
			color: #FFFFFF;
			font-size: 24rpx;
			font-weight: 550;
		}
	}
	.region-list{
		padding: 22rpx 35rpx;
		color: #2D3039;
		font-size: 32rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		border-bottom: 2rpx solid #E2E2E2;
	}
	.empty{
		text-align: center;
		padding-top: 30rpx;
		color: #787A82;
		font-size: 24rpx;
	}
	
	.movie-boom{
		display: flex;
		align-items: center;
		padding-top: 16rpx;
		padding-bottom: 4rpx;
		background-color: #FFFFFF;
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		.movie-boom-item{
			width: 50%;
			text-align: center;
			image{
				width: 48rpx;
				height: 48rpx;
			}
			.boom-item-text{
				color: #A2A4AA;
				font-size: 20rpx;
			}
		}
	}
	// 轮播图中间
			.swiper_main {
				margin-top: 10vh;
				position: relative;
				height: 324rpx;
				overflow: hidden;
	
				.background-img-vague {
					position: absolute;
					left: 0;
					right: 0;
					width: 100%;
					// transform: translateX(-50%);
					filter: blur(15px);
					-webkit-filter: blur(15px);
				}
	
				/deep/.u-swiper-item {
					width: 193rpx !important;
					height: 260rpx !important;
				}
	
				// 影院背景图
				.moviebg {
					width: 100%;
					height: 100%;
					filter: blur(10rpx);
				}
	
				.wrap {
					position: absolute;
					left: 0;
					top: 26rpx;
					right: 0;
					margin: 0 auto;
				}
	
				.box {
					width: 0;
					height: 0;
					border: 10rpx solid;
					border-color: transparent transparent #fff #fff;
					transform: rotate(135deg);
					position: absolute;
					bottom: -10rpx;
					left: 0;
					right: 0;
					margin: 0 auto;
				}
	
			}
</style>
