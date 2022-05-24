<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <img style="height: 60px; padding-top: 5px;" src="../assets/img/logo.png" alt="嗨阅读">
    </div>
    <!-- 作者 -->
    <ul class="navbar" v-if="userType == 'WRITER'">
      <li :class="{active: item.name == activeName}" v-for="item in writerNavMsg" :key="item.path" @click="goPage(item.path,item.name)">
        {{item.name}}
      </li>
      <li>
        <div class="header-search">
          <input type="text" placeholder="搜索书籍或作家" id="seach" @keyup.enter="goSearch()" v-model="keywords">
          <div class="search-btn" @click="goSearch()">
            <span class="iconfont icon-Search" style="font-size:25px;"></span>
          </div>
        </div>
      </li>
      <li class="vip" v-if="!vip" @click="turnVip">
        成为VIP
      </li>
      <li v-show="!loginIn" style="margin-left:30px;" :class="{active: item.name == activeName}" v-for="item in loginMsg" :key="item.path" @click="goPage(item.path,item.name)">
        {{item.name}}
      </li>
    </ul>
    <!-- 读者 -->
    <ul class="navbar" v-else>
      <li :class="{active: item.name == activeName}" v-for="item in navMsg" :key="item.path" @click="goPage(item.path,item.name)">
        {{item.name}}
      </li>
      <li>
        <div class="header-search">
          <input type="text" placeholder="搜索书籍或作家" id="seach" @keyup.enter="goSearch()" v-model="keywords">
          <div class="search-btn" @click="goSearch()">
            <span class="iconfont icon-Search" style="font-size:25px;"></span>
          </div>
        </div>
      </li>
      <li class="vip" v-if="!vip" @click="turnVip">
        成为VIP
      </li>
      <li v-show="!loginIn" style="margin-left:30px;" :class="{active: item.name == activeName}" v-for="item in loginMsg" :key="item.path" @click="goPage(item.path,item.name)">
        {{item.name}}
      </li>
    </ul>
    <div class="header-right" v-show="loginIn">
      <li class="isvip" v-if="vip">
        VIP
      </li>
      <div id='user'>
        <img :src='attachImageUrl(avator)'>
      </div>
      <ul class="menu">
        <li v-for="(item,index) in menuList" :key="index" @click="goMenuList(item.path)">{{item.name}}</li>
      </ul>
    </div>
    <Pay :payVsiable.sync="payVsiable" v-if="payVsiable" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { navMsg, loginMsg, menuList, writerNavMsg } from '../assets/data/header'
import Pay from './pay'
export default {
  name: 'the-header',
  components: {
    Pay
  },
  data() {
    return {
      navMsg: [], //导航栏
      writerNavMsg: [],
      loginMsg: [],
      keywords: '', //搜索关键字
      menuList: [], //用户下拉菜单
      payVsiable: false
    }
  },
  computed: {
    ...mapGetters(['activeName', 'loginIn', 'avator', 'userType', 'vip'])
  },
  created() {
    this.navMsg = navMsg
    this.writerNavMsg = writerNavMsg
    this.loginMsg = loginMsg
    this.menuList = menuList
  },
  mounted() {
    document.querySelector('#user').addEventListener(
      'click',
      function (e) {
        document.querySelector('.menu').classList.add('show')
        e.stopPropagation() //关键在于阻止冒泡
      },
      false
    )
    document.querySelector('.menu').addEventListener(
      'click',
      function (e) {
        e.stopPropagation() //点击菜单内部时，阻止时间冒泡，这样，点击内部时，菜单不会关闭
      },
      false
    )
    document.addEventListener(
      'click',
      function () {
        document.querySelector('.menu').classList.remove('show')
      },
      false
    )
  },
  methods: {
    goHome() {
      this.$router.push({ path: '/' })
      this.$store.commit('setActiveName', '首页')
    },
    goPage(path, name) {
      if (!this.loginIn && path == '/setting?nav=MyBook&activeName=我的书架') {
        this.$message({
          message: '请先登录',
          showClose: true,
          type: 'warning'
        })
      } else {
        this.$store.commit('setActiveName', name)
        this.$router.push({ path: path })
      }
    },
    goSearch() {
      this.$router.push({ path: '/search', query: { keywords: this.keywords } })
    },
    turnVip() {
      if (!this.loginIn) {
        this.$message({
          message: '请先登录',
          showClose: true,
          type: 'warning'
        })
        retrun
      }
      this.payVsiable = true
    },
    //获取图片地址
    attachImageUrl(srcUrl) {
      return srcUrl ? this.$store.state.configure.HOST + srcUrl : '../assets/img/user.jpg'
    },
    goMenuList(path) {
      if (path == 0) {
        this.$store.commit('setLoginIn', false)
        this.$store.commit('setIsActive', false)
        this.$store.commit('setUserType', null)
        this.$router.push({ path: '/' })
        window.sessionStorage.clear()
        this.$router.go(0)
      } else {
        this.$store.commit('setActiveName', '')
        this.$router.push({ path: path })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-header.scss';
</style>
<style scoped>
.vip {
  color: #4747A5 !important;
}
.vip:hover {
  color: #D55969 !important;
}
.isvip {
  font-size: 18pt !important;
  color: #D55969 !important;
}
</style>
