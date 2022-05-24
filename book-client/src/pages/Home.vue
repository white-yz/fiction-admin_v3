<template>
  <div class="home">
    <swiper />
    <div class="section" v-for="(item,index) in list" :key="index">
      <div class="section-title" style="margin-bottom: 10px;">
        <span style="border-bottom: 2px solid #000;padding-bottom: 8px;font-family: 华文楷体;font-weight: bold">{{item.name}}</span>
      </div>
      <content-list :contentList="item.list"></content-list>
    </div>
    <div class="top">
      <div>
        <h1>TOP 1</h1>
        <div class="content clearfix">
          <div class="comment ">
            <h3>嗨阅读热评榜<span> COMMENT</span></h3>
            <div class="odd li">
              <div><img :src="getUrl(comment.pic)" alt=""></div>
              <p class="name">{{comment.name}}</p>
              <span class="btn" @click="goBook(comment.id,comment.writerId)">立即阅读</span>
            </div>
          </div>
          <div class="collect">
            <h3>嗨阅读收藏榜<span> COLLECTION</span></h3>
            <!-- <div class="even li"> -->
            <div class="odd li">
              <img :src="getUrl(collect.pic)" alt="">
              <p class="name">{{collect.name}}</p>
              <span class="btn" @click="goBook(collect.id,collect.writerId)">立即阅读</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Swiper from '../components/Swiper'
import contentList from '../components/ContentList'
import {
  getAllWriter,
  getAllBook,
  getAllComment,
  bookOfBookId,
  getAllCollect,
  getAllBookOfCharge,
  updateUserVip,
  updateWriterVip
} from '../api/index'
import { mixin } from '../mixins/index'
import { mapGetters } from 'vuex'
export default {
  name: 'home',
  mixins: [mixin],
  components: {
    Swiper,
    contentList //=list.list
  },
  data() {
    return {
      list: [
        { name: '精选书籍', list: [] },
        { name: '人气作家', list: [] }
      ],
      max: [],
      collect: '',
      comment: ''
    }
  },
  computed: {
    ...mapGetters(['top1', 'top2'])
  },
  created() {
    let oldUserId = this.$store.state.user.userId
    if (!oldUserId) {
      let userId = window.sessionStorage.getItem('userId')
      if (userId) this.valiPay()
    }
    this.getBook()
    this.getWriter()
    this.max.push(this.top1, this.top2)
    this.getTop()
  },
  methods: {
    getBook() {
      //获取十本书
      getAllBookOfCharge(1)
        .then((res) => {
          var maxId = '' //评论top ID
          var max = 0 //最多评论数
          var pmaxId = '' //收藏top ID
          var pmax = 0 //最多收藏数
          for (var i = 0; i < res.length; i++) {
            var id = res[i].id
            getAllComment(id) //返回当前书籍所有评论列表
              .then((res) => {
                if (res.length > 0) {
                  if (res.length > max) {
                    max = res.length
                    maxId = res[0].bookId
                    this.$store.commit('setTop1', maxId)
                  }
                }
              })
              .catch((err) => {
                this.notify('评论加载失败', 'error')
                console.log(err)
              })
            getAllCollect(id) //返回当前书籍所有收藏列表
              .then((res) => {
                if (res.length > 0) {
                  if (res.length > pmax) {
                    pmax = res.length
                    pmaxId = res[0].bookId
                    this.$store.commit('setTop2', pmaxId)
                  }
                }
              })
              .catch((err) => {
                console.log(err)
              })
          }
          this.list[0].list = this.getRandomArrayElements(res, 10)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    valiPay() {
      this.$store.commit('setLoginIn', true)
      this.$store.commit('setUserId', JSON.parse(window.sessionStorage.getItem('userId')))
      this.$store.commit('setUsername', JSON.parse(window.sessionStorage.getItem('username')))
      this.$store.commit('setVip', window.sessionStorage.getItem('vip'))
      this.$store.commit('setAvator', JSON.parse(window.sessionStorage.getItem('avator')))
      this.$store.commit('setUserType', window.sessionStorage.getItem('userType'))
      this.$store.commit('setActiveName', '首页')
      this.$store.commit('setTop1', window.sessionStorage.getItem('top1'))
      this.$store.commit('setTop2', window.sessionStorage.getItem('top2'))
      if(window.sessionStorage.getItem('isActive')){
          this.$store.commit('setIsActive', JSON.parse(window.sessionStorage.getItem('isActive')))
      }  
      if(window.sessionStorage.getItem('listOfBooks')){
          this.$store.commit('setlistOfBooks', JSON.parse(window.sessionStorage.getItem('listOfBooks')))
      }  
      if(window.sessionStorage.getItem('tempList')){
          this.$store.commit('setTempList', JSON.parse(window.sessionStorage.getItem('tempList')))
      }  
      let outTradeNo = this.$route.query.out_trade_no
      if (outTradeNo) {
        this.$store.commit('setVip', true)
        this.handlePay()
      }
    },
    handlePay() {
      let params = new URLSearchParams()
      params.append('id', this.$store.state.user.userId)
      params.append('vip', true)
      if (this.$store.state.user.userType == 'WRITER') {
        updateWriterVip(params).then((res) => {
          if (res) {
            this.$store.commit('setVip', true)
          }
        })
      } else {
        updateUserVip(params).then((res) => {
          if (res) {
            this.$store.commit('setVip', true)
          }
        })
      }
    },
    getWriter() {
      //获取十名作家
      getAllWriter()
        .then((res) => {
          this.list[1].list = this.getRandomArrayElements(res, 10)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    getTop() {
      //将top 对象分别存入
      bookOfBookId(this.max[0]).then((res) => {
        this.comment = res
      })
      bookOfBookId(this.max[1]).then((res) => {
        this.collect = res
      })
    },

    goBook(id, writerId) {
      //跳转到详情
      this.$router.push({ path: `/Book`, query: { id, writerId } })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
