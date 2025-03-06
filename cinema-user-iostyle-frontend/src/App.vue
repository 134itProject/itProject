<template>
<div id="app">
<router-view></router-view>
<bottom-navigation v-if="showNavigation"></bottom-navigation>
</div>
</template>
<script>
import './assets/css/global.css'
import BottomNavigation from './components/common/BottomNavigation.vue'
export default {
name: 'app',
components: {
BottomNavigation
},
computed: {
showNavigation() {
// 不在登录和注册页面显示底部导航
const hiddenPaths = ['/login', '/register','/billDetail','/error404','/chooseSeat'];
// 检查当前路径是否在隐藏列表中
const currentPath = this.$route.path;
return !hiddenPaths.some(path => currentPath.startsWith(path));
}
}
}
</script>
<style>

body {
margin: 0;
padding: 0;
background-color: #f6f6f6;
}
#app {
font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Arial, sans-serif;
-webkit-font-smoothing: antialiased;
-moz-osx-font-smoothing: grayscale;
color: #2c3e50;
}
</style>